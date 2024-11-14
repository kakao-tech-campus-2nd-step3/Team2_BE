package jeje.work.aeatbe.controller;

import jakarta.validation.Valid;
import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoUpdateReqeustDTO;
import jeje.work.aeatbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 정보를 가져온다.
     *
     * @param loginUserInfo Authorization 헤더에 포함된 토큰으로부터 가져온 유저 정보
     * @return 유저정보
     */
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(@LoginUser LoginUserInfo loginUserInfo) {
        UserInfoResponseDTO userInfoResponseDto = userService.getUserInfo(loginUserInfo.userId());
        return ResponseEntity.ok(userInfoResponseDto);
    }

    /**
     * 유저정보를 업데이트한다.
     *
     * @param userInfoUpdateReqeustDto 업데이트할 유저
     * @param loginUserInfo            Authorization 헤더에 포함된 토큰으로부터 가져온 유저 정보
     * @return 업데이트 된 정보
     */
    @PatchMapping("/info/update")
    public ResponseEntity<UserInfoResponseDTO> updateUserInfo(@RequestBody @Valid UserInfoUpdateReqeustDTO userInfoUpdateReqeustDto,
                                                              @LoginUser LoginUserInfo loginUserInfo) {
        userService.updateUserInfo(userInfoUpdateReqeustDto, loginUserInfo.userId());
        UserInfoResponseDTO userInfoResponseDto = userService.getUserInfo(loginUserInfo.userId());
        return ResponseEntity.ok(userInfoResponseDto);
    }


}
