package urlshortener.common.domain;

public class ThreatMatch {
    private String threatType;
    private String platformType;
    private String threatEntryType;
//    public enum threatType{MALWARE, SOCIAL_ENGINEERING, UNWANTED_SOFTWARE, POTENTIALLY_HARMFUL_APPLICATION, THREAT_TYPE_UNSPECIFIED}
//    public enum platformType{WINDOWS,LINUX, ANDROID, OSX, IOS, ANY_PLATFORM, ALL_PLATFORMS, CHROME, PLATFORM_TYPE_UNSPECIFIED}
//    public enum threatEntryType {URL, EXECUTABLE, IP_RANGE, THREAT_ENTRY_TYPE_UNSPECIFIED}

    private ThreatEntry threat;
    private ThreatEntryMetadata threatEntryMetadata;
    private String cacheDuration;

    public ThreatMatch(ThreatEntry threat, ThreatEntryMetadata threatEntryMetadata, String cacheDuration) {
        this.threat = threat;
        this.threatEntryMetadata = threatEntryMetadata;
        this.cacheDuration = cacheDuration;
    }

    public ThreatMatch() {
    }

    public ThreatMatch(String threatType, String platformType, String threatEntryType, ThreatEntry threat, ThreatEntryMetadata threatEntryMetadata, String cacheDuration) {
        this.threatType = threatType;
        this.platformType = platformType;
        this.threatEntryType = threatEntryType;
        this.threat = threat;
        this.threatEntryMetadata = threatEntryMetadata;
        this.cacheDuration = cacheDuration;
    }

    @Override
    public String toString() {
        return "ThreatMatch{" +
            "threatType='" + threatType + '\'' +
            ", platformType='" + platformType + '\'' +
            ", threatEntryType='" + threatEntryType + '\'' +
            ", threat=" + threat +
            ", threatEntryMetadata=" + threatEntryMetadata +
            ", cacheDuration='" + cacheDuration + '\'' +
            '}';
    }

    public String getThreatType() {
        return threatType;
    }

    public void setThreatType(String threatType) {
        this.threatType = threatType;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getThreatEntryType() {
        return threatEntryType;
    }

    public void setThreatEntryType(String threatEntryType) {
        this.threatEntryType = threatEntryType;
    }


    public ThreatEntry getThreat() {
        return threat;
    }

    public void setThreat(ThreatEntry threat) {
        this.threat = threat;
    }

    public ThreatEntryMetadata getThreatEntryMetadata() {
        return threatEntryMetadata;
    }

    public void setThreatEntryMetadata(ThreatEntryMetadata threatEntryMetadata) {
        this.threatEntryMetadata = threatEntryMetadata;
    }

    public String getCacheDuration() {
        return cacheDuration;
    }

    public void setCacheDuration(String cacheDuration) {
        this.cacheDuration = cacheDuration;
    }


}
