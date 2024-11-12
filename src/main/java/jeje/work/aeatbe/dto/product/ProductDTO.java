package jeje.work.aeatbe.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDTO(
        Long id,
        @JsonProperty("allergy")
        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<ProductAllergyDTO> allergy,
        @JsonProperty("nutrient") String nutritionalInfo, // 영양성분
        @JsonProperty("imgurl1") String productImageUrl, // 상품 이미지
        @JsonProperty("imgurl2") String metaImageUrl, // 상품 상세 설명 이미지
        @JsonProperty("prdkind") String typeName, // 제품 종류(유형명)
        @JsonProperty("manufacture") String manufacturer, // 제조사
        String seller,                                         // todo: 판매 링크(추후에 사용 될 예정)
        @JsonProperty("capacity") String capacity, // 용량
        @JsonProperty("prdlstNm") String productName, // 상품명
        @JsonProperty("rawmtrl") String ingredients, // 원재료
        Long price,  // 가격(추후에 사용 될 예정)
        @JsonProperty("barcode") String productBarcode, // 상품 바코드(현재 미사용중)
        String tag
) {
}
