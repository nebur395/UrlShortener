package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.web.UrlShortenerController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UrlShortenerControllerWithLogs extends UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);

	@Override
	@RequestMapping(value = "/{id:(?!link|index|app).*}", method = RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		return super.redirectTo(id, request);
	}

	@Override
	public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
											  @RequestParam(value = "sponsor", required = false) String sponsor,
											  HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);
        ResponseEntity<ShortURL> r = super.shortener(url, sponsor, request);
        String ip = r.getBody().getIP();
        System.out.println("ip: "+ip);
        ReadLocation l = new ReadLocation(ip);
        l.location();
        return r;
	}
}
