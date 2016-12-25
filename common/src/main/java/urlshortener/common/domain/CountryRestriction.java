package urlshortener.common.domain;

public class CountryRestriction {

    private String country;
    private boolean accessAllowed;
    private int time;
    private int request;

    public CountryRestriction(String country, boolean accessAllowed, int time, int request) {
        this.country = country;
        this.accessAllowed = accessAllowed;
        this.time = time;
        this.request = request;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isaccessAllowed() {
        return accessAllowed;
    }

    public void setaccessAllowed(boolean accessAllowed) {
        this.accessAllowed = accessAllowed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "CountryRestriction{" +
            "country='" + country + '\'' +
            ", accessAllowed=" + accessAllowed +
            ", time=" + time +
            ", request=" + request +
            '}';
    }
}
