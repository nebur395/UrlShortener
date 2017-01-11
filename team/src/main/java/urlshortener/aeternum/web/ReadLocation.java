package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import urlshortener.common.domain.Location;

@Component
public class ReadLocation {

    private final Logger LOG = LoggerFactory.getLogger(ReadLocation.class);

    @Value("${ipinfodb.api_key}")
    private String API_KEY;

    /**
     * Returns info about location of client
     */
    public Location location(){
        String request = "http://api.ipinfodb.com/v3/ip-city/?key="+API_KEY+"&format=json";

        //HTTP GET request and extract response with JSON format
        RestTemplate restTemplate = new RestTemplate();
        Location l = restTemplate.getForObject(request, Location.class);
        if(l.getStatusCode().equals("OK")){
            LOG.info("Reading location");
            return l;
        }
        else{
            LOG.info("Error reading location: "+l.getStatusCode());
            return null;
        }
    }
}
