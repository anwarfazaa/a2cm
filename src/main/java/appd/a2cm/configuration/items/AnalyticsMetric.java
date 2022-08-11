package appd.a2cm.configuration.items;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "analyticsmetric")
public class AnalyticsMetric {
    private String name;
    private String query;
    private String metricPath;
    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "query")
    public void setQuery(String query) {
        this.query = query;
    }

    @XmlElement(name = "metric-path")
    public void setMetricPath(String metricPath){
        this.metricPath = metricPath;
    }

    public String getMetricPath(){
        return this.metricPath;
    }
    @Override
    public String toString() {
        return getName() + ":" + getQuery();
    }
}
