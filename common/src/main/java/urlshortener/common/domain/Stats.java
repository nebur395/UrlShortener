package urlshortener.common.domain;

import java.util.List;

public class Stats {

    private Long upTime;
    private Long totalURL;
    private Long totalUser;
    private Long averageAccessURL;
    private int responseTime;
    private int memoryUsed;
    private int memoryAvailable;
    private List<String> topURL;

    public Stats(Long upTime, Long totalURL, Long totalUser, Long averageAccessURL, int responseTime,
                 int memoryUsed, int memoryAvailable, List<String> topURL) {
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

    public int getResponseTime() {
        return responseTime;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public int getMemoryAvailable() {
        return memoryAvailable;
    }

    public List<String> getTopURL() {
        return topURL;
    }

}
