package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.service.GPTApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatGpt")
public class GptController {
    private GPTApiService gptApiService;

    public GptController(GPTApiService gptApiService) {
        this.gptApiService = gptApiService;
    }

    @GetMapping
    public void testController() {
        gptApiService.getApi("https://file3.instiz.net/data/cached_img/upload/2019/09/25/12/1533b34aa0a797a944820c3f613c63ba.jpg");
    }
}
