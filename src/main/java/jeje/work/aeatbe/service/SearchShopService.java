package jeje.work.aeatbe.service;

import java.util.List;
import java.util.Map;
import jeje.work.aeatbe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * The type Search shop service.
 */
@Service
public class SearchShopService {
    private final String clientId;
    private final String clientSecret;
    private final String baseUrl;
    private final ProductRepository productRepository;
    private final WebClient webClient;
    private final SearchShpParsingService searchShpParsingService;

    public SearchShopService(@Value("${shop.client_id}") String clientId,
        @Value("${shop.client_secret}") String clientSecret,
        @Value("${shop.base_url}") String baseUrl,
        ProductRepository productRepository, WebClient.Builder webClientBuilder,
        SearchShpParsingService searchShpParsingService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.baseUrl = baseUrl;
        this.productRepository = productRepository;
        this.webClient = webClientBuilder
            .clone()
            .baseUrl(baseUrl)
            .build();
        this.searchShpParsingService = searchShpParsingService;
    }

    /**
     * api로부터 상품 상세 데이터 가져오기
     */
    public void fetchProductsFromApi() {
        List<Map<String, Object>> productData = getProductIdAndName();

        for (Map<String, Object> product : productData) {
            Long productId = ((Number) product.get("id")).longValue();
            String productName = (String) product.get("product_name");

            String response = callShopApi(productName);
            searchShpParsingService.jsonParsing(response, productId);
        }
    }

    /**
     * 쇼핑 api 호출
     *
     * @param productName 상품 이름
     * @return api 호출 결과 String
     */
    public String callShopApi(String productName) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
            .uriBuilderFactory(factory)
            .defaultHeader("X-Naver-Client-Id", clientId)
            .defaultHeader("X-Naver-Client-Secret", clientSecret)
            .baseUrl(baseUrl)
            .build();

        String uriString = UriComponentsBuilder.fromUriString(baseUrl)
            .queryParam("query", productName)
            .build()
            .encode()
            .toUriString();

        return webClient.get()
            .uri(uriString)
            .retrieve()
            .bodyToMono(String.class)
//            .doOnError(error -> System.out.println("API 요청 중 오류 발생 : " + error.getMessage()))
//            .doOnNext(responseBody -> System.out.println("Response Body: " + responseBody))
            .onErrorResume(error -> {
//                System.out.println("API 요청 중 오류 발생 : " + error.getMessage());
                return Mono.empty();
            })
            .block();
    }

    /**
     * 상품 이름 및 PK 가져오기
     *
     * @return 상품 id와 name
     */
    public List<Map<String, Object>> getProductIdAndName() {
        List<Map<String, Object>> productData = productRepository.findByProductIdAndName();

//        // 디버깅용 로그
//        productData.forEach(map -> {
//            Long id = ((Number) map.get("id")).longValue();
//            String name = (String) map.get("product_name");
//            System.out.println("ID: " + id + ", Name: " + name);
//        });

        return productData;
    }
}