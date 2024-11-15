package jeje.work.aeatbe.service;

import java.io.IOException;
import java.util.List;
import java.util.Base64;
import jeje.work.aeatbe.dto.ocr.Content;
import jeje.work.aeatbe.dto.ocr.GPTRequestDTO;
import jeje.work.aeatbe.dto.ocr.ImageRequestDTO;
import jeje.work.aeatbe.dto.ocr.ImageUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GPTApiService {
    private final WebClient webClient;
    private final String secretKey;
    private final String baseUrl;

    public GPTApiService(@Value("${gpt.secret_key}") String secretKey,
        WebClient.Builder webClientBuilder,
        @Value("${gpt.base_url}") String baseUrl) {
        this.secretKey = secretKey;
        this.baseUrl = baseUrl;


        this.webClient = webClientBuilder
            .defaultHeader("Authorization", "Bearer " + secretKey)
            .defaultHeader("Content-Type", "application/json")
            .baseUrl(baseUrl)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
            .build();
    }

    public void getApi(String imagePath) {
        try {
            String encodedImage = imageEncoding(imagePath);
            callGptApi(encodedImage);
        } catch (IOException e) {
            System.out.println("Error encoding image: " + e.getMessage());
        }
    }

    private void callGptApi(String base64Image) {
        Content textContent = Content.builder()
            .type("text")
            .text("이 이미지는 알러지 검사지에 관한 종이야. 검사 결과지 결과를 각 카테고리 별로 상세히 분류해서 표로 보여줘. 특히 결과(IU/mL)와 같이 표기된 열을 가장 명확하게 읽어와줘야 해")
            .build();

        Content imageContent = Content.builder()
            .type("image_url")
            .imageUrl(new ImageUrl("data:image/jpeg;base64," + base64Image, "high"))
            .build();

        GPTRequestDTO message = GPTRequestDTO.builder()
            .role("user")
            .content(List.of(textContent, imageContent))
            .build();

        ImageRequestDTO requestDto = ImageRequestDTO.builder()
            .model("gpt-4o-mini")
            .message(List.of(message))
            .maxTokens(2000)
            .build();

        System.out.println("Request DTO: " + requestDto);

        webClient.post()
            .uri(baseUrl)
            .bodyValue(requestDto)
            .retrieve()
            .bodyToMono(String.class)
            .doOnNext(response -> System.out.println("Response: " + response))
            .doOnError(error -> System.out.println("Error: " + error.getMessage()))
            .block();
    }

    private String imageEncoding(String imageUrl) throws IOException {
        byte[] imageBytes = webClient.get()
            .uri(imageUrl)
            .retrieve()
            .bodyToMono(byte[].class)
            .block();

        // Base64로 인코딩
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
