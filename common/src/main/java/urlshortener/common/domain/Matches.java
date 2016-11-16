package urlshortener.common.domain;

import java.util.Arrays;

public class Matches {

    private ThreatMatch[] matches;

    public Matches(ThreatMatch[] matches) {
        this.matches = matches;
    }

    public Matches() {}

    public ThreatMatch[] getMatches() {
        return matches;
    }

    public void setMatches(ThreatMatch[] matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "Matches{" +
            "matches=" + Arrays.toString(matches) +
            '}';
    }
}
