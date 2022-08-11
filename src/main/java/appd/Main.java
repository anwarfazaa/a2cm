package appd;

import appd.a2cm.Analytics;
import appd.a2cm.MachineAgentHttpManager;
import appd.a2cm.configuration.XmlConfiguration;
import appd.a2cm.configuration.YmlConfiguration;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static String getConfig(String config) {
        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File file = new File(jarFile.getParent(), config);
        return file.getPath().replaceAll("%20*", " ");
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, JAXBException {
         
        YmlConfiguration applicationConfiguration = new YmlConfiguration(getConfig("app-config.yml"));
        XmlConfiguration metricsConfiguration = new XmlConfiguration(getConfig("config.xml"));
        Analytics analytics = new Analytics(applicationConfiguration.getEventsEndpoint(),applicationConfiguration.getGlobalAccount(),applicationConfiguration.getApiAccessKey());

        // measure execution time
        long startTime = System.nanoTime();
        
        // add amount metrics reported
        int metricsCount = 0;
        
        if (!applicationConfiguration.getHttpMode()) {
            for (metricsCount = 0; metricsCount < metricsConfiguration.getAnalyticsMetricList().size(); metricsCount++) {
                System.out.println(metricsConfiguration.getAnalyticsMetricList().get(metricsCount).getMetricPath() + ", value=" + analytics.query(metricsConfiguration.getAnalyticsMetricList().get(metricsCount).getQuery()));
            }
        } else {
            MachineAgentHttpManager machineAgentHttpManager = new MachineAgentHttpManager(applicationConfiguration.getMachineAgentIpAddress(), applicationConfiguration.getMachineAgentPort());
            for (metricsCount = 0; metricsCount < metricsConfiguration.getAnalyticsMetricList().size(); metricsCount++) {
                machineAgentHttpManager.postData(metricsConfiguration.getAnalyticsMetricList().get(metricsCount).getMetricPath(),analytics.query(metricsConfiguration.getAnalyticsMetricList().get(metricsCount).getQuery()));
            }
        }
        
        //adding 1 to metric count to give proper count
        metricsCount++;
        
        //execution time end - need to convert it to metric
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000000;
        System.out.println("name=Custom Metrics|AnalyticsToMetrics|HeartBeat, value=1");
        System.out.println("name=Custom Metrics|AnalyticsToMetrics|MetricsCount, value=" + metricsCount);
        System.out.println("name=Custom Metrics|AnalyticsToMetrics|ExecutionTime (seconds), value=" + totalTime);
        
        
        
        
    }
}