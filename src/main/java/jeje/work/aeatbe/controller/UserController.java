package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.UserInfoResponseDto;
import jeje.work.aeatbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@LoginUser LoginUserInfo loginUserInfo){
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(loginUserInfo.userId());
        return ResponseEntity.ok(userInfoResponseDto);
    }
}
