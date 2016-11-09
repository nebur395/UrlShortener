package urlshortener.aeternum.web;

import org.springframework.web.client.RestTemplate;

public class ReadLocation {

    private String ip;

    public ReadLocation(String ip) {
        this.ip = ip;
    }

    public void location(){

        String request = "http://api.ipinfodb.com/v3/ip-city/?key=f1b4a91ee54084023046c04064c8c20ab563d30bff45f35de7abd127155acb4b&ip="
            +ip+"&format=json";

        //HTTP GET request and extract response with JSON format
        RestTemplate restTemplate = new RestTemplate();
        Location l = restTemplate.getForObject(request, Location.class);

        if(l.getStatusCode().equals("OK")){
            System.out.println("latitude: "+l.getLatitude());
            System.out.println("longitude: "+l.getLongitude());
        }
        else{
            System.out.println("El estado de la respuesta es: "+l.getStatusCode());
        }
    }
}
