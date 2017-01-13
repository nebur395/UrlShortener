package urlshortener.aeternum.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

@Component
public class SendMail {
    private static final Logger logger = LoggerFactory.getLogger(SendMail.class);

    @Value("${user.mail}")
    private String from;
    @Value("${psswd.mail}")
    private String pass;
    @Value("${recipent.mail}")
    private String RECIPIENT;

    public void sendMail(String url) {
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Warning!";
        String body = "The url " + url + " has become unsafe! To be able to visit the page go to http://localhost:8080/#/unsafePage";

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
        logger.info("Correo enviado!");
    }
}
