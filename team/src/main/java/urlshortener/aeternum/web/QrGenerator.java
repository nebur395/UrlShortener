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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class QrGenerator {
    private static String infoUrl, infoName, infoEmail, infoPhone,infoCompany,infoAdress, infoLevel;
    private static String vCardText, urlVcard;

    private static final Logger LOG = LoggerFactory
        .getLogger(QrGenerator.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    public static String generateQR(URI shortedURL, HttpServletRequest request) {
        Client client = ClientBuilder.newClient();
        String shortURL = shortedURL.toString();

        // VCard Starting Text
        vCardText = "BEGIN:VCARD\r\n" +
                    "VERSION:3.0\r\n";

        // Additions to Vcard


        infoUrl = "URL:" + shortURL +"\n";
        vCardText += infoUrl;

        if ((!request.getParameter("fName").equals("")) || (!request.getParameter("lName").equals(""))){
            infoName = "FN:" + request.getParameter("fName") + " " + request.getParameter("lName")+"\n";
            vCardText += infoName;
        }
        if (!request.getParameter("Email").equals("")){
            infoEmail = "EMAIL:" + request.getParameter("Email")+"\n";
            vCardText += infoEmail;
        }
        if (!request.getParameter("Phone").equals("") && !request.getParameter("Phone").equals("null")){
            infoPhone = "TEL;TYPE=PREF:" + request.getParameter("Phone")+"\n";
            vCardText += infoPhone;
        }
        if (!request.getParameter("Company").equals("")){
            infoCompany =  "ORG:" + request.getParameter("Company")+"\n";
            vCardText += infoCompany;
        }
        if ((!request.getParameter("Street").equals("")) || (!request.getParameter("Zip").equals("") && !request.getParameter("Zip").equals("null")) || (!request.getParameter("City").equals("")) || (!request.getParameter("Country").equals(""))) {
            infoAdress = "ADR:" + request.getParameter("Street") + ";" + request.getParameter("City") + ";" + request.getParameter("Zip") + ";" + request.getParameter("Country") +"\n";
            vCardText += infoAdress;
        }
        LOG.info("Nombre: " + request.getParameter("fName"));
        LOG.info(request.getParameter("Street"));
        LOG.info(request.getParameter("Zip"));
        LOG.info(request.getParameter("City"));
        LOG.info(request.getParameter("Country"));
        // Final text for Vcard
        vCardText += "REV:" + getCurrentTimeStamp() + "\r\n" + "END:VCARD";

        // Taking the correction level selected by the user
        infoLevel = request.getParameter("Level");

        // Enconding of Vcard as an URL object
        try {
            urlVcard = URLEncoder.encode(vCardText, "UTF-8");
        }
        catch (UnsupportedEncodingException e){

        }

        Response response = client.target("https://chart.googleapis.com/chart?chs=500x500&cht=qr&chld=" + infoLevel +"&chl="
            + urlVcard).request().get();

        if(response.getStatus() == 200){
            String qrCode = "\"https://chart.googleapis.com/chart?chs=500x500&cht=qr&chld=" + infoLevel +"&chl=" + urlVcard + "\"";
            LOG.info("QR code generated");
            return qrCode;
        }else{
            LOG.info("Error to get the qr code");
            return "ERROR EN LA PETICION";
        }
    }

    // Method that returns a time Stamp to generate the Vcard
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
