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

    public Location() {

    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;

    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
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
