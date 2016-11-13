package urlshortener.common.domain;

/**
 * Created by anicacortes on 09/11/2016.
 */
public class MetadataEntry {

    private String key;
    private String value;

    public MetadataEntry() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
