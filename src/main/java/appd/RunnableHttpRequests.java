package appd;

import appd.a2cm.Analytics;
import appd.a2cm.configuration.XmlConfiguration;
import appd.a2cm.configuration.YmlMetricsConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author Anwar Fazaa
 * 
 * This was added to split the metric size into two threads.
 * 
 * 
 */
public class RunnableHttpRequests implements Runnable {
    public Thread t;
    private int startIndex;
    private int endIndex;
    private YmlMetricsConfiguration metricsConfiguration;
    private Analytics analytics;
    public int metricsCount;
    private Map formatJson;
    private List<Map<String,String>> requestList;
    private List<String> metricsPaths;
    
    
    
    RunnableHttpRequests(int threadRole, YmlMetricsConfiguration metricsConfiguration, Analytics analytics) {
        this.metricsCount = 0;
        this.metricsConfiguration = metricsConfiguration;
        this.analytics = analytics;
        int metricsListSize = metricsConfiguration.getConfiguredMetricsCount() - 1;
      
        // Managing threads lets divide and conquer :D
        // This is a really bad approach , will be sorted later on
        // But it does the job for now
        startIndex = 0;
        endIndex = metricsListSize;
        
        
        if (threadRole == 1) {
            startIndex = 0;
            //System.out.println("1st thread - Start Index: " +  startIndex);
            if (metricsListSize % 2 == 0) {
                endIndex = metricsListSize / 2;
                //System.out.println("1st thread - End Index: " +  endIndex);
            } else {
                endIndex = metricsListSize / 2 - 1;
                //System.out.println("1st thread - End Index: " +  endIndex);
            }
            
        } else if(threadRole == 2) {
            if (metricsListSize % 2 == 0) {
                startIndex = metricsListSize / 2 + 1;
                //System.out.println("2nd thread - Start Index: " +  startIndex);
            } else {
                startIndex = metricsListSize / 2;
                //System.out.println("2nd thread - Start Index: " +  startIndex);
            }
            endIndex = metricsListSize;
            //System.out.println("2nd thread - End Index: " +  endIndex);
        }
        
    }
    
    
    @Override
    public void run() {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonArray jsonArray;
        int sizeCount = 1;
        formatJson = new HashMap<String,String>();
        requestList = new ArrayList<Map<String,String>>();
        metricsPaths = new ArrayList<String>();
        for (int i = startIndex; i <= endIndex ; i++) {
            //formatJson = new HashMap<String,String>();
            formatJson.put("label", metricsConfiguration.getName(i));
            formatJson.put("query", metricsConfiguration.getQuery(i));
            
            
            metricsPaths.add(metricsConfiguration.getMetricPath(i));
            requestList.add(formatJson);
            try {
                if ((endIndex - i) < 10 || (sizeCount % 10 == 0)) {
                    jsonArray = analytics.query(gson.toJson(requestList).toString(),10);
                    
                    for (int x = 0; x < requestList.size(); x++) {
                        
                        System.out.println(metricsPaths.get(x) + ", value=" + beautify(jsonArray.get(x).getAsJsonObject().getAsJsonArray("results").toString()));
                        metricsCount++;
                    }
                    requestList.clear();
                    metricsPaths.clear();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            sizeCount++;
  
        }
        
    }
    
    public String beautify(String result) {
        result = result.replaceAll("(\\[)|(\\])", "");
        
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
    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
    
}
