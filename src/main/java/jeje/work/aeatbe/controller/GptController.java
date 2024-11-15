package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.service.GptApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/chatgpt")
public class GptController {
    private GptApiService gptApiService;

    public GptController(GptApiService gptApiService) {
        this.gptApiService = gptApiService;
    }

    @PostMapping
    public ResponseEntity<String> getCallGpt(@RequestParam("image")MultipartFile file, @LoginUser LoginUserInfo loginUserInfo) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("업로드 할 이미지를 선택해주세요");
        }

        try {
            gptApiService.getApi(file, loginUserInfo.userId());
            return ResponseEntity.ok("이미지 가져오기 성공");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("이미지 가져오기 실패: " + e.getMessage());
        }
    }
}
