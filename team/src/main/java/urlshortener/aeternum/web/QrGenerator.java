package urlshortener.aeternum.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

@RequestMapping(value = "/qr", method = RequestMethod.GET)
public class QrGenerator {

    public String generateQR(@RequestParam("URL") String url) {
        Client client = ClientBuilder.newClient();

        Response response = client.target("https://chart.googleapis.com/chart?cht=qr&chl=" + url).request().get();

        if(response.getStatus() == 200){
            return "redirect:" + "https://chart.googleapis.com/chart?" + url;
        }else{
            return "402";
        }
    }
}
