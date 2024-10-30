package jeje.work.aeatbe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final KakaoService kakaoService;



    public Long getUserId(String kakaoId){
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        if(user.isPresent()){
            return user.get().getId();
        }
        return null;
    }

    public String createToken(User user) {
        return jwtUtil.createToken(user);
    }

    public boolean validateToken(String token) {
        String kakaoId = jwtUtil.getKakaoId(token);
        return userRepository.findByKakaoId(kakaoId).isPresent();
    }





}
