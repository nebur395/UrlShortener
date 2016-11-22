package urlshortener.aeternum.web;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.crypto.MacProvider;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import urlshortener.common.domain.Matches;
import urlshortener.common.domain.User;
import urlshortener.common.repository.UserRepository;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
public class SignIn {
    private static final Logger LOG = LoggerFactory
        .getLogger(SignIn.class);

    @Autowired
    protected UserRepository userRepository;

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ResponseEntity<String> signIn(HttpServletRequest request) {

        String username = request.getHeader("user");
        String password = request.getHeader("pass");

        if (username == null || password == null) {
            LOG.info("Usuario o contraseña nulo");
            return new ResponseEntity<String>("\"Usuario o contraseña nulo\"",HttpStatus.BAD_REQUEST);
        }
        else {
            User u = userRepository.findByName(username);
            if(u == null) {
                LOG.info("Usuario no existe");
                return new ResponseEntity<String>("\"El usuario no existe\"",HttpStatus.BAD_REQUEST);
            }
            else {
                if(u.getPass().equals(password)) {
                    //Generate secret key
                    SecretKey secretKey = null;
                    try {
                        secretKey = KeyGenerator.getInstance("AES").generateKey();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

                    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
                    byte [] apiKey = DatatypeConverter.parseBase64Binary(encodedKey);
                    Key signingKey = new SecretKeySpec(apiKey, signatureAlgorithm.getJcaName());

                    Claims claims = Jwts.claims().setSubject(username);
                    claims.put("password", password);
                    JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Authorization", builder.toString());
                    System.out.println("Builder: " + builder.toString());
                    return new ResponseEntity<String>("Exito en login",headers, HttpStatus.CREATED);
                }
                else {
                    LOG.info("Contraseña incorrecta");
                    return new ResponseEntity<String>("\"Contraseña incorrecta\"",HttpStatus.BAD_REQUEST);
                }
            }
        }
    }
}
