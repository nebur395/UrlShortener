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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Timer;

@RestController
public class UrlShortenerControllerWithLogs extends UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);
    private boolean isSafe;
    //Timer time = new Timer(); // Instantiate Timer Object
    ScheduledTask st = new ScheduledTask();
    private static final Logger LOG = LoggerFactory
        .getLogger(QrGenerator.class);

    @Autowired
    protected CountryResRepository countryResRepository;


    @Override
	@RequestMapping(value = "/{id:(?!link|index|app|viewStatistics|qr|signUp|signIn|unsafePage|restrictAccess).*}",
        method = RequestMethod.GET)
	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
		logger.info("Requested redirection with hash " + id);
		ResponseEntity<?> r = super.redirectTo(id, request);

        ShortURL s = shortURLRepository.findByKey(id);
        //Read ip client from shortURL and obtain its location info if there is a click with this hash
        if (s != null) {
            String ip = s.getIP();
            Location loc = ReadLocation.location(ip);
            updateLocation(s, loc);
        }
        return r;
	}

    @RequestMapping(value = "/checkRegion", method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkRegion (HttpServletRequest request){
        String ip = request.getRemoteAddr();
        Boolean regionAvaiable = new Boolean(false);

        ip = "62.101.181.50";
        //Read ip client from shortURL and obtain its location info if there is a click with this hash
        if (ip != null) {
            String country = ReadLocation.location(ip).getCountryName();
            CountryRestriction rs = countryResRepository.findCountry(country);
            if(rs.isaccessAllowed()){
                regionAvaiable = true;
                logger.info("Access allowed");
            }else{
                logger.info("Access denied");
            }
        }
        return new ResponseEntity<>(regionAvaiable, HttpStatus.OK);
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

        LOG.info("OBJETO JSON?:" + r.toString());

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
