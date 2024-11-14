package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

/**
 * Search Shop Parsing Service
 */
@Service
@RequiredArgsConstructor
public class SearchShpParsingService {
    private final ProductRepository productRepository;

    /**
     * Json parsing
     *
     * @param response  JSON 형식의 response
     * @param productId 상품 id
     */
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

            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("상품 ID를 찾을 수 없습니다: " + productId));

            JSONArray itemsArray = (JSONArray) jsonObject.get("items");
            boolean updated = false;

            for (Object itemObj : itemsArray) {
                JSONObject item = (JSONObject) itemObj;
                String mallName = (String) item.get("mallName");

                if (!"네이버".equals(mallName)) {
                    String link = replaceBackSlash((String) item.get("link"));
                    Long price = Long.parseLong((String) item.get("lprice"));
                    String image = (String) item.get("image");
                    String shopName = mallName;

                    product.updateField(shopName, price, link, image);
                    productRepository.save(product);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                product.updateTag("상품 준비 중");
                productRepository.save(product);
            }

        } catch (ParseException e) {
            throw new RuntimeException("JSON 파싱 중 에러 발생 : ", e);
        }
    }

    private String replaceBackSlash(String link) {
        return link.replace("\\", "");  // "\"를 ""로 대체
    }
}
