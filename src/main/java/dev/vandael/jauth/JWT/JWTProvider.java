package dev.vandael.jauth.JWT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.vandael.jauth.User.User;

@Component
public class JWTProvider {
  private final File keysDir = new File("keys");
  private final Path pubKeyPath = Paths.get(keysDir.getPath(), "pub.pem");
  private final Path privKeyPath = Paths.get(keysDir.getPath(), "priv.pem");

  public JWTProvider() throws NoSuchAlgorithmException, IOException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(4096);
    KeyPair keyPair = keyPairGenerator.genKeyPair();
    keysDir.mkdir();
    Files.write(pubKeyPath, keyPair.getPublic().getEncoded());
    Files.write(privKeyPath, keyPair.getPrivate().getEncoded());
  }

  public String generateToken(User user)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    RSAPublicKey pubKey = retrievePublicKey(pubKeyPath);
    RSAPrivateKey privKey = retrievePrivateKey(privKeyPath);
    Instant now = Instant.now();
    return JWT.create().withIssuer("dev.vandael.jauth").withIssuedAt(now).withNotBefore(now)
        .withExpiresAt(now.plus(Duration.ofMinutes(30))).withSubject(user.getEmail())
        .withClaim("id", user.getId()).sign(Algorithm.RSA512(pubKey, privKey));
  }

  public boolean verifyToken(String token) {
    try {
      RSAPublicKey pubKey = retrievePublicKey(pubKeyPath);
      RSAPrivateKey privKey = retrievePrivateKey(privKeyPath);
      return JWT.require(Algorithm.RSA512(pubKey, privKey)).withIssuer("dev.vandael.jauth")
          .withClaimPresence("id").build().verify(token) != null;
    } catch (Exception _) {
      return false;
    }
  }

  private RSAPublicKey retrievePublicKey(Path path)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    X509EncodedKeySpec spec = new X509EncodedKeySpec(Files.readAllBytes(path));
    return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
  }

  private RSAPrivateKey retrievePrivateKey(Path path)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Files.readAllBytes(path));
    return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
  }
}
