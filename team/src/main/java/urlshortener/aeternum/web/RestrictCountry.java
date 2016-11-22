package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import urlshortener.common.domain.CountryRestriction;
import urlshortener.common.repository.CountryResRepository;

import javax.annotation.PostConstruct;

public class RestrictCountry {

    private static final Logger log = LoggerFactory.getLogger(RestrictCountry.class);

    @Autowired
    protected static CountryResRepository countryResRepository;

    private String country;

    /*CountryRestriction c = new CountryRestriction("Spain", false);
        countryResRepository.save(c);*/

    public RestrictCountry(String country) {
        this.country = country;
    }


   /* @PostConstruct
    public static void initializeCountryTable(){
        System.out.println("me ejecuto al inicio");
        String[] countries = {"spain", "italy", "ireland", "japan"};
        CountryRestriction c;
        for(int i=0; i<countries.length; i++){
            c = new CountryRestriction(countries[i], true);
            System.out.println(c.toString());
            countryResRepository.save(c);
        }
        log.info("Initialized countryrestriction table");

    }
*/
    public void restrictCountry(String country){
        System.out.println("restingir pais: "+ country);
        countryResRepository.restrictCountry(country);
    }
}
