package jeje.work.aeatbe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.UserInfoResponseDto;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.UserNotFoundException;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final KakaoService kakaoService;


    /**
     * 카카오 id로 유저 id(pk)를 반환
     * @param kakaoId
     * @return Long 유저의 id
     */
    public Long getUserId(String kakaoId){
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        if(user.isPresent()){
            return user.get().getId();
        }
        return null;
    }

    /**
     * 주어진 jwt토큰을 검증하여 이미 있는 유저인지 확인한다.
     * @param token
     * @return boolean 이미 존재하는 유저인지
     */
    public boolean validateToken(String token) {
        LoginUserInfo loginUserInfo= jwtUtil.getLoginUserInfo(token);
        return userRepository.findByKakaoId(loginUserInfo.kakaoId()).isPresent();
    }

    /**
     * 유저 정보를 반환한다,
     * @param userId
     * @return UserInfoResponseDto
     */
    public UserInfoResponseDto getUserInfo(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("잘못된 유저입니다."));
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userImageUrl(user.getUserImgUrl())
                .build();

    }





}
