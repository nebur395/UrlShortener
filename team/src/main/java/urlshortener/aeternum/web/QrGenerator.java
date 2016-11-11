package urlshortener.aeternum.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;


public class QrGenerator {
    @RequestMapping(value = "/qr",method = RequestMethod.GET)
    public String generateQR(@RequestParam("url") String url) {
        Client client = ClientBuilder.newClient();

        Response response = client.target("https://chart.googleapis.com/chart?chs=150x150&cht=qr&chl=" + url).request().get();

        if(response.getStatus() == 200){
            return "https://chart.googleapis.com/chart?chs=150x150&cht=qr&chl=" + url;
        }else{
            return "402";
        }
    }
}
