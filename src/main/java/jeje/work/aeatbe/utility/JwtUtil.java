package jeje.work.aeatbe.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Long tokenExpTime = 3600000L;

    /**
     * 토큰을 만든다.
     * @param user
     * @return 토큰
     */
    public String createToken(User user){
        return Jwts.builder()
            .subject("jwtAccessToken")
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+tokenExpTime))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .claims(createClaims(user))
            .compact();
    }

    /**
     * jwt에 들어갈 claim 생성
     * @param user
     * @return claims
     */
    public Map<String,Object> createClaims(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("kakaoId",user.getKakaoId());
        return claims;
    }

    /**
     * 토큰 내용을 분석
     * @param token
     * @return claims
     */
    public Claims extractClaims(String token){
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        return Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .build()
            .parseSignedClaims(token)
            .getBody();
    }

    /**
     * 토큰으로부터 카카오ID를 추출
     * @param token
     * @return kakaoId
     */
    public String getKakaoId(String token){
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public LoginUserInfo getLoginUserInfo(String token){
        Claims claims = extractClaims(token);
        return LoginUserInfo.builder()
            .userId(claims.get("userId",Long.class))
            .kakaoId(claims.get("kakaoId",String.class))
            .build();
    }

}
