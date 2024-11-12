package jeje.work.aeatbe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Haccp Service
 */
@Service
public class HaccpService {
    private final String serviceKey;
    private final String baseUrl;
    private final HaccpParsingService haccpParsingService;
    private final ObjectMapper objectMapper;


    public HaccpService(@Value("${haccp.service_key}") String serviceKey,
                        @Value("${haccp.base_url}") String baseUrl,
                        HaccpParsingService haccpParsingService,
                        ObjectMapper objectMapper) {
        this.serviceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
        this.baseUrl = baseUrl;
        this.haccpParsingService = haccpParsingService;
        this.objectMapper = objectMapper;
    }

    /**
     * Gets product api.
     */
    public void getProductApi() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .baseUrl(baseUrl)
                .build();

        String uriString = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/getCertImgListServiceV3")
                .queryParam("ServiceKey", serviceKey)
                .queryParam("returnType", "json")
//            .queryParam("pageNo", "1")       // 페이지 번호
//            .queryParam("numOfRows", "100")  // 한 페이지 결과 수
                .build(true)  // query parameter도 인코딩된 형태로 빌드
                .toUriString();

        System.out.println("Constructed URL: " + uriString);

        Mono<String> response = webClient.get()
                .uri(uriString)
                .accept(MediaType.APPLICATION_JSON, MediaType.valueOf("text/json"))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.out.println("API 요청 중 오류 발생 : " + error.getMessage()))
                .doOnNext(responseBody -> System.out.println("Response Body: " + responseBody))  // 응답 출력(확인용)
                .doOnNext(haccpParsingService::jsonParsing)
                .onErrorResume(error -> {
                    System.out.println("API 요청 중 오류 발생 : " + error.getMessage());
                    return Mono.empty();  // 에러 발생 시 빈 Mono를 반환
                });

        response.subscribe();
    }
}

