package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

/**
 * Haccp api 파싱 서비스
 */
@Service
@RequiredArgsConstructor
public class HaccpParsingService {
    private final Random random = new Random();

    private final AllergyCategoryService allergyCategoryService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    /**
     * Json parsing list.
     *
     * @param response api 응답 문자열
     * @return productDTO 리스트
     */
    public List<ProductDTO> jsonParsing(String response) {
        List<ProductDTO> productDTOList = parseJsonToProductDTOs(response);

        for (ProductDTO productDTO : productDTOList) {
            saveProductDTO(productDTO);
        }

        return productDTOList;
    }

    private List<ProductDTO> parseJsonToProductDTOs(String response) {
        JSONParser jsonParser = new JSONParser();
        List<ProductDTO> productDTOList = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            JSONObject bodyObject = (JSONObject) jsonObject.get("body");
            JSONArray itemsArray = (JSONArray) bodyObject.get("items");

            for (Object itemObj : itemsArray) {
                JSONObject item = (JSONObject) ((JSONObject) itemObj).get("item");

                String allergyStr = (String) item.get("allergy");
                List<ProductAllergyDTO> allergies = parseAllergyStringToDTO(allergyStr);

                ProductDTO productDTO = buildJsonProductDTO(item, allergies);

                productDTOList.add(productDTO);
            }
        } catch (ParseException e) {
            throw new RuntimeException("JSON 파싱 중 에러 발생", e);
        }

        return productDTOList;
    }

    private List<ProductAllergyDTO> parseAllergyStringToDTO(String allergyStr) {
        List<ProductAllergyDTO> allergyDTOList = new ArrayList<>();
        if (allergyStr != null && !allergyStr.isEmpty()) {
            String[] items = allergyStr.replaceAll("[\\[\\]']", "").split(",");
            for (String item : items) {
                String allergyType = item.trim();

                if (allergyType.equals("없음")) {
                    continue;
                }
                AllergyCategoryDTO allergyCategory = allergyCategoryService.getProductAllergyByType(allergyType);

                ProductAllergyDTO allergyDTO = ProductAllergyDTO.builder()
                    .allergyId(allergyCategory.id())
                    .build();
                allergyDTOList.add(allergyDTO);
            }
        }
        return allergyDTOList;
    }

    private ProductDTO buildJsonProductDTO(JSONObject item, List<ProductAllergyDTO> allergies) {
        return ProductDTO.builder()
            .allergy(allergies)
            .nutritionalInfo((String) item.get("nutrient"))
            .productImageUrl((String) item.get("imgurl1"))
            .metaImageUrl((String) item.get("imgurl2"))
            .typeName((String) item.get("prdkind"))
            .manufacturer((String) item.get("manufacture"))
            .capacity((String) item.get("capacity"))
            .productName((String) item.get("prdlstNm"))
            .ingredients((String) item.get("rawmtrl"))
            .productBarcode((String) item.get("barcode"))
            .price(0L)
            .tag(randomTagGenerator())
            .build();
    }

    private void saveProductDTO(ProductDTO productDTO) {
        productRepository.save(productMapper.toEntity(productDTO, true));
    }

    private String randomTagGenerator() {
        return random.nextInt(100) == 0 ? "광고" : null;
    }
}
