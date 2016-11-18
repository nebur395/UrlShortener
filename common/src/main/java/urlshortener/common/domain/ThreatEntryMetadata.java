package urlshortener.common.domain;

/**
 * Created by anicacortes on 09/11/2016.
 */
public class ThreatEntryMetadata {

    private MetadataEntry[] entries;

    public ThreatEntryMetadata() {
    }

    public MetadataEntry[] getEntries() {
        return entries;
    }

    public void setEntries(MetadataEntry[] entries) {
        this.entries = entries;
    }
}
