package urlshortener.common.domain;

public class Threat {

    private String url;

    public Threat(String url) {
        this.url = url;
    }
    public Threat() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
