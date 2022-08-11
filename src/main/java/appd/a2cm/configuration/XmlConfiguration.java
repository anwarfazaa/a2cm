package appd.a2cm.configuration;

import appd.a2cm.configuration.items.AnalyticsMetric;
import appd.a2cm.configuration.items.Metrics;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class XmlConfiguration {

    JAXBContext jaxbContext = null;

    List<AnalyticsMetric> analyticsMetricList;
    public XmlConfiguration(String filePath) throws JAXBException {

        jaxbContext = JAXBContext.newInstance(Metrics.class);

        File file = new File(filePath);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Metrics o = (Metrics) jaxbUnmarshaller.unmarshal(file);

        this.analyticsMetricList =  o.getAnalyticsMetricList();

    }

    public List<AnalyticsMetric> getAnalyticsMetricList() {
        return this.analyticsMetricList;
    }
}
