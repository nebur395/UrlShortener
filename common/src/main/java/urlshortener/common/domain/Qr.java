package urlshortener.common.domain;

/**
 * Created by belbus on 13/11/16.
 */
public class Qr {
    private String qrURL;

    public Qr(String qrURL){
        this.qrURL = qrURL;

    }

    public void setQrURL(String qrURL) {
        this.qrURL = qrURL;
    }

    public String getQrURL() {

        return qrURL;
    }
}
