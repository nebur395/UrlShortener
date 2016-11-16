package urlshortener.common.domain;

public class StatsVisibility {

    private boolean upTime;
    private boolean totalURL;
    private boolean totalUser;
    private boolean averageAccessURL;
    private boolean responseTime;
    private boolean memoryUsed;
    private boolean memoryAvailable;
    private boolean topUrl;

    public StatsVisibility(boolean upTimeVisibility, boolean totalURLVisibility,
                           boolean totalUserVisibility, boolean averageAccessURLVisibility,
                           boolean responseTimeVisibility, boolean memoryUsedVisibility,
                           boolean memoryAvailableVisibility, boolean topUrlVisibility) {
        this.upTime = upTimeVisibility;
        this.totalURL = totalURLVisibility;
        this.totalUser = totalUserVisibility;
        this.averageAccessURL = averageAccessURLVisibility;
        this.responseTime = responseTimeVisibility;
        this.memoryUsed = memoryUsedVisibility;
        this.memoryAvailable = memoryAvailableVisibility;
        this.topUrl = topUrlVisibility;
    }

    public StatsVisibility() {
    }

    public boolean getUpTime() {
        return upTime;
    }

    public boolean getTotalURL() {
        return totalURL;
    }

    public boolean getTotalUser() {
        return totalUser;
    }

    public boolean getAverageAccessURL() {
        return averageAccessURL;
    }

    public boolean getResponseTime() {
        return responseTime;
    }

    public boolean getMemoryUsed() {
        return memoryUsed;
    }

    public boolean getMemoryAvailable() {
        return memoryAvailable;
    }

    public boolean getTopUrl() {
        return topUrl;
    }

}
