package appd.a2cm.configuration.items;

public final class ApplicationConfiguration {

    private String eventsEndpoint;
    private String globalAccount;
    private String apiAccessKey;
    private boolean httpMode;
    private String machineAgentIpAddress;
    private int machineAgentPort;

    public String getEventsEndpoint() {
        return this.eventsEndpoint;
    }
    public void setEventsEndpoint(String value) {
        this.eventsEndpoint = value;
    }
    public String getGlobalAccount() {
        return this.globalAccount;
    }
    public void setGlobalAccount(String value) {
        this.globalAccount = value;
    }
    public String getApiAccessKey() {
        return this.apiAccessKey;
    }
    public void setApiAccessKey(String value) {
        this.apiAccessKey = value;
    }
    public boolean getHttpMode() { return this.httpMode; }
    public void setHttpMode(boolean value) {
        this.httpMode = value;
    }
    public String getMachineAgentIpAddress() { return  this.machineAgentIpAddress; }
    public void setMachineAgentIpAddress(String value) {
        this.machineAgentIpAddress = value;
    }
    public int getMachineAgentPort() { return  this.machineAgentPort; }
    public void setMachineAgentPort(int value) {
        this.machineAgentPort = value;
    }

    @Override
    public String toString() {
        return "EventsEndPoint: " + this.eventsEndpoint + " - GlobalAccount: " + this.globalAccount + " - ApiAccessKey: " + this.apiAccessKey;
    }

}
