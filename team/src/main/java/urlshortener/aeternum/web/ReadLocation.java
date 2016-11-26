package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import urlshortener.common.domain.Location;

public class ReadLocation {

    private static final Logger LOG = LoggerFactory.getLogger(ReadLocation.class);

    public static Location location(String ip){

        String request = "http://api.ipinfodb.com/v3/ip-city/?key=f1b4a91ee54084023046c04064c8c20ab563d30bff45f35de7abd127155acb4b&ip="
            +ip+"&format=json";

        //HTTP GET request and extract response with JSON format
        RestTemplate restTemplate = new RestTemplate();
        Location l = restTemplate.getForObject(request, Location.class);
        if(l.getStatusCode().equals("OK")){
            LOG.info("Reading location from "+ip);
            return l;
        }
        else{
            LOG.info("Error reading location: "+l.getStatusCode());
            return  null;
        }
    }
}
