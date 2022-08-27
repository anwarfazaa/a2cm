package appd.a2cm.configuration;

import appd.a2cm.configuration.items.AnalyticsMetric;
import appd.a2cm.configuration.items.Metrics;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;

public class XmlConfiguration {

    static Logger log = Logger.getLogger(XmlConfiguration.class.getName());  
    
    JAXBContext jaxbContext = null;

    List<AnalyticsMetric> analyticsMetricList;
    public XmlConfiguration(String filePath) {
        try {
        
            jaxbContext = JAXBContext.newInstance(Metrics.class);

            File file = new File(filePath);
            //log.info("Loading Metrics List from: " + filePath);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Metrics o = (Metrics) jaxbUnmarshaller.unmarshal(file);

            this.analyticsMetricList =  o.getAnalyticsMetricList();
            
        } catch (Exception ex) {
            log.error(ex);
        }

    }

    public List<AnalyticsMetric> getAnalyticsMetricList() {
        return this.analyticsMetricList;
    }
}
