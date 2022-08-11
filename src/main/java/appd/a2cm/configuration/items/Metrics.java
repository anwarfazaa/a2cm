package appd.a2cm.configuration.items;

import appd.a2cm.configuration.items.AnalyticsMetric;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "metric-list")
@XmlAccessorType (XmlAccessType.FIELD)
public class Metrics {

    @XmlElement(name = "analyticsmetric")
    private List<AnalyticsMetric> analyticsMetricList = null;

    public List<AnalyticsMetric> getAnalyticsMetricList() {
        return  analyticsMetricList;
    }

    public void setAnalyticsMetricList(List<AnalyticsMetric> analyticsMetricList) {
        this.analyticsMetricList = analyticsMetricList;
    }
}
