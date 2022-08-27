package appd.a2cm.configuration;


import appd.a2cm.configuration.items.ApplicationConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

public class YmlConfiguration {

    static Logger log = Logger.getLogger(YmlConfiguration.class.getName()); 
    
    private ApplicationConfiguration applicationConfiguration;

    public YmlConfiguration(String filePath) {
        Yaml yaml = new Yaml();
        try(InputStream in = Files.newInputStream(Paths.get(filePath))) {
            applicationConfiguration = yaml.loadAs(in , ApplicationConfiguration.class);
            //log.debug("Yml Configuration: " + applicationConfiguration.toString());
        } catch (Exception ex) {
            log.error("Error loading Yml file: " + ex);
        }

    }

    public String getEventsEndpoint() {
        return  this.applicationConfiguration.getEventsEndpoint();
    }

    public String getGlobalAccount() {
        return this.applicationConfiguration.getGlobalAccount();
    }

    public String getApiAccessKey() {
        return this.applicationConfiguration.getApiAccessKey();
    }

    public String getMachineAgentIpAddress() { return  this.applicationConfiguration.getMachineAgentIpAddress(); }

    public int getMachineAgentPort() { return  this.applicationConfiguration.getMachineAgentPort(); }

    public boolean getHttpMode() { return this.applicationConfiguration.getHttpMode(); }
}
