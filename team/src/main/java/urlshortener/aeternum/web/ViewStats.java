package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

import urlshortener.common.domain.*;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.repository.UserRepository;
import urlshortener.common.web.UrlShortenerController;

import javax.servlet.http.HttpServletRequest;

@RestController
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

    @RequestMapping(value = "/viewStatistics", method = RequestMethod.GET)
    public ResponseEntity<Stats> getStats() {
        Long upTime = upTimeVisibility?getUpTime() :null;
        Long totalURL = totalURLVisibility?shortURLRepository.count() :null;
        Long totalUser = totalUserVisibility?userRepository.count() :null;
        Long averageAccessURL = averageAccessURLVisibility?getAverageAccessURL(shortURLRepository.count()) :null;
        Long responseTime = responseTimeVisibility?UrlShortenerController.getLastResponseTime() :null;
        Runtime runtime = Runtime.getRuntime();
        Long memoryUsed = memoryUsedVisibility?getUsedMemory(runtime) :null;
        Long memoryAvailable = memoryAvailableVisibility?getAvailableMemory(runtime) :null;
        List<String> topUrl = topUrlVisibility?getTopUrl(new Long(11)) :null;

        Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topUrl, null);
        if (statisticsSystem != null) {
            LOG.info("SUCCESS: System statistics delivered");
            return new ResponseEntity<>(statisticsSystem, HttpStatus.OK);
        } else {
            LOG.info("ERROR: Error to get the system statistics");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/viewStatistics/admin", method = RequestMethod.GET)
    public ResponseEntity<Stats> getStatsAdmin(HttpServletRequest request) {

        String sessionJWT = request.getHeader("Authorization");

        if (SignIn.verify(sessionJWT)) {
            Long upTime = getUpTime();
            Long totalURL = shortURLRepository.count();
            Long totalUser = userRepository.count();
            Long averageAccessURL = getAverageAccessURL(totalURL);
            Long responseTime = UrlShortenerController.getLastResponseTime();
            Runtime runtime = Runtime.getRuntime();
            Long memoryUsed = getUsedMemory(runtime);
            Long memoryAvailable = getAvailableMemory(runtime);
            List<String> topUrl = getTopUrl(new Long(11));

            StatsVisibility statsVisibility = new StatsVisibility(upTimeVisibility,
                totalURLVisibility, totalUserVisibility, averageAccessURLVisibility,
                responseTimeVisibility, memoryUsedVisibility, memoryAvailableVisibility,
                topUrlVisibility);
            Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
                responseTime, memoryUsed, memoryAvailable, topUrl, statsVisibility);
            if (statisticsSystem != null) {
                LOG.info("SUCCESS: System admin statistics delivered");
                return new ResponseEntity<>(statisticsSystem, HttpStatus.OK);
            } else {
                LOG.info("ERROR: Error to get the system admin statistics");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            LOG.info("ERROR: Error to get the system admin statistics. You are not an administrator.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/viewStatistics", method = RequestMethod.POST)
    public ResponseEntity<String> setStatsVisibility(@RequestParam("upTime") boolean upTime,
                                                     @RequestParam("totalURL") boolean totalURL,
                                                     @RequestParam("topUrl") boolean topURL,
                                                     @RequestParam("totalUser") boolean totalUser,
                                                     @RequestParam("averageAccessURL") boolean averageAccessURL,
                                                     @RequestParam("responseTime") boolean responseTime,
                                                     @RequestParam("memoryUsed") boolean memoryUsed,
                                                     @RequestParam("memoryAvailable") boolean memoryAvailable,
                                                     HttpServletRequest request) {

        String sessionJWT = request.getHeader("Authorization");

        if (SignIn.verify(sessionJWT)) {
            upTimeVisibility = upTime;
            totalURLVisibility = totalURL;
            totalUserVisibility = totalUser;
            averageAccessURLVisibility = averageAccessURL;
            responseTimeVisibility = responseTime;
            memoryUsedVisibility = memoryUsed;
            memoryAvailableVisibility = memoryAvailable;
            topUrlVisibility = topURL;
            LOG.info("SUCCESS: Stats visibility succesfully changed");
            return new ResponseEntity<>("\"Stats visibility succesfully changed\"", HttpStatus.OK);
        } else {
            LOG.info("ERROR: Error to set stats visibility. You are not an administrator.");
            return new ResponseEntity<>("\"Error to set stats visibility. You are not an " +
                "administrator.\"",HttpStatus.BAD_REQUEST);
        }
    }

    private List<String> getTopUrl (Long limit) {
        List<ClickTop> clicksTop = clickRepository.topURL(limit);
        List<ShortURL> topUrl = new ArrayList<ShortURL>();
        for (ClickTop click : clicksTop) {
            topUrl.add(shortURLRepository.findByKey(click.getHash()));
        }
        List <String> hashUrl = new ArrayList<String>();
        for (ShortURL url : topUrl) {
            hashUrl.add(url.getTarget());
        }
        return hashUrl;
    }

    private Long getUpTime () {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        return (rb.getUptime()/1000);   // milliseconds to seconds
    }

    private Long getAverageAccessURL (Long totalURL) {
        if (totalURL.equals(new Long(0))) {
            return new Long(0);
        } else {
            return clickRepository.count() / totalURL;
        }
    }

    private Long getUsedMemory (Runtime runTime) {
        int kb = 1024;
        return ((runTime.totalMemory() - runTime.freeMemory()) / kb);
    }

    private Long getAvailableMemory (Runtime runTime) {
        int kb = 1024; // 1024 *1024 = MB; 1024 = KB
        return (runTime.freeMemory() / kb);
    }
}
