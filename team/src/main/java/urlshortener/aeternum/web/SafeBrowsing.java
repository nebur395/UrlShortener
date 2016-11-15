package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import urlshortener.common.domain.*;

public class SafeBrowsing {
    private static final Logger LOG = LoggerFactory.getLogger(SafeBrowsing.class);

    private boolean isSafe;

    public SafeBrowsing() {}

    /*public void listasInseguras() {
        System.out.println("Listas inseguras..");
        String peticionSafe = "https://safebrowsing.googleapis.com/v4/threatLists?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(headers);
        //HTTP GET request and extract response with JSON format
        RestTemplate restTemplate = new RestTemplate();
        //ThreatMatch tm = restTemplate.getForObject(peticionSafe, ThreatMatch.class);
        ResponseEntity<ThreatInfo> r = restTemplate.exchange(peticionSafe, HttpMethod.GET, entity, ThreatInfo.class);

        if (r.getStatusCodeValue() == 200) {
            System.out.println("Peticion correcta");
            System.out.println(r.getBody().getPlatformTypes());
            System.out.println(r.getBody().getThreatEntries());
            System.out.println(r.getBody().getThreatEntryTypes());
            System.out.println(r.getBody().getThreatEntries().getUrl());
        }
        else System.out.println("Peticion incorrecta");
    }
*/
    public boolean safe(String url) throws JsonProcessingException {

        String peticionSafe = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI";
        ObjectMapper mapper = new ObjectMapper();
        ThreatEntries entries = new ThreatEntries(url);
        ThreatInfo info = new ThreatInfo(entries);
        ClientInfo client = new ClientInfo("100","1.5.2");

        RestTemplate restTemplate = new RestTemplate();
        Union u = new Union(client, info);

        String jsonUnion = mapper.writeValueAsString(u);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request= new HttpEntity(jsonUnion, headers );

        //System.out.println(jsonUnion);
        //ThreatMatch tm = restTemplate.postForObject(peticionSafe, request, ThreatMatch.class);
        ResponseEntity<ThreatMatch> tm = restTemplate.exchange(peticionSafe, HttpMethod.POST, request, ThreatMatch.class);

        if(tm.getStatusCodeValue() == 200) {
            LOG.info("Ha ido bien la peticion");
            //String jsonRespuesta = mapper.writeValueAsString(tm);
            //System.out.println(jsonRespuesta);
            //System.out.println(tm.getBody());
            if(tm.getBody().getTe() == null) {
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
