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
import urlshortener.common.domain.ClickTop;
import urlshortener.common.domain.ShortURL;
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

        List<String> topUrl = getTopUrl(new Long(11));
        Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topUrl);
        if (statisticsSystem != null) {
            LOG.info("System statistics");
            return new ResponseEntity<>(statisticsSystem, HttpStatus.CREATED);
        } else {
            LOG.info("Error to get the system statistics");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
