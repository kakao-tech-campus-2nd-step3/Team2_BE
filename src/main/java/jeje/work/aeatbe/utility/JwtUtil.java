package jeje.work.aeatbe.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.TokenException;
import jeje.work.aeatbe.exception.TokenExpException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey refreshSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final Long tokenExpTime = 3600000L; //1시간
    private final Long refreshExpTime = 1209600000L; //2주

    /**
     * 토큰을 만든다.
     *
     * @param user
     * @return 토큰
     */
    public String createToken(User user) {
        return Jwts.builder()
                .subject("jwtAccessToken")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claims(createClaims(user))
                .claim("token_type", "access")
                .compact();
    }


    /**
     * jwt에 들어갈 claim 생성
     *
     * @param user
     * @return claims
     */
    public Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("kakaoId", user.getKakaoId());
        return claims;
    }


    /**
     * 토큰으로부터 userId를 추출
     *
     * @param accessToken
     * @return userId
     */
    public Long getUserIdForAccessToken(String accessToken) throws TokenException {
        Claims claims = parseToken(accessToken, true);
        return claims.get("userId", Long.class);
    }

    /**
     * 토큰으로부터 userId를 추출
     *
     * @param refreshToken
     * @return userId
     */
    public Long getUserIdForRefreshToken(String refreshToken) throws TokenException{
        Claims claims = parseToken(refreshToken, false);
        return claims.get("userId", Long.class);
    }

    /**
     * 토큰으로부터 LoginUserInfo를 추철
     *
     * @param token
     * @return LoginUserInfo
     */
    public LoginUserInfo getLoginUserInfo(String token) throws TokenException{
        Claims claims = parseToken(token, true);
        return LoginUserInfo.builder()
                .userId(claims.get("userId", Long.class))
                .kakaoId(claims.get("kakaoId", String.class))
                .build();
    }

    /**
     * 토큰 유효시간 검증
     *
     * @param token
     * @param isAccessToken
     * @return boolean
     * @throws TokenExpException
     */
    public boolean validTokenExpiration(String token, boolean isAccessToken) throws TokenExpException {
        try {
            Claims claims = parseToken(token, isAccessToken);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw new TokenExpException("올바르지 않은 토큰");
        }
    }

    /**
     * 토큰 파싱
     *
     * @param token
     * @param isAccessToken
     * @return claims
     */
    private Claims parseToken(String token, boolean isAccessToken) throws TokenException {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            if (isAccessToken) {
                return Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getBody();
            }
            return Jwts.parser()
                    .setSigningKey(refreshSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getBody();
        }
        catch(SignatureException e){
            throw new TokenException("인식 할 수 없는 토큰입니다.");
        }
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param user
     * @return refreshToken
     */
    public String createRefreshToken(User user) {
        return Jwts.builder()
                .subject("jwtRefreshToken")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpTime))
                .signWith(refreshSecretKey, SignatureAlgorithm.HS256)
                .claims(createClaims(user))
                .claim("token_type", "refresh")
                .compact();
    }


    /**
     * 리프레시 토큰 유효기간이 3일 이상남았는지
     *
     * @param refreshToken
     * @return boolean 3일이상이면 true
     */
    public boolean enoughRefreshToken(String refreshToken) throws TokenException{
        Claims claims = parseToken(refreshToken, false);
        Date expiration = claims.getExpiration();
        Date nowTime = new Date();
        long remainTime = (expiration.getTime() - nowTime.getTime()) / (1000 * 60 * 60 * 24);
        return remainTime > 3;
    }


}