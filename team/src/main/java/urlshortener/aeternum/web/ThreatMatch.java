package urlshortener.aeternum.web;

public class ThreatMatch {

    private enum threadType {MALWARE, SOCIAL_ENGINEERING, UNWANTED_SOFTWARE, POTENTIALLY_HARMFUL_APPLICATION}
    private enum platformTypes {WINDOWS, LINUX, ANDROID, OSX, IOS, ANY_PLATFORM, ALL_PLATFORMS, CHROME}
    private enum threatEntryType {URL, EXECUTABLE, IP_RANGE}
    private ThreatEntries te;
    private String cacheDuration;

    public ThreatMatch(ThreatEntries te) {
        this.te = te;
    }

    public ThreatMatch(){}

    public ThreatEntries getTe() {
        return te;
    }

    public void setTe(ThreatEntries te) {
        this.te = te;
    }

    public String getCacheDuration() {
        return cacheDuration;
    }

    public void setCacheDuration(String cacheDuration) {
        this.cacheDuration = cacheDuration;
    }
}
