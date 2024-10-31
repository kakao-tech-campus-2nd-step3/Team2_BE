package jeje.work.aeatbe.service;

import jakarta.transaction.Transactional;
import java.net.URI;
import java.util.Optional;
import jeje.work.aeatbe.domian.KakaoProperties;
import jeje.work.aeatbe.domian.KakaoTokenResponsed;
import jeje.work.aeatbe.domian.KakaoUserInfo;
import jeje.work.aeatbe.entity.User;
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

    public KakaoTokenResponsed getKakaoTokenResponse(String code){
        var uri = "https://kauth.kakao.com/oauth/token";
        var body = createBody(code);
        var response = restClient.post()
            .uri(URI.create(uri))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .body(KakaoTokenResponsed.class);
        return response;
    }

    private LinkedMultiValueMap<String, String> createBody(String code) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperties.clientId());
        body.add("redirect_uri", kakaoProperties.redirectUrl());
        body.add("code", code);
        return body;
    }

    @Transactional
    public String Login(String accessToken, String refreshToken){
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


}
