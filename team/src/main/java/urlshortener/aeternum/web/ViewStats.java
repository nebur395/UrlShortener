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

import urlshortener.common.domain.Click;
import urlshortener.common.domain.Stats;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.repository.UserRepository;
import urlshortener.common.web.UrlShortenerController;

@RestController
public class ViewStats {
    private static final Logger LOG = LoggerFactory
        .getLogger(ViewStats.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected UserRepository userRepository;

    @RequestMapping(value = "/viewStatistics", method = RequestMethod.GET)
    public ResponseEntity<Stats> shortener() {
        Long upTime = getUpTime();
        Long totalURL = shortURLRepository.count();
        Long totalUser = userRepository.count();
        Long averageAccessURL = getAverageAccessURL(totalURL);
        Long responseTime = UrlShortenerController.getLastResponseTime();

        Runtime runtime = Runtime.getRuntime();
        Long memoryUsed = getUsedMemory(runtime);
        Long memoryAvailable = getAvailableMemory(runtime);

        List<Click> topClicks = getTopUrl(new Long(10));
        List<String> topURL = new ArrayList<String>();
        topURL.add("hola1");
        topURL.add("hola2");
        topURL.add("hola3");
        Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topURL);
        if (statisticsSystem != null) {
            LOG.info("System statistics");
            return new ResponseEntity<>(statisticsSystem, HttpStatus.CREATED);
        } else {
            LOG.info("Error to get the system statistics");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private List<Click> getTopUrl (Long limit) {
        return clickRepository.topURL(limit);
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
        int mb = 1024;
        return ((runTime.totalMemory() - runTime.freeMemory()) / mb);
    }

    private Long getAvailableMemory (Runtime runTime) {
        int mb = 1024; // 1024 *1024 = MB; 1024 = KB
        return (runTime.freeMemory() / mb);
    }
}
