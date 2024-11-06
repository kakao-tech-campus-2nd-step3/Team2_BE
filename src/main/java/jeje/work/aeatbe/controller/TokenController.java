package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.user.RefreshTokenRequestDTO;
import jeje.work.aeatbe.dto.user.TokenResponseDTO;
import jeje.work.aeatbe.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        TokenResponseDTO tokenResponseDTO = userService.reissueAccessToken(refreshTokenRequestDTO.refreshToken());
        return ResponseEntity.ok(tokenResponseDTO);
    }
}
