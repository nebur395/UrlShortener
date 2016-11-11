package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import urlshortener.common.domain.ShortURL;
import urlshortener.common.domain.Stats;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.domain.Click;
import urlshortener.common.web.UrlShortenerController;

@RestController
public class ViewStats {
    private static final Logger LOG = LoggerFactory
        .getLogger(UrlShortenerController.class);
    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @RequestMapping(value = "/viewStatistics", method = RequestMethod.GET)
    public ResponseEntity<Stats> shortener() {
        int upTime = 1;
        int totalURL = 2;
        int totalUser = 3;
        int averageAccessURL = 4;
        int responseTime = 5;
        int memoryUsed = 6;
        int memoryAvailable = 7;
        List<String> topURL = new ArrayList<String>();
        topURL.add("hola1");
        topURL.add("hola2");
        topURL.add("hola3");
        Stats statisticsSystem = new Stats(upTime, totalUser, totalURL, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topURL);
        if (statisticsSystem != null) {
            LOG.info("Estadisticas del sistema " + statisticsSystem.toString());
            return new ResponseEntity<>(statisticsSystem, HttpStatus.CREATED);
        } else {
            LOG.info("Fallo al recoger las estadisticas del sistema");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
