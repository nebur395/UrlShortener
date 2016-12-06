package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

import urlshortener.common.domain.*;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.repository.UserRepository;
import urlshortener.common.web.UrlShortenerController;

@Component
@Controller
public class ViewStats {
    private static final Logger LOG = LoggerFactory
        .getLogger(ViewStats.class);
    // stats visibility
    private static boolean upTimeVisibility = true;
    private static boolean totalURLVisibility = true;
    private static boolean totalUserVisibility = true;
    private static boolean averageAccessURLVisibility = true;
    private static boolean responseTimeVisibility = true;
    private static boolean memoryUsedVisibility = true;
    private static boolean memoryAvailableVisibility = true;
    private static boolean topUrlVisibility = true;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate template;

    public void getStats() {
        Long upTime = upTimeVisibility ? getUpTime() : null;
        Long totalURL = totalURLVisibility ? shortURLRepository.count() : null;
        Long totalUser = totalUserVisibility ? userRepository.count() : null;
        Long averageAccessURL = averageAccessURLVisibility ? getAverageAccessURL(shortURLRepository.count()) : null;
        Long responseTime = responseTimeVisibility ? UrlShortenerController.getLastResponseTime() : null;
        Runtime runtime = Runtime.getRuntime();
        Long memoryUsed = memoryUsedVisibility ? getUsedMemory(runtime) : null;
        Long memoryAvailable = memoryAvailableVisibility ? getAvailableMemory(runtime) : null;
        List<String> topUrl = topUrlVisibility ? getTopUrl(new Long(11)) : null;

        Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topUrl);
        if (statisticsSystem != null) {
            LOG.debug("SUCCESS: System statistics delivered");
            template.convertAndSend("/viewStats/standardStats", statisticsSystem);
        } else {
            LOG.debug("ERROR: Error to get the system statistics");
        }
    }

    public void getStatsAdmin() {

        Long upTime = getUpTime();
        Long totalURL = shortURLRepository.count();
        Long totalUser = userRepository.count();
        Long averageAccessURL = getAverageAccessURL(totalURL);
        Long responseTime = UrlShortenerController.getLastResponseTime();
        Runtime runtime = Runtime.getRuntime();
        Long memoryUsed = getUsedMemory(runtime);
        Long memoryAvailable = getAvailableMemory(runtime);
        List<String> topUrl = getTopUrl(new Long(11));

        Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topUrl);
        if (statisticsSystem != null) {
            LOG.debug("SUCCESS: System admin statistics delivered");
            template.convertAndSend("/viewStats/adminStats", statisticsSystem);
        } else {
            LOG.debug("ERROR: Error to get the system admin statistics");
        }
    }

    @MessageMapping("/changeVisibility")
    public void setStatsVisibility(StatsVisibility statsVisibility) {
        if (!statsVisibility.getFirstStats()) {
            upTimeVisibility = statsVisibility.getUpTime();
            totalURLVisibility = statsVisibility.getTotalURL();
            totalUserVisibility = statsVisibility.getTotalUser();
            averageAccessURLVisibility = statsVisibility.getAverageAccessURL();
            responseTimeVisibility = statsVisibility.getResponseTime();
            memoryUsedVisibility = statsVisibility.getMemoryUsed();
            memoryAvailableVisibility = statsVisibility.getMemoryAvailable();
            topUrlVisibility = statsVisibility.getTopUrl();
            LOG.debug("SUCCESS: Stats visibility succesfully changed");
        }

        StatsVisibility statsVisibilityTemp = new StatsVisibility(upTimeVisibility,
            totalURLVisibility, totalUserVisibility, averageAccessURLVisibility,
            responseTimeVisibility, memoryUsedVisibility, memoryAvailableVisibility,
            topUrlVisibility, false);

        template.convertAndSend("/viewStats/visibilityStats", statsVisibilityTemp);
    }

    @Scheduled(fixedRate = 1000)
    private void backgroundGetTimeProcess() {
        getStats();
        getStatsAdmin();
    }

    private List<String> getTopUrl(Long limit) {
        List<ClickTop> clicksTop = clickRepository.topURL(limit);
        List<ShortURL> topUrl = new ArrayList<ShortURL>();
        for (ClickTop click : clicksTop) {
            topUrl.add(shortURLRepository.findByKey(click.getHash()));
        }
        List<String> hashUrl = new ArrayList<String>();
        for (ShortURL url : topUrl) {
            hashUrl.add(url.getTarget());
        }
        return hashUrl;
    }

    private Long getUpTime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        return (rb.getUptime() / 1000);   // milliseconds to seconds
    }

    private Long getAverageAccessURL(Long totalURL) {
        if (totalURL.equals(new Long(0))) {
            return new Long(0);
        } else {
            return clickRepository.count() / totalURL;
        }
    }

    private Long getUsedMemory(Runtime runTime) {
        int kb = 1024;
        return ((runTime.totalMemory() - runTime.freeMemory()) / kb);
    }

    private Long getAvailableMemory(Runtime runTime) {
        int kb = 1024; // 1024 *1024 = MB; 1024 = KB
        return (runTime.freeMemory() / kb);
    }
}
