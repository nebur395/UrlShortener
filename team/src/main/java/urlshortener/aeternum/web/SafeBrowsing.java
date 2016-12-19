package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import urlshortener.common.domain.*;

//@Component
public class SafeBrowsing {
    private static final Logger LOG = LoggerFactory.getLogger(SafeBrowsing.class);

    private boolean isSafe;

    @Value("${googleAPI.apikey}")
    private String API_KEY;

    //private Matches m;
    private static Matches m;

    public SafeBrowsing() {}

//    public Matches getM() {
//        return m;
//    }
    public static Matches getM() {
        return m;
    }



    public boolean safe(String url)  {

        //String peticionSafe = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI";
        String peticionSafe = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=" + API_KEY;

        ObjectMapper mapper = new ObjectMapper();
        ThreatEntry entries = new ThreatEntry(url);
        ThreatInfo info = new ThreatInfo(new ThreatEntry[]{entries});
        ClientInfo client = new ClientInfo("1","1.5.2");

        RestTemplate restTemplate = new RestTemplate();
        Union u = new Union(client, info);

        String jsonUnion = null;
        try {
            jsonUnion = mapper.writeValueAsString(u);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request= new HttpEntity(jsonUnion, headers );
        ResponseEntity<Matches> tm = restTemplate.exchange(peticionSafe, HttpMethod.POST, request, Matches.class);
        m = new Matches(tm.getBody().getMatches());
        if(tm.getStatusCodeValue() == 200) {
            if(tm.getBody().getMatches() == null) {
                System.out.println(tm.getBody().getMatches());
                LOG.info("La URL " + url + " es segura");
                isSafe = true;
            }
            else {
                LOG.info("La URL " + url + " NO es segura");
                isSafe = false;
            }
        }
        return isSafe;
    }



}
