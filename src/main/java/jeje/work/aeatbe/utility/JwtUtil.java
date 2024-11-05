package jeje.work.aeatbe.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.ToekenExpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret.AccessKey}")
    private String secretKey;

    @Value("${jwt.secret.ReFreshKey}")
    private String secretRefreshKey;

    private Long tokenExpTime = 3600000L; //1시간

    private Long refreshExpTime = 1209600000L ; //2주

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

    /**
     * 토큰으로부터 LoginUserInfo를 추철
     * @param token
     * @return LoginUserInfo
     */
    public LoginUserInfo getLoginUserInfo(String token){
        Claims claims = extractClaims(token);
        return LoginUserInfo.builder()
            .userId(claims.get("userId",Long.class))
            .kakaoId(claims.get("kakaoId",String.class))
            .build();
    }

    /**
     * 토큰 유효시간 검증
     * @param token
     * @param isAccessToken
     * @return boolean
     * @throws ToekenExpException
     */
    public boolean validTokenExpiration(String token, boolean isAccessToken) throws ToekenExpException {
        try{
            Claims claims = parseToken(token, isAccessToken);
            return claims.getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }catch (Exception e){
            throw new ToekenExpException("올바르지 않은 토큰");
        }
    }

    /**
     * 토큰 파싱
     * @param token
     * @param isAccessToken
     * @return claims
     */
    private Claims parseToken(String token, boolean isAccessToken){
        if(isAccessToken){
            return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseSignedClaims(token)
                .getBody();
        }
        return Jwts.parser()
            .setSigningKey(secretRefreshKey.getBytes())
            .build()
            .parseSignedClaims(token)
            .getBody();
    }

    /**
     * 리프레시 토큰 생성
     * @param user
     * @return refreshToken
     */
    public String createRefreshToken(User user){
        return Jwts.builder()
            .subject("jwtRefreshToken")
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+refreshExpTime))
            .signWith(Keys.hmacShaKeyFor(secretRefreshKey.getBytes()))
            .claims(createClaims(user))
            .compact();
    }



}
