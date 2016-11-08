package urlshortener.aeternum.web;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ReadLocation {

    //KEY: f1b4a91ee54084023046c04064c8c20ab563d30bff45f35de7abd127155acb4b

    private String ip;

    public ReadLocation(String ip) {
        this.ip = ip;
    }

    //@GET
    //@Produces(MediaType.APPLICATION_JSON)
    public void location(){
        Client client = ClientBuilder.newClient();
        String request = "http://api.ipinfodb.com/v3/ip-city/?key=f1b4a91ee54084023046c04064c8c20ab563d30bff45f35de7abd127155acb4b&ip="
            +ip+"&format=json";
        Response response = client.target(request).request(MediaType.APPLICATION_JSON,MediaType.CHARSET_PARAMETER).get();

        //If the status is OK
        if(response.getStatus() == 200){
            System.out.println("mediaType: "+response.getMediaType());
            Location l = response.readEntity(Location.class);
            /*System.out.println("latitude: "+l.getLatitude());
            System.out.println("longitude: "+l.getLongitude());*/
        }
        else{
            System.out.println("El estado de la respuesta es: "+response.getStatus());
        }
    }
}
