package urlshortener.aeternum.web;

/**
 * Created by anicacortes on 08/11/2016.
 */
public class ClientInfo {

    private String clientId;
    private String clientVersion;

    public ClientInfo(String clientId, String clientVersion) {
        this.clientId = clientId;
        this.clientVersion = clientVersion;
    }

    public ClientInfo(){}

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
