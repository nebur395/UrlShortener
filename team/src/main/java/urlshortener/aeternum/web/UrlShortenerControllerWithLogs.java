package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.common.domain.CountryRestriction;
import urlshortener.common.domain.Location;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.repository.CountryResRepository;
import urlshortener.common.web.UrlShortenerController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Timer;

@RestController
public class UrlShortenerControllerWithLogs extends UrlShortenerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);
    private boolean isSafe;
    List<String> allUrls;
    //Timer time = new Timer(); // Instantiate Timer Object
    ScheduledTask st = new ScheduledTask();

    @Autowired
    protected CountryResRepository countryResRepository;


    @Override
	@RequestMapping(value = "/{id:(?!link|index|app|viewStatistics|qr|signUp|signIn|restrictAccess).*}",
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

	@Override
	public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
											  @RequestParam(value = "sponsor", required = false) String sponsor,
											  HttpServletRequest request) {
		logger.info("Requested new short for uri " + url);
        ResponseEntity<ShortURL> r = super.shortener(url, sponsor, request);

        SafeBrowsing sb = new SafeBrowsing();
        isSafe = sb.safe(url);
        //st = new ScheduledTask(sb);
        //st.setSb(sb);
        //allUrls = shortURLRepository.listAllUrls();
        //st.setAllUrls(allUrls);

        ShortURL miShort = r.getBody();
        shortURLRepository.mark(miShort, isSafe);

        //Read ip client from shortURL and obtain its location info if there is a click with this hash
        if (miShort != null) {
            String ip = miShort.getIP();
            String country = ReadLocation.location(ip).getCountryName();
            System.out.println("pais: "+country);
            CountryRestriction rs = countryResRepository.findCountry(country);
            System.out.println("resticcion: "+rs);
            if(rs != null){
                System.out.println("No se puede acceder");
            }
        }
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
