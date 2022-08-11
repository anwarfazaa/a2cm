package appd.a2cm;



import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MachineAgentHttpManager {
    private HttpClient httpClient;

    private HttpPost request;

    public MachineAgentHttpManager(String MachineAgentIpAddress,int MachineAgentPort) throws IOException {
        this.httpClient = HttpClientBuilder.create().build();
        this.request = new HttpPost("http://" + MachineAgentIpAddress + ":" + MachineAgentPort + "/api/v1/metrics");
    }

    public void postData(String metricPath, String value) throws UnsupportedEncodingException {
       StringEntity parameters = new StringEntity("[{\"metricName\":\"" + metricPath + "\",\"aggregatorType\":\"AVERAGE\",\"value\":" + value + "}]");
        try {
            this.request.setEntity(parameters);
            HttpResponse response = this.httpClient.execute(this.request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
