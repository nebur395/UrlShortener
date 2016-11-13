package urlshortener.common.domain;

import java.sql.Date;

public class Click {

	private Long id;
	private String hash;
	private Date created;
	private String referrer;
	private String browser;
	private String platform;
	private String ip;
	private String country;
    private double latitude;
    private double longitude;

    public Click(Long id, String hash, Date created, String referrer,
                 String browser, String platform, String ip, String country,
                double latitude, double longitude) {
		this.id = id;
		this.hash = hash;
		this.created = created;
		this.referrer = referrer;
		this.browser = browser;
		this.platform = platform;
		this.ip = ip;
		this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public String getHash() {
		return hash;
	}

	public Date getCreated() {
		return created;
	}

	public String getReferrer() {
		return referrer;
	}

	public String getBrowser() {
		return browser;
	}

	public String getPlatform() {
		return platform;
	}

	public String getIp() {
		return ip;
	}

	public String getCountry() {
		return country;
	}

    public double getLatitude() { return latitude;}

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Click{" +
            "id=" + id +
            ", hash='" + hash + '\'' +
            ", created=" + created +
            ", referrer='" + referrer + '\'' +
            ", browser='" + browser + '\'' +
            ", platform='" + platform + '\'' +
            ", ip='" + ip + '\'' +
            ", country='" + country + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
