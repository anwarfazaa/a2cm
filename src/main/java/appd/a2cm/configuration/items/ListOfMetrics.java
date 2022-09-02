/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appd.a2cm.configuration.items;

/**
 *
 * @author Anwar Fazaa
 */
public class ListOfMetrics {
    private String name;
    private String metricPath;
    private String query;
    
    
    public void setName(String value){
        this.name = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setQuery(String value){
        this.query = value;
    }
    
    public String getQuery(){
        return this.query;
    }
    
    public void setMetricPath(String value){
        this.metricPath = value;
    }
    
    public String getMetricPath(){
        return this.metricPath;
    }
}
