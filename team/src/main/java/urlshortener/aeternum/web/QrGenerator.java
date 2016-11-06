package urlshortener.aeternum.web;
/**
 * Created by belbus on 6/11/16.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;



@RequestMapping(value = "/qr", method = RequestMethod.GET)
public class QrGenerator {

    public String generateQR(@RequestParam("URL") String url) {
        Client client = ClientBuilder.newClient();

        javax.ws.rs.core.Response response = client.target("https://chart.googleapis.com/chart?" + url).request().get();

        if(response.getStatus() == 200){
            return "redirect:" + "https://chart.googleapis.com/chart?" + url;
        }else{
            return "402";
        }
    }
}
