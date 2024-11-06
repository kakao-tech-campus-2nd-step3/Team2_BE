package jeje.work.aeatbe.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.TokenResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoUpdateReqeustDTO;
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
    public UserInfoResponseDTO getUserInfo(Long userId){
        User user = findById(userId);
        return UserInfoResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userImageUrl(user.getUserImgUrl())
                .build();

    }


    @Transactional
    public void updateUserInfo(UserInfoUpdateReqeustDTO userInfoUpdateReqeustDto,Long userId){
        User user = findById(userId);
        user.updateInfo(userInfoUpdateReqeustDto.userName(), userInfoUpdateReqeustDto.userImageUrl());
    }


    /**
     * 올바른 리프레시 토큰인지 확인
     * @param refreshToken
     * @return boolean 올바른 리프레시 토큰인지
     */
    public boolean validateRefreshToken(String refreshToken){
        Long userId = jwtUtil.getUserIdForRefreshToken(refreshToken);
        User user = findById(userId);
        return refreshToken.equals(user.getJwtRefreshToken());
    }

    /**
     * userId로 user찾기
     * @param userId
     * @return user
     */
    public User findById(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(()->new UserNotFoundException("잘못된 유저입니다."));
    }

    @Transactional
    public TokenResponseDTO reissueAccessToken(String refreshToken){
        Long userId = jwtUtil.getUserIdForRefreshToken(refreshToken);
        User user = findById(userId);
        String accessToken = jwtUtil.createToken(user);
        if(!jwtUtil.enoughRefreshToken(refreshToken)){
            refreshToken = jwtUtil.createRefreshToken(user);
            user.updateJwtRefreshToken(refreshToken);
        }
        return TokenResponseDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }










}
