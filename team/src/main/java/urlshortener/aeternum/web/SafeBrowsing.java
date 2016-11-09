package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import urlshortener.common.domain.ShortURL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import org.springframework.http.HttpEntity;

import javax.ws.rs.core.Response;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SafeBrowsing {

    public SafeBrowsing() {}


//git branch -d nameBranch

    public void safe(String url) throws JsonProcessingException {
/*

        Client client = ClientBuilder.newClient();
        Response response = client.target("https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI").request(MediaType.APPLICATION_JSON).post(Entity.entity(u, MediaType.APPLICATION_JSON));
*/
        ObjectMapper mapper = new ObjectMapper();
        ThreatEntries entries = new ThreatEntries(url);
        ThreatInfo info = new ThreatInfo(entries);
        ClientInfo client = new ClientInfo("id","1.5.2");

        RestTemplate restTemplate = new RestTemplate();
        Union u = new Union(client, info);

        String jsonUnion = mapper.writeValueAsString(u);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON );

        HttpEntity request= new HttpEntity(jsonUnion, headers );

        System.out.println(jsonUnion);
        ThreatMatch tm = restTemplate.postForObject("https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI", request, ThreatMatch.class);
        System.out.println("despues");
        System.out.println("REALIZADA PETICION");

        //if(tm.getStatusCode())
        //System.out.println(tm.getTe().getUrl());


    }
}
