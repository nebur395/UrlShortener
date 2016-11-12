package urlshortener.common.domain;

import java.util.List;

public class Stats {

    private int upTime;
    private Long totalURL;
    private int totalUser;
    private int averageAccessURL;
    private int responseTime;
    private int memoryUsed;
    private int memoryAvailable;
    private List<String> topURL;

    public Stats(int upTime, Long totalURL, int totalUser, int averageAccessURL, int responseTime,
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

    public int getUpTime() {
        return upTime;
    }

    public Long getTotalURL() {
        return totalURL;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public int getAverageAccessURL() {
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
