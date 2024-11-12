package jeje.work.aeatbe.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HaccpService {
    private final String serviceKey;
    private final String baseUrl;
    private final HaccpParsingService haccpParsingService;

    public HaccpService(@Value("${haccp.service_key}") String serviceKey,
        @Value("${haccp.base_url}") String baseUrl,
        HaccpParsingService haccpParsingService) {
        this.serviceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
        this.baseUrl = baseUrl;
        this.haccpParsingService = haccpParsingService;
    }

    public void getAllProducts() {
//        int totalDataCount = 14881;
        int totalDataCount = 4000;
        int numOfRows = 100;

        int totalPages = (int) Math.ceil((double) totalDataCount / numOfRows);

        for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
            infinityChallengeCount(pageNo, 3);
        }
    }

    private void infinityChallengeCount(int pageNo, int retry) {
        while (retry > 0) {
            if (fetchPageData(pageNo)) return;
            retry--;
            if (retry > 0) delayForRetry(pageNo, retry);
        }
        System.out.println("페이지 : " + pageNo + " 데이터 가져오기를 포기했어요");
    }

    private boolean fetchPageData(int pageNo) {
        try {
            String uriString = createUriString(pageNo);
            String responseBody = callApi(uriString);
            if (responseBody == null || responseBody.isEmpty()) {
                System.out.println("API 응답이 비어 있습니다.");
                return false;
            }

            System.out.println("응답 내용: " + responseBody); // 응답 내용 로그 출력

            haccpParsingService.jsonParsing(responseBody);
            return true;
        } catch (Exception e) {
            System.out.println("API 요청 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    private String callApi(String uriString) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
            .uriBuilderFactory(factory)
            .baseUrl(baseUrl)
            .build();

        return webClient.get()
            .uri(uriString)
            .accept(MediaType.APPLICATION_JSON, MediaType.valueOf("text/json"))
            .retrieve()
            .bodyToMono(String.class)
            .doOnError(error -> System.out.println("API 요청 중 오류 발생1 : " + error.getMessage()))
            .block();
    }

    private String createUriString(int pageNo) {
        return UriComponentsBuilder.fromUriString(baseUrl)
            .path("/getCertImgListServiceV3")
            .queryParam("ServiceKey", serviceKey)
            .queryParam("returnType", "json")
            .queryParam("pageNo", pageNo)
            .queryParam("numOfRows", "100")
            .build(true)
            .toUriString();
    }

    private void delayForRetry(int pageNo, int retries) {
//        System.out.println("재시도 중 페이지 " + pageNo + ", 남은 시도 횟수: " + retries);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
