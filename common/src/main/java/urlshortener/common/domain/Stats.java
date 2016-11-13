package urlshortener.common.domain;

import java.util.List;

public class Stats {

    private Long upTime;
    private Long totalURL;
    private Long totalUser;
    private Long averageAccessURL;
    private Long responseTime;
    private Long memoryUsed;
    private Long memoryAvailable;
    private List<String> topURL;

    public Stats(Long upTime, Long totalURL, Long totalUser, Long averageAccessURL, Long responseTime,
                 Long memoryUsed, Long memoryAvailable, List<String> topURL) {
        this.upTime = upTime;
        this.totalURL = totalURL;
        this.totalUser = totalUser;
        this.averageAccessURL = averageAccessURL;
        this.responseTime = responseTime;
        this.memoryUsed = memoryUsed;
        this.memoryAvailable = memoryAvailable;
        this.topURL = topURL;
    }

    public Stats() {
    }

    public Long getUpTime() {
        return upTime;
    }

    public Long getTotalURL() {
        return totalURL;
    }

    public Long getTotalUser() {
        return totalUser;
    }

    public Long getAverageAccessURL() {
        return averageAccessURL;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public Long getMemoryAvailable() {
        return memoryAvailable;
    }

    public List<String> getTopURL() {
        return topURL;
    }

}
