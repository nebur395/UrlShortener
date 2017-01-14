package urlshortener.aeternum.web;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.common.domain.Matches;
import urlshortener.common.domain.ThreatMatch;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GetInformationPage {
    private static final Logger LOG = LoggerFactory.getLogger(GetInformationPage.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected SafeBrowsing safebrowsing;

    @RequestMapping(value = "/unsafePage", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getInformationPage () {
        LOG.debug("Doing the request to information page...");

        Matches m  = safebrowsing.getM();
        List<String> listThreatTypes = new ArrayList<>();
        List<String> listPlatforms = new ArrayList<>();
        String url = "";
        JSONObject jsonObject = new JSONObject();

        if(m.getMatches() != null) {
            ThreatMatch[] t = m.getMatches();
            for (int i = 0; i < t.length; i++) {
                if (!listThreatTypes.contains(t[i].getThreatType())) {
                    listThreatTypes.add(t[i].getThreatType());
                }
                if (!listPlatforms.contains(t[i].getPlatformType())) {
                    listPlatforms.add(t[i].getPlatformType());
                }
                url = t[0].getThreat().getUrl();
            }

            jsonObject.put("listThreatTypes", listThreatTypes);
            jsonObject.put("listPlatforms", listPlatforms);
            jsonObject.put("url", url);
        }
        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }
}
