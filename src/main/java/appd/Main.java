package appd;

import appd.a2cm.Analytics;
import appd.a2cm.MachineAgentHttpManager;
import appd.a2cm.configuration.XmlConfiguration;
import appd.a2cm.configuration.YmlAppConfiguration;
import appd.a2cm.configuration.YmlMetricsConfiguration;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;



public class Main {

    public static List<String> metricsList;
    
    public static String getConfig(String config) {
        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File file = new File(jarFile.getParent(), config);
        return file.getPath().replaceAll("%20*", " ");
    }
    
    

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, JAXBException, InterruptedException {
         BasicConfigurator.configure();
         
         
        YmlAppConfiguration applicationConfiguration = new YmlAppConfiguration(getConfig("app-config.yml"));
        YmlMetricsConfiguration ymlMetricsConfiguration = new YmlMetricsConfiguration(getConfig("metrics-config.yml"));
        Analytics analytics = new Analytics(applicationConfiguration.getEventsEndpoint(),applicationConfiguration.getGlobalAccount(),applicationConfiguration.getApiAccessKey());

        // measure execution time
        long startTime = System.nanoTime();
        
        // add amount metrics reported
        int metricsCount = 0;
        List<String> metricsList = new ArrayList<String>();
        
        RunnableHttpRequests R1 = new RunnableHttpRequests(1,ymlMetricsConfiguration,analytics);
        RunnableHttpRequests R2 = new RunnableHttpRequests(2,ymlMetricsConfiguration,analytics);
        

        //Starting threads
        R1.start();
        R2.start();
             

        
        //execution time end - need to convert it to metric
        boolean threadState = true;
        
        //make sure if threads are alive before giving final statistics
        while (R1.t.isAlive() || R2.t.isAlive()) {
            sleep(100);  
        }
        
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000000;
        System.out.println("name=Custom Metrics|AnalyticsToMetrics|HeartBeat, value=1");
        System.out.println("name=Custom Metrics|AnalyticsToMetrics|MetricsCount, value=" + (R1.metricsCount + R2.metricsCount));
        R1 = null;
        R2 = null;
        System.out.println("name=Custom Metrics|AnalyticsToMetrics|ExecutionTime (seconds), value=" + totalTime);
        if (!applicationConfiguration.getHttpMode()) {
            for (int i = 0; i < metricsList.size(); i++) {
                System.out.println(metricsList.get(i));
            }
        }
        
        
        
        
    }
}