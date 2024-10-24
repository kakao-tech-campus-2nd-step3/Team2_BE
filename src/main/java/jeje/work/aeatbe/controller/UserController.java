package jeje.work.aeatbe.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jeje.work.aeatbe.domian.KakaoProperties;
import jeje.work.aeatbe.domian.KakaoTokenResponsed;
import jeje.work.aeatbe.dto.User.TokenResponseDto;
import jeje.work.aeatbe.service.UserService;
import jeje.work.aeatbe.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final KakaoProperties kakaoProperties;

    @GetMapping("/login")
    public void redirectKakaoLogin(HttpServletResponse response) throws IOException {
        String url = kakaoProperties.authUrl() +
            "?scope=talk_message&response_type=code&client_id=" + kakaoProperties.clientId() +
            "&redirect_uri=" + kakaoProperties.redirectUrl();
        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<TokenResponseDto> getAccessToken(@RequestParam String code){
        KakaoTokenResponsed token = userService.getKakaoTokenResponse(code);
        String jwt = userService.Login(token.accessToken(), token.refreshToken());
        return new ResponseEntity<>(new TokenResponseDto(jwt), HttpStatus.OK);
    }

}
