package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.repository.ShortURLRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTask {

    boolean saaaf = true;
    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected Subscription subscription;

    @Autowired
    protected SendMail sm;

    @Autowired
    protected SafeBrowsing sf;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    List<String> allUrls;
    boolean isSafe;

    public ScheduledTask() {
    }

    @Override
    public String toString() {
        return "ScheduledTask{" +
            "allUrls=" + allUrls +
            '}';
    }

    @Scheduled(fixedRate = 10000)
    public void checkIsSafe() {
        allUrls = shortURLRepository.listAllUrls();
        if(allUrls != null && !allUrls.isEmpty()) {
            for (int i = 0; i <= allUrls.size()-1; i++) {
                isSafe = sf.safe(allUrls.get(i));
                //Para testear el envio del email
                //isSafe = saaaf;
                ShortURL s = shortURLRepository.findByTarget(allUrls.get(i)).get(0);
                shortURLRepository.mark(s, isSafe);
                if (!subscription.getMap().isEmpty() && subscription.getMap().containsKey(allUrls.get(i))) {
                    if (isSafe != subscription.getMap().get(allUrls.get(i)) && subscription.getMap().get(allUrls.get(i))) { //url ha cambiado
                        System.out.println("Envio mail..");
                        subscription.getMap().put(allUrls.get(i), false);
                        sm.sendMail(allUrls.get(i));
                    }
                }
            }
        }
    }
    /*
    *Para testear el envio del email
    @Scheduled(fixedRate = 50000)
    public void changeUrl() {
        System.out.println("Haciendo cosas malas...");
        allUrls = shortURLRepository.listAllUrls();
        if(allUrls != null && !allUrls.isEmpty()) {
            saaaf = false;
        }
    }*/
}
