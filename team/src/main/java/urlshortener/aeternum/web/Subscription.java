package urlshortener.aeternum.web;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.repository.ShortURLRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RestController
public class Subscription {

    private static final Logger logger = LoggerFactory.getLogger(Subscription.class);
    private Map<String, Boolean> map = new HashMap<>();
    @Autowired
    protected ShortURLRepository shortURLRepository;

    @RequestMapping(value = "/subscription", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listSubscribedUrls (HttpServletRequest request) {

        List<String> subscribedUrls = shortURLRepository.listSubscribedUrls(true);
        List<String> unsubscribedUrls = shortURLRepository.listSubscribedUrls(false);
        JSONObject obj = new JSONObject();
        obj.put("subscribedUrls", subscribedUrls);
        obj.put("unsubscribedUrls", unsubscribedUrls);

        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/subscription/addSubscription", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addSubscribedUrls (@RequestParam("subscribed") String urlSubscribed, HttpServletRequest request) {
        ShortURL s = shortURLRepository.findByTarget(urlSubscribed).get(0);
        s.setSubscribed(true);
        shortURLRepository.update(s);
        logger.info("url subscribed");
        map.put(urlSubscribed, s.getSafe());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/subscription/removeSubscription", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> removeUnsubscribedUrls (@RequestParam("unsubscribed") String urlUnsubscribed, HttpServletRequest request) {
        List<ShortURL> s = shortURLRepository.findByTarget(urlUnsubscribed);
        for (int i = 0; i < s.size(); i++) {
            s.get(i).setSubscribed(false);
            shortURLRepository.update(s.get(i));
        }
        logger.info("url "+urlUnsubscribed+" unsubscribed");
        map.remove(urlUnsubscribed);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/subscription/removeUrl", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> removeSubscribedUrls (@RequestParam("removed") String urlRemoved, HttpServletRequest request) {
        List<ShortURL> s = shortURLRepository.findByTarget(urlRemoved);

        for (int i = 0; i < s.size(); i++) {
            shortURLRepository.delete(s.get(i).getHash());
        }
        logger.info("url "+urlRemoved+" removed");
        map.remove(urlRemoved);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Map<String, Boolean> getMap() {
        return map;
    }
}
