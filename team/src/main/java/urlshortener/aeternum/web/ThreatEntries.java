package urlshortener.aeternum.web;

public class ThreatEntries {

    //private String hash;
    private String url;
    //private String digest;

    public ThreatEntries() {
    }

    public ThreatEntries(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   /* public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }*/
}
