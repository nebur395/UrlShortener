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

    boolean testMail = true;
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
                if(!testMail){
                    isSafe = sf.safe("http://1800shopnet.com");
                }
                else {
                    isSafe = sf.safe(allUrls.get(i));
                }
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

    //To test email sending
    @Scheduled(fixedRate = 50000)
    public void changeUrl() {
        allUrls = shortURLRepository.listAllUrls();
        if(allUrls != null && !allUrls.isEmpty()) {
            testMail = false;
        }
    }
}
