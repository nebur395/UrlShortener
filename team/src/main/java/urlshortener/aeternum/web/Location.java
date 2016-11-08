package urlshortener.aeternum.web;

public class Location {

    private String statusCode;
    private String statusMessage;
    private String ip;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private String zipCode;
    private String latitude;
    private String longitude;
    private String timeZone;

    /*{
	"statusCode" : "OK",
	"statusMessage" : "",
	"ipAddress" : "74.125.45.100",
	"countryCode" : "US",
	"countryName" : "UNITED STATES",
	"regionName" : "CALIFORNIA",
	"cityName" : "MOUNTAIN VIEW",
	"zipCode" : "94043",
	"latitude" : "37.3956",
	"longitude" : "-122.076",
	"timeZone" : "-08:00"
}     */

    public String getStatusCode() {

        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getIp() {
        return ip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
