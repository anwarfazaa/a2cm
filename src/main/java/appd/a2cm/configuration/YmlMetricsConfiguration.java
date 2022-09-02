/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appd.a2cm.configuration;

import appd.a2cm.configuration.items.ApplicationConfiguration;
import appd.a2cm.configuration.items.ListOfMetrics;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Anwar Fazaa
 */
public class YmlMetricsConfiguration {
    
    static Logger log = Logger.getLogger(YmlAppConfiguration.class.getName()); 
    
    private YmlMetricsConfigurationList ymlMetricsConfigurationList;
    Map<String, Object> data ;
    public YmlMetricsConfiguration(String filePath) {
       
        Yaml yaml = new Yaml();
        try(InputStream in = Files.newInputStream(Paths.get(filePath))) {
            ymlMetricsConfigurationList = yaml.loadAs(in, YmlMetricsConfigurationList.class);
            //log.debug("Yml Configuration: " + applicationConfiguration.toString());
        } catch (Exception ex) {
            log.error("Error loading Yml file: " + ex);
        }

       
    }
    
    //System.out.println(ymlMetricsConfigurationList.getListOfMetrics().get(0).getQuery());
    public String getName(int index) {
        return  this.ymlMetricsConfigurationList.getListOfMetrics().get(index).getName();
    }

    public String getMetricPath(int index) {
        return  this.ymlMetricsConfigurationList.getListOfMetrics().get(index).getMetricPath();
    }

    public String getQuery(int index) {
        return  this.ymlMetricsConfigurationList.getListOfMetrics().get(index).getQuery();
    }
    
    public int getConfiguredMetricsCount() {
        return this.ymlMetricsConfigurationList.getListOfMetrics().size();
    }
}
