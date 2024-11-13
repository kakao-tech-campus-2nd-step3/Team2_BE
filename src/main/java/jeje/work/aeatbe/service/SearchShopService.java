package jeje.work.aeatbe.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jeje.work.aeatbe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class SearchShopService {
    private final String clientId;
    private final String clientSecret;
    private final String baseUrl;
    private final ProductRepository productRepository;
    private final WebClient webClient;
    private final SearchShpParsingService searchShpParsingService;

    // todo : product에서 상품 명을 가져오는 로직
    // todo: 가져오기 위해 entity -> dto 변환 로직 필요
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

    public void fetchProductsFromApi() {
        List<Map<Long, String>> productData = getProductIdAndName();
        productData.forEach(productMap -> {
            productMap.forEach((productId, productName) -> {
                String response = callShopApi(productName);
                System.out.println("제발 제발 제발 제발 id: " + productId + "상품명 :" + productName);
                searchShpParsingService.jsonParsing(response, productId);  // 각 productId와 response 전달
            });
        });
    }

    public String callShopApi(String productName) {
//        String queryParamValue = "르방 통밀발효종빵";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
            .uriBuilderFactory(factory)
            .defaultHeader("X-Naver-Client-Id", clientId)
            .defaultHeader("X-Naver-Client-Secret", clientSecret)
            .baseUrl(baseUrl)
            .build();

        String uriString = UriComponentsBuilder.fromUriString(baseUrl)
            .queryParam("query", productName)                      // test용 문자열
//            .queryParam("query", 변수)                      // db에서 상품 명을 가져와야 함
//            .queryParam("display", 100)                   // default = 10
            .build()
            .encode()
            .toUriString();

        return webClient.get()
            .uri(uriString)
            .retrieve()
            .bodyToMono(String.class)
            .doOnError(error -> System.out.println("API 요청 중 오류 발생 : " + error.getMessage()))
            .doOnNext(responseBody -> System.out.println("Response Body: " + responseBody))  // 응답 출력(확인용)
            .onErrorResume(error -> {
                System.out.println("API 요청 중 오류 발생 : " + error.getMessage());
                return Mono.empty();  // 에러 발생 시 빈 Mono를 반환
            })
            .block();


    }

//    // 상품 이름 호출
//    // todo: DB내 product_Name 항목을 가져옴
//    public List<Map<Long, String>> getProductIdAndName() {
//        List<Map<Long, String>> productName = productRepository.findByProductIdAndName();   // 상품 이름을 list 형태로 가지고 오는 로직 필요
////        System.out.println("productName 모음 : " + productName);
////        System.out.println("총 길이 : " + productName.size());
//
////        // 파싱 테스트용
////        List<String> productName = Arrays.asList("설화눈꽃팝김부각스낵", "설화눈꽃팝김부각스낵 아몬드맛", "눈꽃팝김부각스낵","참군고구마",
////            "홀리닭 청양큐브", "홀리닭 통가슴살 오리지널", "바삭하고 고소한 우유팝콘", "고소하고 매콤 달콤한 고추 팝콘", "충주 사과 팝콘");
//
//        return productName;
//    }

    public List<Map<Long, String>> getProductIdAndName() {
        List<String> productNames = Arrays.asList(
            "설화눈꽃팝김부각스낵", "설화눈꽃팝김부각스낵 아몬드맛", "눈꽃팝김부각스낵", "참군고구마칩" , "참군고구마",
            "홀리닭 청양큐브", "홀리닭 통가슴살 오리지널", "바삭하고 고소한 우유팝콘", "고소하고 매콤 달콤한 고추 팝콘", "충주 사과 팝콘"
        );

        List<Map<Long, String>> productData = IntStream.range(0, productNames.size())
            .mapToObj(i -> Map.of((long) i + 1, productNames.get(i)))
            .collect(Collectors.toList());

        System.out.println(productData);

        return productData;
    }


}
