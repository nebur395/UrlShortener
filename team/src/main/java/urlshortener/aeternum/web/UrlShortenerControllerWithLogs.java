package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.common.domain.CountryRestriction;
import urlshortener.common.domain.Location;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.repository.CountryResRepository;
import urlshortener.common.web.UrlShortenerController;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Timer;

@RestController
public class UrlShortenerControllerWithLogs extends UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);
    private boolean isSafe;
    //Timer time = new Timer(); // Instantiate Timer Object
    ScheduledTask st = new ScheduledTask();

    @Autowired
    protected CountryResRepository countryResRepository;

    @Autowired
    protected ReadLocation readLocation;

    @Override
	@RequestMapping(value = "/{id:(?!link|index|app|viewStatistics|qr|signUp|signIn|unsafePage|restrictAccess|forbiddenAccess).*}",
        method = RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		ResponseEntity<?> r = super.redirectTo(id, request);

        ShortURL s = shortURLRepository.findByKey(id);
        //Read ip client from shortURL and obtain its location info if there is a click with this hash
        if (s != null) {
            Location loc = readLocation.location();
            updateLocation(s, loc);

            //Search if the country is restricted
            String country = loc.getCountryName();
            CountryRestriction rs = countryResRepository.findCountry(country);
            if(rs.isaccessAllowed()){
                logger.info("Access allowed");
                return r;
            }else{
                logger.info("Access denied");
                return createForbiddenRedirectToResponse();
            }
        }
        return r;
    }

	@Override
	public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
											  @RequestParam(value = "sponsor", required = false) String sponsor,
                                              @RequestParam(value = "wantQr", required = false) boolean wantQr,
											  HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);
        ResponseEntity<ShortURL> r = super.shortener(url, sponsor, wantQr, request);

        ShortURL miShort = r.getBody();

        if (wantQr){
            logger.info("ESTOY  APUNTO DE PEDIR EL QR");
            String qrCode = QrGenerator.generateQR(miShort.getUri(),request);
            logger.info("EL QR OBTENIDO HA SIDO: " + qrCode);
            miShort.setQrCode(qrCode);
            HttpHeaders h = new HttpHeaders();
            h.setLocation(miShort.getUri());
            r = new ResponseEntity<ShortURL>(miShort, h, HttpStatus.CREATED);
        }

        SafeBrowsing sb = new SafeBrowsing();
        isSafe = sb.safe(url);


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

    /**
     * Redirects to forbiddenAccess html page when the access is restricted
     */
    private ResponseEntity<?> createForbiddenRedirectToResponse() {
        HttpHeaders h = new HttpHeaders();
        String url = "http://localhost:8080/#/forbiddenAccess";
        h.setLocation(URI.create(url));
        return new ResponseEntity<>(h, HttpStatus.FOUND);
    }
}
