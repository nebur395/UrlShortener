package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.common.domain.Location;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.domain.*;
import urlshortener.common.web.UrlShortenerController;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@RestController
public class UrlShortenerControllerWithLogs extends UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);
    private boolean isSafe;
    //Timer time = new Timer(); // Instantiate Timer Object
    ScheduledTask st = new ScheduledTask();


    @Override
	@RequestMapping(value = "/{id:(?!link|index|app|viewStatistics|qr|signUp|signIn|unsafePage).*}",
        method = RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		ResponseEntity<?> r = super.redirectTo(id, request);

        ShortURL s = shortURLRepository.findByKey(id);
        //Read ip client from shortURL and obtain its location info if there is a click with this hash
        if (s != null) {
            String ip = s.getIP();
            ReadLocation l = new ReadLocation(ip);
            Location loc = l.location();
            updateLocation(s, loc);
        }
        return r;
	}

	@Override
	public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
											  @RequestParam(value = "sponsor", required = false) String sponsor,
											  HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);
        ResponseEntity<ShortURL> r = super.shortener(url, sponsor, request);

        SafeBrowsing sb = new SafeBrowsing();
        isSafe = sb.safe(url);

        ShortURL miShort = r.getBody();
        miShort.setSafe(isSafe);
        shortURLRepository.mark(miShort, isSafe);
        return r;
	}

    /**
     * Update location from shortURL in db
     */
	private void updateLocation(ShortURL s, Location loc){
        String hash = s.getHash();
        long n = clickRepository.clicksByHash(hash);
        //Update shortURL coordinates and country
        clickRepository.addLocationInfo(hash, n-1, loc.getCountryName(), loc.getLatitude(), loc.getLongitude());
    }
}
