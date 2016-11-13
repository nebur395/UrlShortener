package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.common.domain.*;
import urlshortener.common.web.UrlShortenerController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UrlShortenerControllerWithLogs extends UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);
    private boolean isSafe;

    @Override
	@RequestMapping(value = "/{id:(?!link|index|app|viewStatistics|qr).*}", method = RequestMethod
        .GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		return super.redirectTo(id, request);
	}

	@Override
	public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
											  @RequestParam(value = "sponsor", required = false) String sponsor,
											  HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);

        //Request to Google safe browsing

        ResponseEntity<ShortURL> r = super.shortener(url, sponsor, request);

        Union u = new Union(new ClientInfo("id","version blabla"), new ThreatInfo(new ThreatEntries(url)));
        SafeBrowsing sb = new SafeBrowsing();
        try {
            isSafe = sb.safe(url);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(isSafe) logger.info("The URL " + url + " is safe");
        else logger.info("The URL " + url + "is unsafe");
        ShortURL miShort = r.getBody();
        shortURLRepository.mark(miShort, isSafe);
        //System.out.println(shortURLRepository.findByKey(miShort.getHash()));

        return r;
	}

    //Clave de API para el proyecto "SafeProject": AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI
    //GET https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI HTTP/1.1
}
