package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.user.RefreshTokenRequestDTO;
import jeje.work.aeatbe.dto.user.TokenResponseDTO;
import jeje.work.aeatbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final UserService userService;

    /**
     * 리프레시 토큰을 통해 토큰을 재발급 받는다.
     * @param refreshTokenRequestDTO 리프레시 토큰
     * @return 헤더에 토큰 재발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        TokenResponseDTO tokenResponseDto = userService.reissueAccessToken(refreshTokenRequestDTO.refreshToken());
        HttpHeaders httpHeaders = userService.setCookie(tokenResponseDto);
        return ResponseEntity.ok().body(tokenResponseDto);
    }
}
