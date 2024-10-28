package jeje.work.aeatbe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Optional;
import jeje.work.aeatbe.domian.KakaoProperties;
import jeje.work.aeatbe.domian.KakaoTokenResponsed;
import jeje.work.aeatbe.domian.KakaoUserInfo;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil,
        ObjectMapper objectMapper, KakaoProperties kakaoProperties) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.kakaoProperties = kakaoProperties;
        this.restClient = RestClient.builder().build();
    }


    public Long getUserId(String userId){
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()){
            return user.get().getId();
        }
        return -1L;
    }

    public String createToken(User user) {
        return jwtUtil.createToken(user);
    }

    public boolean validateToken(String token) {
        String userId = jwtUtil.getKakaoId(token);
        return userRepository.findByUserId(userId).isPresent();
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

    public String Login(String accessToken, String refreshToken){
        var uri = "https://kapi.kakao.com/v2/user/me";
        var response = restClient.get()
            .uri(URI.create(uri))
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoUserInfo.class);
        String kakaoId = response.id()+"";
        Optional<User> user = userRepository.findByUserId(kakaoId);
        if(user.isEmpty()){
            User newUser = User.builder().userId(kakaoId).
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
