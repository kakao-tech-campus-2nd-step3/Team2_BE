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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GptApiService {
    private final WebClient webClient;
    private final String secretKey;
    private final String baseUrl;
    private final GptParsingService gptParsingService;

    public GptApiService(@Value("${gpt.secret_key}") String secretKey,
        WebClient.Builder webClientBuilder,
        @Value("${gpt.base_url}") String baseUrl, GptParsingService gptParsingService) {
        this.secretKey = secretKey;
        this.baseUrl = baseUrl;

        this.webClient = webClientBuilder
            .defaultHeader("Authorization", "Bearer " + secretKey)
            .defaultHeader("Content-Type", "application/json")
            .baseUrl(baseUrl)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
            .build();

        this.gptParsingService = gptParsingService;
    }

    public void getApi(MultipartFile file, Long userId) {
        try {
            String encodedImage = imageEncoding(file);
            String response  = callGptApi(encodedImage);
            gptParsingService.jsonParsing(response, userId);

        } catch (IOException e) {
            System.out.println("이미지 인코딩 실패: " + e.getMessage());
            throw new RuntimeException("이미지 분석에 실패했습니다.", e);
        }
    }

    private String callGptApi(String base64Image) {
        Content textContent = Content.builder()
            .type("text")
            .text("나는 지금 위의 이미지를 기반으로 사용자가 가지고 있는 식품 알러지를 알려주는 서비스를 만들고 있어\n" +
                "- 컴퓨터에 rest api로 전달해줄 값이라 아래의 json 형식을 절대적으로! 준수해야 해\n" +
                "- 코드 블럭은 절대로 쓰지마 rest api기 때문이야\n" +
                "- 식품 관련 알러지를 열심히 찾아야 해! 응원할게~!\n" +
                "- 숫자가 소숫점일거야 이걸 “정확히” 찾으면 내가 맛있는 쿠키를 줄게\n" +
                "- 우리가 가지고 있는 카테고리는 아래와 같아\n"+
                "- 알러지 필터링 조건\n" +
                "  - IU/ml 값이 0.35이상\n"+
                "  - 강조가 되어 있는 경우\n" +
                "  - Class가 1(단계) 이상인 경우\n" +
                "- 아래 제시된 객체에 없는 항목은 보지마 \n" +
                "{id : value}로 구성됨\n" +
                "[{1 : 난류},\n" +
                "{2 : 우유},\n" +
                "{3 : 메밀},\n" +
                "{4 : 땅콩},\n" +
                "{5 : 대두},\n" +
                "{6 : 밀},\n" +
                "{7 : 잣},\n" +
                "{8 : 호두},\n" +
                "{9 : 게},\n" +
                "{10 : 새우},\n" +
                "{11 : 오징어},\n" +
                "{12 : 고등어},\n" +
                "{13 : 조개류},\n" +
                "{14 : 복숭아},\n" +
                "{15 : 토마토},\n" +
                "{16 : 닭고기},\n" +
                "{17 : 돼지고기},\n" +
                "{18 : 쇠고기},\n" +
                "{19 : 아황산류},\n" +
                "{20 : 홍합},\n" +
                "{21 : 전복},\n" +
                "{22 : 굴},\n" +
                "{23: 콩류},\n" +
                "{24: 아몬드}]\n" +
                "- 너가 반환해야 할 형식은 다음과 같아\n" +
                "[{id : value}, {id, value}, … ]\n" +
                "- 찾아봤는데 없으면 다음과 같이 반환해야 해\n" +
                "[]\n" +
                "- 컴퓨터에 주는 형식이니까 준수해야 해! 코드블럭 쓰지 말고! 없으면 빈 리스트!")
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
            .model("gpt-4o")
            .message(List.of(message))
            .maxTokens(1000)
            .build();

        System.out.println("Request DTO: " + requestDto);

        return webClient.post()
            .uri(baseUrl)
            .bodyValue(requestDto)
            .retrieve()
            .bodyToMono(String.class)
            .doOnNext(response -> System.out.println("Response: " + response))
            .doOnError(error -> System.out.println("Error: " + error.getMessage()))
            .block();
    }

    private String imageEncoding(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(fileBytes);
    }
}