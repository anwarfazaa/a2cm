/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appd.a2cm.configuration;

import appd.a2cm.configuration.items.ListOfMetrics;
import java.util.List;

/**
 *
 * @author anwar
 */
public class YmlMetricsConfigurationList {
    
    private List<ListOfMetrics> listOfMetrics;
    
    public void setListOfMetrics(List<ListOfMetrics> value) {
        this.listOfMetrics = value;
    }
    
    public List<ListOfMetrics> getListOfMetrics(){
        return this.listOfMetrics;
    }
    
}
