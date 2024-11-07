package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoUpdateReqeustDTO;
import jeje.work.aeatbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(@LoginUser LoginUserInfo loginUserInfo){
        UserInfoResponseDTO userInfoResponseDto = userService.getUserInfo(loginUserInfo.userId());
        return ResponseEntity.ok(userInfoResponseDto);
    }

    @PatchMapping("/info/update")
    public ResponseEntity<UserInfoResponseDTO> updateUserInfo(@RequestBody UserInfoUpdateReqeustDTO userInfoUpdateReqeustDto,
        @LoginUser LoginUserInfo loginUserInfo){
        userService.updateUserInfo(userInfoUpdateReqeustDto, loginUserInfo.userId());
        UserInfoResponseDTO userInfoResponseDto = userService.getUserInfo(loginUserInfo.userId());
        return ResponseEntity.ok(userInfoResponseDto);
    }



}
