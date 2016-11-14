package urlshortener.common.domain;

public class ClickTop {

    private String hash;
    private int topUrl;

    public ClickTop(String hash, int topUrl) {
        this.hash = hash;
        this.topUrl = topUrl;
    }

    public String getHash() {
        return hash;
    }

    public int getTopUrl() {
        return topUrl;
    }

}
