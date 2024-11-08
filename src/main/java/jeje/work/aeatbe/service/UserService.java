package jeje.work.aeatbe.service;


import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.TokenResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoUpdateReqeustDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.entity.UserAllergy;
import jeje.work.aeatbe.entity.UserFreeFrom;
import jeje.work.aeatbe.exception.AllergyCategoryNotFoundException;
import jeje.work.aeatbe.exception.UserNotFoundException;
import jeje.work.aeatbe.repository.AllergyCategoryRepository;
import jeje.work.aeatbe.repository.FreeFromCategoryRepository;
import jeje.work.aeatbe.repository.UserAllergyRepository;
import jeje.work.aeatbe.repository.UserFreeFromRepository;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserAllergyRepository userAllergyRepository;
    private final UserFreeFromRepository userFreeFromRepository;
    private final AllergyCategoryRepository allergyCategoryRepository;
    private final FreeFromCategoryRepository freeFromCategoryRepository;


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
        List<String> allergies = user.getAllergies().stream()
            .map(userAllergy -> userAllergy.getAllergy().getAllergyType())
            .collect(Collectors.toList());
        List<String> freefrom = user.getAllergies().stream()
            .map(userAllergy -> userAllergy.getAllergy().getAllergyType())
            .collect(Collectors.toList());
        return UserInfoResponseDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .userImageUrl(user.getUserImgUrl())
            .allergies(allergies)
            .freefrom(freefrom)
            .build();

    }


    @Transactional
    public void updateUserInfo(UserInfoUpdateReqeustDTO userInfoUpdateReqeustDto,Long userId){
        User user = findById(userId);
        userAllergyRepository.deleteAll(user.getAllergies());
        userFreeFromRepository.deleteAll(user.getFreeFroms());

        userInfoUpdateReqeustDto.allergies().forEach(allergyType -> {
            AllergyCategory allergyCategory = allergyCategoryRepository.findByAllergyType(allergyType)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 알러지 입니다"));
            user.addAllergy(allergyCategory);
        });

        // 새로운 free from 데이터 추가
        userInfoUpdateReqeustDto.freefrom().forEach(freeFromType -> {
            FreeFromCategory freeFromCategory = freeFromCategoryRepository.findByFreeFromType(freeFromType)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 freefrom입니다"));
            user.addFreeFrom(freeFromCategory);
        });
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

    /**
     * 토큰을 재발급 받는다.
     * @param refreshToken
     * @return TokenResponseDTO
     */
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


    /**
     * 토큰들을 쿠키에 담는다.
     * @param tokenResponseDTO
     * @return HttpHeaders
     */
    public HttpHeaders setCookie(TokenResponseDTO tokenResponseDTO){
        ResponseCookie refreshCookie = ResponseCookie.from("Authorization-refreshToken", tokenResponseDTO.refreshToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(3600)
            .domain(".aeat.jeje.work")
            .sameSite("LAX")
            .build();
        ResponseCookie accessCookie = ResponseCookie.from("Authorization-accessToken", tokenResponseDTO.accessToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(3600)
            .domain(".aeat.jeje.work")
            .sameSite("LAX")
            .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        return headers;

    }








}
