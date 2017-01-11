package urlshortener.common.domain;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.sql.Date;

public class ShortURL {

	private String hash;
	private String target;
	private URI uri;
	private String sponsor;
	private Date created;
	private String owner;
	private Integer mode;
	private Boolean safe;
	private String ip;
	private String country;
    private String qrCode;
    private Boolean subscribed;

	public ShortURL(String hash, String target, URI uri, String sponsor,
			Date created, String owner, Integer mode, Boolean safe, String ip,
			String country, String qrCode, Boolean subscribed) {
		this.hash = hash;
		this.target = target;
		this.uri = uri;
		this.sponsor = sponsor;
		this.created = created;
		this.owner = owner;
		this.mode = mode;
		this.safe = safe;
		this.ip = ip;
		this.country = country;
        this.qrCode = qrCode;
        this.subscribed = subscribed;
	}

	public ShortURL() {
	}

	public String getHash() {
		return hash;
	}

	public String getTarget() {
		return target;
	}

	public URI getUri() {
		return uri;
	}

	public Date getCreated() {
		return created;
	}

	public String getOwner() {
		return owner;
	}

	public Integer getMode() {
		return mode;
	}

	public String getSponsor() {
		return sponsor;
	}

	public Boolean getSafe() {
		return safe;
	}

	public String getIP() {
		return ip;
	}

	public String getCountry() {
		return country;
	}

    public void setSafe(Boolean safe) {
        this.safe = safe;
    }

    public String getQrCode() { return qrCode;}

    public void setQrCode(String qrCode) { this.qrCode = qrCode;}

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return "ShortURL{" +
            "hash='" + hash + '\'' +
            ", target='" + target + '\'' +
            ", uri=" + uri +
            ", sponsor='" + sponsor + '\'' +
            ", created=" + created +
            ", owner='" + owner + '\'' +
            ", mode=" + mode +
            ", safe=" + safe +
            ", ip='" + ip + '\'' +
            ", country='" + country + '\'' +
            ", qrCode='" + qrCode + '\'' +
            ", subscribed=" + subscribed +
            '}';
    }
}
