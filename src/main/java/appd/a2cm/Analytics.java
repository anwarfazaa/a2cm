package appd.a2cm;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import org.json.*;


public class Analytics {

    private boolean _moreData;
    private SimpleDateFormat _iso8601;
    private String _host;
    private String _account;
    private String _key;


    public Analytics(String _host , String _account , String _key){
        this._host = _host;
        this._account = _account;
        this._key = _key;
    }
    private static InputStream request(final String account, final String key, final String method, final String url, final String data) throws IOException, NoSuchAlgorithmException {
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
        //System.out.println(connection.getContent().toString());
        out.write (postData);
        return connection.getInputStream ();
    }

    protected InputStream post (final String url, final String data) throws IOException,  NoSuchAlgorithmException
    {
        return request (_account, _key, "POST", url, data);
    }

    public String query (String queryBody) throws IOException,  NoSuchAlgorithmException
    {
        String postUrl = this._host + "/events/query";
        String result = new JSONArray(print(post (postUrl,  queryBody)) ).getJSONObject(0).get("results").toString().replaceAll("(\\[)|(\\])", "");

        //In the case of an empty string , we need to return 0 as metric value.
        if (result.length() == 0) {
            result = "0";
        }
        //In the case of a query that returns a data set rather than a metric value
        

        // checking for non integer results to eliminate long results
        int nonIntCheck = result.indexOf(".");
        if (nonIntCheck > -1) {
            result = result.substring(0,(nonIntCheck));
        }
        
        if (!result.matches("\\d*")) {
            result = "0";
        }
        
        return result;
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
