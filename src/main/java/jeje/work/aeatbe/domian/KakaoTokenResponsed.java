package jeje.work.aeatbe.domian;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenResponsed(
        String accessToken,
        String tokenType,
        String refreshToken,
        String scope,
        int expiresIn,
        int refreshTokenExpiresIn
) {

}
