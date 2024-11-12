package jeje.work.aeatbe.domian;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kakao")
public record KakaoProperties(
        String clientId,
        String redirectUrl,
        String authUrl,
        String logoutUrl,
        String logoutRedirectUrl
) {

}
