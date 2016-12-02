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
import org.springframework.objenesis.instantiator.sun.MagicInstantiator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.common.domain.Matches;
import urlshortener.common.domain.ThreatMatch;

import javax.ws.rs.GET;
import java.util.List;

@RestController
public class GetInformationPage {
    private static final Logger LOG = LoggerFactory.getLogger(GetInformationPage.class);

    @RequestMapping(value = "/unsafePage", method = RequestMethod.GET)
    public ResponseEntity<String>pageRequest () {
        LOG.info("Doing the request to information page...");

        ObjectMapper mapper = new ObjectMapper();

        Matches m  = SafeBrowsing.getM();
        List<String> listThreatTypes = null;
        List<String> listPlatforms = null;

        if(m != null) {
            ThreatMatch[] t = m.getMatches();
            for(int i = 0; i < t.length; i++) {
                if(!listThreatTypes.contains(t[i].getThreatType())) listThreatTypes.add(t[i].getThreatType()); //mirar si no esta repetido
                if(!listPlatforms.contains(t[i].getPlatformType())) listPlatforms.add(t[i].getPlatformType());
            }
            //net.minidev:json-smart:2.2.1
        }
        JSONObject j = new JSONObject();
        SafeBrowsing s = new SafeBrowsing();
        j.put("listThreatTypes", listThreatTypes);
        j.put("listPlatforms", listPlatforms);
        String sa = t[1].getThreatType();
        System.out.println("THREAT TYPE " + sa);
        //Crear JSON object con las string que me interesan.
        //Crear una lista para threatType y otra para platform y se van añadiendo los que me salgan ahi

        System.out.println(t[1].getPlatformType());
        String jsonObject = null;
        try {
            jsonObject = mapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = mapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.CREATED);
    }
}
