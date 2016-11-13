package urlshortener.aeternum.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.web.UrlShortenerController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

@RestController
public class QrGenerator {
    private static final Logger LOG = LoggerFactory
        .getLogger(UrlShortenerController.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @RequestMapping(value = "/qr", method = RequestMethod.GET)
    public ResponseEntity<String> generateQR(HttpServletRequest request) {
        Client client = ClientBuilder.newClient();

        String shortURL = request.getHeader("url");

        Response response = client.target("https://chart.googleapis.com/chart?chs=150x150&cht=qr&chl="
            + shortURL).request().get();

        System.out.println(shortURL);
        if(response.getStatus() == 200){
            String qrCode= "https://chart.googleapis.com/chart?chs=150x150&cht=qr&chl=" + shortURL;
            LOG.info("QR code generated");
            return new ResponseEntity<>(qrCode, HttpStatus.CREATED);
        }else{
            LOG.info("Error to get the qr code");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
