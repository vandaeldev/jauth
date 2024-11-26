package dev.vandael.jauth.JWT;

import java.time.Duration;
import java.time.Instant;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.vandael.jauth.User.User;

@Component
public class JWTProvider {
  public String generateToken(User user) {
    Instant now = Instant.now();
    return JWT.create().withIssuer("dev.vandael.jauth").withIssuedAt(now).withNotBefore(now)
        .withExpiresAt(now.plus(Duration.ofMinutes(30))).withSubject(user.getEmail())
        .withClaim("id", user.getId()).sign(Algorithm.HMAC256("secret"));
  }

  public boolean verifyToken(String token) {
    try {
      return JWT.require(Algorithm.HMAC256("secret")).withIssuer("dev.vandael.jauth")
          .withClaimPresence("id").build().verify(token) != null;
    } catch (JWTVerificationException _) {
      return false;
    }
  }
}
