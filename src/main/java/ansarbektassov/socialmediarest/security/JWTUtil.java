package ansarbektassov.socialmediarest.security;

import ansarbektassov.socialmediarest.models.Person;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    public String generateToken(Person person) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username",person.getUsername())
                .withClaim("email",person.getEmail())
                .withClaim("name",person.getName())
                .withIssuedAt(new Date())
                .withIssuer("ansarbektassov")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET))
                .withSubject("User details")
                .withIssuer("ansarbektassov")
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        System.out.println("JWT Passed");
        return jwt.getClaim("username").asString();
    }
}
