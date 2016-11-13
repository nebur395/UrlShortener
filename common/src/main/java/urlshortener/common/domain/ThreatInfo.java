package urlshortener.common.domain;

public class ThreatInfo {

    private enum threatTypes {MALWARE, SOCIAL_ENGINEERING, UNWANTED_SOFTWARE, POTENTIALLY_HARMFUL_APPLICATION}
    private enum platformTypes {WINDOWS, LINUX, ANDROID, OSX, IOS, ANY_PLATFORM, ALL_PLATFORMS, CHROME}
    private enum threatEntryTypes {URL, EXECUTABLE, IP_RANGE}

    private String[] threatTypes= new String []{"MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE", "POTENTIALLY_HARMFUL_APPLICATION"};
    private String[] platformTypes = new String [] {"WINDOWS", "LINUX", "ANDROID", "OSX", "IOS", "ANY_PLATFORM", "ALL_PLATFORMS", "CHROME"};
    private String[] threatEntryTypes = new String [] {"URL"};

    //No ponemos lista porque solo se va a comprobar una url
    private ThreatEntries threatEntries;

    public ThreatInfo() {}

    public ThreatInfo(ThreatEntries threatEntries) {
        this.threatEntries = threatEntries;
    }

    public String[] getThreatTypes() {
        return threatTypes;
    }

    public void setThreatTypes(String[] threatTypes) {
        this.threatTypes = threatTypes;
    }

    public String[] getPlatformTypes() {
        return platformTypes;
    }

    public void setPlatformTypes(String[] platformTypes) {
        this.platformTypes = platformTypes;
    }

    public String[] getThreatEntryTypes() {
        return threatEntryTypes;
    }

    public void setThreatEntryTypes(String[] threatEntryTypes) {
        this.threatEntryTypes = threatEntryTypes;
    }

    public ThreatEntries getThreatEntries() {
        return threatEntries;
    }

    public void setThreatEntries(ThreatEntries threatEntries) {
        this.threatEntries = threatEntries;
    }
}
