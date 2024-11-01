package jeje.work.aeatbe.service;

import jakarta.transaction.Transactional;
import java.net.URI;
import java.util.Optional;
import jeje.work.aeatbe.domian.KakaoProperties;
import jeje.work.aeatbe.domian.KakaoTokenResponsed;
import jeje.work.aeatbe.domian.KakaoUserInfo;
import jeje.work.aeatbe.dto.Kakao.LogoutResponseDto;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.UserNotFoundException;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.utility.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoService {

    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public KakaoService(KakaoProperties kakaoProperties, UserRepository userRepository, JwtUtil jwtUtil) {
        this.kakaoProperties = kakaoProperties;
        this.restClient = RestClient.builder().build();
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * @param code 인가코드
     * @return KakaoTokenResponsed
     */
    public KakaoTokenResponsed getKakaoTokenResponse(String code){
        var uri = "https://kauth.kakao.com/oauth/token";
        var body = createLoginBody(code);
        var response = restClient.post()
            .uri(URI.create(uri))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .body(KakaoTokenResponsed.class);
        return response;
    }

    private LinkedMultiValueMap<String, String> createLoginBody(String code) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperties.clientId());
        body.add("redirect_uri", kakaoProperties.redirectUrl());
        body.add("code", code);
        return body;
    }

    /**
     * 로그인을한다.
     * @param accessToken 카카오 엑세스 토큰
     * @param refreshToken 카카오 리프레시 토큰
     * @return jwt토큰
     */
    @Transactional
    public String login(String accessToken, String refreshToken){
        var uri = "https://kapi.kakao.com/v2/user/me";
        var response = restClient.get()
            .uri(URI.create(uri))
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoUserInfo.class);
        String userName = response.kakaoAccount().profile().nickname();
        String kakaoId = response.id()+"";
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        if(user.isEmpty()){
            User newUser = User.builder().kakaoId(kakaoId).
                    userName(userName).
                    accessToken(accessToken).
                    refreshToken(refreshToken).
                    build();
            userRepository.save(newUser);
            return jwtUtil.createToken(newUser);
        }
        user.get().kakaoTokenUpdate(accessToken, refreshToken);
        return jwtUtil.createToken(user.get());
    }

    /**
     * 카카오 로그아웃 수행
     * @param userId
     * @return logoutResponseDto
     */
    @Transactional
    public LogoutResponseDto logout(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("확인되지 않은 유저 입니다."));

        var uri = "https://kapi.kakao.com/v1/user/logout";
        var response = restClient.post()
                .uri(URI.create(uri))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + user.getAccessToken())
                .retrieve()
                .body(LogoutResponseDto.class);
        user.kakaoTokenUpdate("","");
        return response;

    }


}
