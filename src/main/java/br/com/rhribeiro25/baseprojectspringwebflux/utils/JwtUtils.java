package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Class JWT Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 06/28/2021
 */
@Slf4j
@Component
public class JwtUtils {

    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private long EXPIRATION_TIME;
    private String JWT_SECRET;

    public JwtUtils(Environment env) {
        EXPIRATION_TIME = Long.parseLong(env.getProperty("base.project.spring.webflux.expiration.time"));
        JWT_SECRET = env.getProperty("base.project.spring.webflux.secret");
    }

    public String getSubjectFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpiryDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public String createToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getFirstName())
                .setSubject(user.getEmail())
                .setSubject(user.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            var tokenOnly = token.substring(0, token.lastIndexOf('.') + 1);
            return Jwts.parser().parseClaimsJws(tokenOnly).getBody();
        } catch (ExpiredJwtException ex) {
            logger.error("GetAllClaimsFromToken error='{}' ", ex.getClaims());
            return ex.getClaims();
        } catch (Exception e) {
            logger.error("GetAllClaimsFromToken error='{}' ", e.getMessage());
            return null;
        }
    }

    public Boolean validTokenExpired(String token) {
        try {
            var tokenOnly = token.substring(0, token.lastIndexOf('.') + 1);
            Jwts.parser().parse(tokenOnly).getBody();
            return true;
        } catch (Exception e) {
            logger.error("GetAllClaimsFromToken error='{}' ", e.getMessage());
            return false;
        }
    }

    public boolean validateToken(String token) {
        return getExpiryDateFromToken(token).after(new Date());
    }
}
