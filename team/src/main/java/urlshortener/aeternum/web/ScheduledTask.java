package urlshortener.aeternum.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import urlshortener.common.repository.ShortURLRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    protected ShortURLRepository shortURLRepository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    SafeBrowsing sb;
    List<String> allUrls;

    public ScheduledTask() {
    }

    public SafeBrowsing getSb() {
        return sb;
    }

    public void setSb(SafeBrowsing sb) {
        this.sb = sb;
    }

    public List<String> getAllUrls() {
        return allUrls;
    }

    public void setAllUrls(List<String> allUrls) {
        this.allUrls = allUrls;
    }

    @Override
    public String toString() {
        return "ScheduledTask{" +
            "allUrls=" + allUrls +
            '}';
    }

    @Scheduled(fixedRate = 10000)
    public void checkIsSafe() {
        //Manera de quitar este sb de aqui? Poniendolo como atributo de la clase lo reconoce como null
        SafeBrowsing sb = new SafeBrowsing();
        //Poder pasarle este parametro sin que sea null todo el rato?
        allUrls = shortURLRepository.listAllUrls();
        System.out.println("allurls: " + this.allUrls);
        if(allUrls != null && !allUrls.isEmpty()) {
            for (int i = 0; i <= allUrls.size()-1; i++) {
                sb.safe(allUrls.get(i));
            }
        }
        else {
            System.out.println("Lista vacia de urls");
        }
    }

}
