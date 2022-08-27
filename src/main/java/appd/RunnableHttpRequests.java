package appd;

import appd.a2cm.Analytics;
import appd.a2cm.configuration.XmlConfiguration;
import java.util.List;

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
    private XmlConfiguration metricsConfiguration;
    private Analytics analytics;
    public int metricsCount;
    
    
    RunnableHttpRequests(int threadRole, XmlConfiguration metricsConfiguration, Analytics analytics) {
        this.metricsCount = 0;
        this.metricsConfiguration = metricsConfiguration;
        this.analytics = analytics;
        int metricsListSize = metricsConfiguration.getAnalyticsMetricList().size() - 1;
        
        //System.out.println("Array Size : " + metricsListSize);
        
        //Managing threads lets divide and conquer :D
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
        for (int i = startIndex; i <= endIndex ; i++) {
            System.out.println(metricsConfiguration.getAnalyticsMetricList().get(i).getMetricPath() + ", value=" + analytics.query(metricsConfiguration.getAnalyticsMetricList().get(i).getQuery()));
            this.metricsCount++;
        }
    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
    
}
