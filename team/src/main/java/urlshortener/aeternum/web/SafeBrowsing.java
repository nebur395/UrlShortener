package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.web.socket.sockjs.client.Transport;
import urlshortener.common.domain.*;

import javax.websocket.Session;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Component
public class SafeBrowsing {
    private static final Logger LOG = LoggerFactory.getLogger(SafeBrowsing.class);

    private boolean isSafe;

    private static Matches m;

    public SafeBrowsing() {}

    public static Matches getM() {
        return m;
    }


    public boolean safe(String url)  {
        String peticionSafe = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyDG39Zc-4BtPjLR_6gVj7LUJjbGEdV-oqI";

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

    /*public void sendMail() {
        // Common variables
        String host = "localhost";
        String from = "anagonzalor@gmail.com";
        String to = "anagonzalor@gmail.com";

// Set properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.debug", "true");

// Get session
        Session session = Session.getInstance(props);

        try {
            // Instantiate a message
            Message msg = new MimeMessage(session);

            // Set the FROM message
            msg.setFrom(new InternetAddress(from));

            // The recipients can be more than one so we use an array but you can
            // use 'new InternetAddress(to)' for only one address.
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);

            // Set the message subject and date we sent it.
            msg.setSubject("Email from JavaMail test");

            // Set message content
            msg.setText("This is the text for this simple demo using JavaMail.");

            // Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }*/
}
