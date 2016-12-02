package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import urlshortener.common.domain.User;
import urlshortener.common.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@RestController
public class SignUp {
    private static final Logger LOG = LoggerFactory
        .getLogger(SignUp.class);

    @Autowired
    protected UserRepository userRepository;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@RequestParam("user") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("pass") String pass,
                                           @RequestParam("repass") String repass,
                                           HttpServletRequest request) {

        if ((username == null) || (username.trim().equals(""))) {
            LOG.info("Invalid username.");
            return new ResponseEntity<String>("\"Invalid username.\"",HttpStatus.BAD_REQUEST);
        } else if((pass == null) || (pass.trim().equals(""))) {
            LOG.info("Invalid password.");
            return new ResponseEntity<String>("\"Invalid password.\"",HttpStatus.BAD_REQUEST);
        } else if((repass == null) || !repass.equals(pass)) {
            LOG.info("Passwords doesn't match.");
            return new ResponseEntity<String>("\"Passwords doesn't match.\"",HttpStatus.BAD_REQUEST);
        } else if((email == null) || (email.trim().equals(""))) {
            LOG.info("Invalid email.");
            return new ResponseEntity<String>("\"Invalid email.\"",HttpStatus.BAD_REQUEST);
        } else {
            // check if user exists
            if (userRepository.findByName(username) != null) {
                LOG.info("User already exists.");
                return new ResponseEntity<String>("\"User already exists.\"",HttpStatus.BAD_REQUEST);
            } else {
                User user = new User(username, pass, email, true, new Date(System.currentTimeMillis()));
                user = userRepository.save(user);
                LOG.info(user!=null?"["+user.getName()+"] saved":"["+user.getName()+"] was not saved");
                return new ResponseEntity<String>("\"User successfully created.\"",HttpStatus.CREATED);
            }
        }
    }
}
