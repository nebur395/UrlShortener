package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import urlshortener.common.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Logout {
    private static final Logger LOG = LoggerFactory
        .getLogger(Logout.class);

    @Autowired
    protected UserRepository userRepository;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String userName = request.getHeader("jwt");
        if (true) {// TODO
            LOG.info("System statistics");
            return new ResponseEntity<String>(HttpStatus.CREATED);
        } else {
            LOG.info("Error to get the system statistics");
            return new ResponseEntity<String>("\"Usuario no creado\"",HttpStatus.BAD_REQUEST);
        }
    }
}
