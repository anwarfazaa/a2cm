package appd.a2cm;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.json.*;


public class Analytics {

    //private boolean _moreData;
    //private SimpleDateFormat _iso8601;
    private String _host;
    private String _account;
    private String _key;
    
    
    static Logger log = Logger.getLogger(Analytics.class.getName()); 

    public Analytics(String _host , String _account , String _key){
        this._host = _host;
        this._account = _account;
        this._key = _key;
    }
    private InputStream request(final String account, final String key, final String method, final String url, final String data) throws IOException, NoSuchAlgorithmException {
        //log(data);
        URL urlRequest = new URL(url);
        
        final byte postData [] = data.getBytes(StandardCharsets.UTF_8);
        SSLContext sc = SSLContext.getInstance("SSL");
        HttpsURLConnection connection = (HttpsURLConnection) urlRequest.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty ("X-Events-API-AccountName", account);
        connection.setRequestProperty ("X-Events-API-Key", key);
        connection.setRequestProperty ("Content-Type", "application/vnd.appd.events+json;v=2");
        connection.setRequestProperty ("Accept", "application/vnd.appd.events+json;v=2");
        
        connection.setRequestProperty ("charset", "utf-8");
        connection.setRequestProperty ("Content-Length", Integer.toString (postData.length));
        connection.getOutputStream();
        OutputStream out = connection.getOutputStream();
        out.write (postData);
        return connection.getInputStream ();
    }

    protected InputStream post (final String url, final String data) throws IOException,  NoSuchAlgorithmException
    {
        return request (_account, _key, "POST", url, data);
    }

    // Abandoned for now
    public String query (String queryBody)
    {
        try {
        String postUrl = this._host + "/events/query";
        String result = new JSONArray(print(post (postUrl,  queryBody)) ).getJSONObject(0).get("results").toString().replaceAll("(\\[)|(\\])", "");
        log.debug("Http Request result: " + result);
        //In the case of an empty string , we need to return 0 as metric value.
        if (result.length() == 0) {
            result = "0";
        }
        
        // checking for non integer results to eliminate long results
        int nonIntCheck = result.indexOf(".");
        if (nonIntCheck > -1) {
            result = result.substring(0,(nonIntCheck));
        }
        
        //In the case of a query that returns a data set rather than a metric value
        if (!result.matches("\\d*")) {
            result = "0";
        }
        
        return result;
        
        } catch (Exception ex) {
            log.error("Http Request Exception: " + ex);
            log.error(ex.getStackTrace().toString());
            return "0";
        }
    }
    
    public JsonArray query(String queryBody, int queryLimit) throws IOException, NoSuchAlgorithmException { 
        JsonArray obj = new JsonArray();
        try {
            //System.out.println(queryBody);
            String postUrl = this._host + "/events/query?limit=" + queryLimit;
            String result = new JSONArray(print(post (postUrl,  queryBody)) ).toString();
            Gson gson = new Gson();
            obj = gson.fromJson(result, JsonArray.class);
            //.get(0).getAsJsonObject().getAsJsonArray("results").toString().replaceAll("(\\[)|(\\])", "");
            return obj;
        } catch (Exception ex) {
            log.error("Error at : " + queryBody );
            return obj;
        }
    }

    /*
    public String query (String queryBody) throws IOException,  NoSuchAlgorithmException
    {
        String postUrl = this._host + "/events/query";
        log(postUrl);
        return print(post (postUrl,  queryBody));
    }*/


    private static void log(final String message) {
        System.out.println(message);
    }

    private String print(InputStream is) {
        Scanner scanner = new Scanner (is);
        String output = "";
        while (scanner.hasNextLine ())
        {
            String line = scanner.nextLine ();
            output += line;
        }
        return output;
    }
}
