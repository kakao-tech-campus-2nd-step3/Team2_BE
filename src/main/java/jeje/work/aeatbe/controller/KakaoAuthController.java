package jeje.work.aeatbe.controller;

import jakarta.servlet.http.HttpServletResponse;
import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.domian.KakaoProperties;
import jeje.work.aeatbe.domian.KakaoTokenResponsed;
import jeje.work.aeatbe.dto.Kakao.LogoutResponseDto;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.TokenResponseDTO;
import jeje.work.aeatbe.service.KakaoService;
import jeje.work.aeatbe.service.TokenService;
import jeje.work.aeatbe.service.UserService;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final KakaoProperties kakaoProperties;
    private final KakaoService kakaoService;
    private final TokenService tokenService;

    /**
     * 카카오 로그인페이지로 리다이렉션
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/login")
    public void redirectKakaoLogin(HttpServletResponse response) throws IOException {
        String url = kakaoProperties.authUrl() +
                "?scope=talk_message,profile_nickname&response_type=code&client_id=" + kakaoProperties.clientId() +
                "&redirect_uri=" + kakaoProperties.redirectUrl();
        response.sendRedirect(url);
    }

    /**
     * 카카오 로그인후 jwt토큰 발급
     *
     * @param code
     * @return httpHeader(Cookie)
     */
    @GetMapping("/callback")
    public ResponseEntity<TokenResponseDTO> getAccessToken(@RequestParam String code) {
        KakaoTokenResponsed token = kakaoService.getKakaoTokenResponse(code);
        TokenResponseDTO tokenResponseDto = kakaoService.login(token.accessToken(), token.refreshToken());
        HttpHeaders httpHeaders = userService.setCookie(tokenResponseDto);
        return ResponseEntity.ok().body(tokenResponseDto);
    }

    /**
     * 카카오 로그아웃후 카카오계정과 함께 로그아웃으로 리다이렉션
     *
     * @param response
     * @param loginUserInfo
     * @throws IOException
     */
    @PostMapping("/logout")
    public void logout(HttpServletResponse response, @RequestHeader("Authorization") String token, @LoginUser LoginUserInfo loginUserInfo) throws IOException {
        String url = kakaoProperties.logoutUrl() +
                "?client_id=" + kakaoProperties.clientId() + "&logout_redirect_uri=" + kakaoProperties.logoutRedirectUrl();
        tokenService.addBlackList(tokenService.removePrefix(token), loginUserInfo.userId().toString());
        LogoutResponseDto logoutResponseDto = kakaoService.logout(loginUserInfo.userId());
        response.sendRedirect(url);
    }

    /**
     * 카카오계정과 함께 로그아웃
     *
     * @return 카카오계정과 함꼐 로그아웃 페이지
     */
    @GetMapping("/logoutWithKakao/callback")
    public ResponseEntity<?> logoutWithKakao() {
        return ResponseEntity.ok().build();
    }

}
