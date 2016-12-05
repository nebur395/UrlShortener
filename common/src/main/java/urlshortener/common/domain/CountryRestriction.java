package urlshortener.common.domain;

public class CountryRestriction {

    private String country;
    private boolean accessAllowed;

    public CountryRestriction(String country, boolean accessAllowed) {
        this.country = country;
        this.accessAllowed = accessAllowed;
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

    @Override
    public String toString() {
        return "CountryRestriction{" +
            "country='" + country + '\'' +
            ", accessAllowed=" + accessAllowed +
            '}';
    }
}
