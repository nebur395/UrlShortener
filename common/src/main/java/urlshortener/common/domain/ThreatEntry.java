package urlshortener.common.domain;

public class ThreatEntry {

    private String hash;
    private String url;
    private String digest;

    public ThreatEntry() {
    }

    public ThreatEntry(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   /*
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }*/

    @Override
    public String toString() {
        return "ThreatEntry{" +
            "url='" + url + '\'' +
            '}';
    }
}
