package urlshortener.aeternum.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.common.domain.Matches;
import urlshortener.common.domain.ThreatMatch;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GetInformationPage {
    private static final Logger LOG = LoggerFactory.getLogger(GetInformationPage.class);

    @RequestMapping(value = "/unsafePage", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getInformationPage (HttpServletRequest request) {
        LOG.info("Doing the request to information page...");

        Matches m  = SafeBrowsing.getM();
        List<String> listThreatTypes = new ArrayList<>();
        List<String> listPlatforms = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        if(m.getMatches() != null) {
            ThreatMatch[] t = m.getMatches();
            for (int i = 0; i < t.length; i++) {
                //LOG.info("ListaTypes["+i+"] = " + t[i].getThreatType());
                //LOG.info("ListaPlatform["+i+"] = " + t[i].getPlatformType());
                if (!listThreatTypes.contains(t[i].getThreatType())) {
                    listThreatTypes.add(t[i].getThreatType());
                }
                if (!listPlatforms.contains(t[i].getPlatformType())) {
                    listPlatforms.add(t[i].getPlatformType());
                }
            }

            jsonObject.put("listThreatTypes", listThreatTypes);
            jsonObject.put("listPlatforms", listPlatforms);
            //Meter tambien la url corta,buscar en bbdd?
        }

        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }
}
