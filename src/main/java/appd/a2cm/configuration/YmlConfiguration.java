package appd.a2cm.configuration;


import appd.a2cm.configuration.items.ApplicationConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class YmlConfiguration {

    private ApplicationConfiguration applicationConfiguration;

    public YmlConfiguration(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        try(InputStream in = Files.newInputStream(Paths.get(filePath))) {
            applicationConfiguration = yaml.loadAs(in , ApplicationConfiguration.class);
            //System.out.println(applicationConfiguration.toString());
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
