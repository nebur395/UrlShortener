package urlshortener.aeternum.web;

public class Union {

    private ClientInfo client;
    private ThreatInfo threatInfo;

    public Union(ClientInfo client, ThreatInfo threatInfo) {
        this.client = client;
        this.threatInfo = threatInfo;
    }

    public ClientInfo getClient() {
        return client;
    }

    public void setClient(ClientInfo client) {
        this.client = client;
    }

    public ThreatInfo getThreatInfo() {
        return threatInfo;
    }

    public void setThreatInfo(ThreatInfo threatInfo) {
        this.threatInfo = threatInfo;
    }
}
