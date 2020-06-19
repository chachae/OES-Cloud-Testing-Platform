package com.oes.gateway.enhance.auth;

import com.oes.gateway.enhance.entity.RouteUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chachae
 */
@Component
public class JwtTokenHelper implements Serializable {

  private static final long serialVersionUID = 1579222883969867182L;

  @Value("${oes.gateway.jwt.secret}")
  private String secret;

  @Value("${oes.gateway.jwt.expiration}")
  private String expirationTime;

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
        .parseClaimsJws(token)
        .getBody();
  }

  public String getUsernameFromToken(String token) {
    return getAllClaimsFromToken(token).getSubject();
  }

  public Date getExpirationDateFromToken(String token) {
    return getAllClaimsFromToken(token).getExpiration();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(RouteUser routeUser) {
    Map<String, Object> claims = new HashMap<>(1);
    claims.put("permission", routeUser.getRoles());
    return doGenerateToken(claims, routeUser.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String username) {
    long expirationTimeLong = Long.parseLong(expirationTime);

    final Date createdDate = new Date();
    final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
        .compact();
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }
}
