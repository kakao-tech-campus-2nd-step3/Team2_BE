package jeje.work.aeatbe.service;


import java.util.Optional;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchShpParsingService {
    private final ProductRepository productRepository;

    public void jsonParsing(String response, Long productId) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("API 응답이 비어있습니다.");
        }

        parseJsonToShop(response, productId);
    }

    private void parseJsonToShop(String response, Long productId) {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            JSONArray itemsArray = (JSONArray) jsonObject.get("items");

            for (Object itemObj : itemsArray) {
                JSONObject item = (JSONObject) itemObj;
                String mallName = (String) item.get("mallName");

                if (!"네이버".equals(mallName)) {
                    String link = replaceBackSlash((String) item.get("link"));
                    Long price = Long.parseLong((String) item.get("lprice"));
                    String image = (String) item.get("image");
                    String shopName = mallName;

                    Optional<Product> optionalProduct = productRepository.findById(productId);
                    if (optionalProduct.isPresent()) {
                        Product product = optionalProduct.get();
                        System.out.println("객체 출력 확인용 : " + product.toString());
                        product.updateField(shopName, price, link, image);
                        productRepository.save(product);  
                    }
                    break;  // 네이버가 아닌 첫 번째 mallName을 찾으면 루프 종료
                }
            }


        } catch (ParseException e) {
            throw new RuntimeException("JSON 파싱 중 에러 발생 : ", e);
        }
    }

    private String replaceBackSlash(String link) {
        return link.replace("\\", "");  // "\"를 ""로 대체
    }
}
