package dev.vandael.jauth.JWT;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  private final String BEARER = "Bearer ";

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String token = retrieveToken(req);
    if (!StringUtils.hasText(token) || !jwtProvider.verifyToken(token)) {
      res.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    filterChain.doFilter(req, res);
  }

  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest req) throws ServletException {
    List<String> allowList = List.of("/api/v1/signup", "/api/v1/login");
    return allowList.contains(req.getRequestURI());
  }

  private String retrieveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken == null || bearerToken.isBlank() || !bearerToken.startsWith(BEARER)) {
      return null;
    }
    return bearerToken.substring(7);
  }
}
