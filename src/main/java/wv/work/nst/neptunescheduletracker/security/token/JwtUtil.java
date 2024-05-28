package wv.work.nst.neptunescheduletracker.security.token;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import wv.work.nst.neptunescheduletracker.entity.User;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String secretKey = "randomsecret";

    private final JwtParser jwtParser;

    private final String TOKEN_PREFIX = "Bearer ";
    private final String TOKEN_HEADER = "Authorization";

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(secretKey);
    }

    //refresh token
    public String generateExpiryToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(TOKEN_HEADER, TOKEN_PREFIX + user.getEmail());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //authorization token
    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(TOKEN_HEADER, TOKEN_PREFIX + user.getEmail());
        Date tokenCreateTime = new Date();
        Date expirationDate = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(15));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if (token != null) {
                return parseClaims(token);
            }
            return null;
        } catch (ExpiredJwtException e) {
            request.setAttribute("expired", e.getMessage());
            throw e;
        } catch (Exception e) {
            request.setAttribute("invalid", e.getMessage());
            throw e;
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
