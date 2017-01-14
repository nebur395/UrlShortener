package urlshortener.aeternum.web;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.common.repository.CountryResRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RestrictCountry {

    private static final Logger LOG = LoggerFactory.getLogger(RestrictCountry.class);

    @Autowired
    protected CountryResRepository countryResRepository;

    /**
     * Returns the response with JSONobject which contains two list with
     * blocked and unblocked countries
     */
    @RequestMapping(value = "/restrictAccess", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getListOfCountries(HttpServletRequest request) {
        List<String> unblockedCountries = countryResRepository.listCountries(true);
        List<String> blockedCountries = countryResRepository.listCountries(false);

        //Create JSON object and add both lists
        JSONObject obj = new JSONObject();
        obj.put("unblockList", unblockedCountries);
        obj.put("blockList", blockedCountries);

        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    /**
     * Returns the response with true if the country has been blocked
     */
    @RequestMapping(value = "/restrictAccess/blockCountry", method = RequestMethod.POST)
    public ResponseEntity<Boolean> blockCountry(@RequestParam("unblocked") String country) {
        Boolean blocked = countryResRepository.restrictCountry(country);
        LOG.debug("Access from "+country+" blocked");
        return new ResponseEntity<>(blocked, HttpStatus.CREATED);
    }

    /**
     * Returns the response with true if the country has been unblocked
     */
    @RequestMapping(value = "/restrictAccess/unblockCountry", method = RequestMethod.POST)
    public ResponseEntity<Boolean> unblockCountry(@RequestParam("blocked") String country) {
        Boolean unblocked = countryResRepository.unblockCountry(country);
        LOG.debug("Access from "+country+" unblocked");
        return new ResponseEntity<>(unblocked, HttpStatus.CREATED);
    }

    /**
     * Returns the response with true if the country has been unblocked
     */
    @RequestMapping(value = "/restrictAccess/updateFrequency", method = RequestMethod.POST)
    public ResponseEntity<Boolean> updateFrequency(@RequestParam("updatedCountry") String country,
                                                   @RequestParam("request") Integer req,
                                                   @RequestParam("time") Integer time,
                                                 HttpServletRequest request) {
        if(country==null || req==null || time==null){
            return new ResponseEntity<>(false,HttpStatus.NO_CONTENT);
        }else{
            Boolean updated = countryResRepository.updateFrequency(country,req,time);
            if (updated) LOG.debug("Update from "+country+": request "+req+" time "+time);
            return new ResponseEntity<>(updated,HttpStatus.CREATED);
        }
    }
}
