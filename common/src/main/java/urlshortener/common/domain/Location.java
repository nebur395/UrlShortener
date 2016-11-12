package urlshortener.common.domain;

public class Location {

    private String statusCode;
    private String statusMessage;
    private String ip;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private String zipCode;
    private double latitude;
    private double longitude;
    private String timeZone;

    public Location() {

    }

    public String getIp() {
        return ip;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStatusCode() {return statusCode;}

    @Override
    public String toString() {
        return "Location{" +
            "statusCode='" + statusCode + '\'' +
            ", statusMessage='" + statusMessage + '\'' +
            ", ip='" + ip + '\'' +
            ", countryCode='" + countryCode + '\'' +
            ", countryName='" + countryName + '\'' +
            ", regionName='" + regionName + '\'' +
            ", cityName='" + cityName + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", latitude='" + latitude + '\'' +
            ", longitude='" + longitude + '\'' +
            ", timeZone='" + timeZone + '\'' +
            '}';
    }
}
