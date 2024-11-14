package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.AllergyCategoryRepository;
import jeje.work.aeatbe.repository.ProductAllergyRepository;
import jeje.work.aeatbe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

/**
 * Haccp API 파싱 서비스
 */
@Service
@RequiredArgsConstructor
public class HaccpParsingService {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final AllergyCategoryService allergyCategoryService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductAllergyRepository productAllergyRepository;
    private final AllergyCategoryRepository allergyCategoryRepository;
    private final List<String> allergyList = Arrays.asList(
        "난류", "우유", "메밀", "대두", "호두", "땅콩", "복숭아", "토마토", "닭고기", "돼지고기",
        "쇠고기", "새우", "고등어", "홍합", "전복", "굴", "조개류", "게", "오징어", "아황산류", "잣", "콩류", "아몬드", "밀"
    );


    /**
     * JSON 파싱 로직
     *
     * @param response API 응답 문자열
     */
    public void jsonParsing(String response) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("API 응답이 비어 있습니다.");
        }

        List<ProductDTO> productDTOList = parseJsonToProductDTO(response);

        System.out.println("상품 목록 저장 시작");
        for (ProductDTO productDTO : productDTOList) {
            saveProductDTO(productDTO);
        }
    }

    /**
     * JSON 응답 문자열을 파싱하여 ProductDTO로 변환
     *
     * @param response API 응답 JSON 문자열
     * @return 파싱된 ProductDTO 목록
     */
    private List<ProductDTO> parseJsonToProductDTO(String response) {
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

    /**
     * 호출된 api를 파싱하여 ProductAllergyDTO 목록을 생성
     *
     * @param allergyStr 알러지 정보 문자열
     * @return 파싱된 ProductAllergyDTO 목록
     */
    private List<ProductAllergyDTO> parseAllergyStringToDTO(String allergyStr) {
        List<ProductAllergyDTO> allergyDTOList = new ArrayList<>();

        if (allergyStr != null && !allergyStr.isEmpty()) {
            String[] items = allergyStr.replaceAll("[\\\\[\\\\]()]", " ").split(",");

            for (String item : items) {
                String allergyType = item.trim();
//                System.out.println("알러지 항목: " + allergyType);

                allergyType = replaceWithCategory(allergyType);
//                System.out.println("대체 후: " + allergyType);

                if (allergyType.equals("없음")) continue;

                AllergyCategoryDTO allergyCategoryDTO = allergyCategoryService.getProductAllergyByType(allergyType);

                ProductAllergyDTO allergyDTO = ProductAllergyDTO.builder()
                    .allergyId(allergyCategoryDTO.id())
                    .build();

                allergyDTOList.add(allergyDTO);
            }
        }

        return allergyDTOList;
    }

    /**
     * 알러지 타입을 적절한 카테고리로 대체
     *
     * @param allergyType 알러지 타입
     * @return 대체된 알러지 카테고리
     */
    private String replaceWithCategory(String allergyType) {
        Map<String, List<String>> categoryMap = new HashMap<>();
        categoryMap.put("난류", Arrays.asList("알류", "계란", "메추리알", "난백", "난각칼슘", "난황분말"));
        categoryMap.put("우유", Arrays.asList("탈지분유", "유청"));
        categoryMap.put("대두", Arrays.asList("구아검", "두부", "백태", "적두", "레시틴"));
        categoryMap.put("콩류", Arrays.asList("완두콩", "렌틸콩", "땅콩"));
        categoryMap.put("조개류", Arrays.asList("바지락", "개량조개", "모시조개", "해조칼슘", "키조개", "소라", "가리비", "칼슘분말", "패각칼슘", "무수아황산", "위소라"));
        categoryMap.put("닭고기", Arrays.asList("닭가슴살"));
        categoryMap.put("쇠고기", Arrays.asList("소고기"));
        categoryMap.put("돼지고기", Arrays.asList("돈지방", "돈창", "돈혈", "돈골"));
        categoryMap.put("아황산류", Arrays.asList("아황산", "아황산나트륨", "산성아황산나트륨", "이산화황"));

        for (String category : allergyList) {
            if (allergyType.contains(category)) return category;
        }

        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            for (String value : entry.getValue()) {
                if (allergyType.contains(value)) return entry.getKey();
            }
        }

        return "없음";
    }

    /**
     * 파싱된 정보로 ProductDTO를 생성
     *
     * @param item      상품 정보가 담긴 객체
     * @param allergies ProductAllergyDTO 리스트
     * @return ProductDTO
     */
    private ProductDTO buildJsonProductDTO(JSONObject item, List<ProductAllergyDTO> allergies) {
        String nutritionalInfo = (String) item.get("nutrient");
        nutritionalInfo = nutritionalInfo == null ? "알수없음" : nutritionalInfo;

        return ProductDTO.builder()
            .allergy(allergies)
            .nutritionalInfo(nutritionalInfo)
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

    /**
     * ProductDTO를 데이터베이스에 저장
     *
     * @param productDTO ProductDTO 객체
     */
    private void saveProductDTO(ProductDTO productDTO) {
        Product product = productRepository.save(productMapper.toEntity(productDTO, true));

        List<Long> allergyIds = productDTO.allergy() != null ? productDTO.allergy().stream()
            .map(ProductAllergyDTO::allergyId)
            .toList() : new ArrayList<>();

        for (Long allergyId : allergyIds) {
            AllergyCategory allergyCategory = allergyCategoryRepository.findById(allergyId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 알러지 ID: " + allergyId));

            ProductAllergy productAllergy = ProductAllergy.builder()
                .product(product)
                .allergy(allergyCategory)
                .build();

            productAllergyRepository.save(productAllergy);
        }
    }

    private String randomTagGenerator() {
        return random.nextInt(100) == 0 ? "광고" : null;
    }
}
