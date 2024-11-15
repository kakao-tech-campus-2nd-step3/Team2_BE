package jeje.work.aeatbe.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import org.hibernate.validator.constraints.URL;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDTO(
        Long id,
        @JsonProperty("allergy")
        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<ProductAllergyDTO> allergy,
        @JsonProperty("nutrient") String nutritionalInfo, // 영양성분
        @URL(message = "유효하지 않는 URL 형식입니다.")
        @JsonProperty("imgurl1") String productImageUrl, // 상품 이미지
        @URL(message = "유효하지 않는 URL 형식입니다.")
        @JsonProperty("imgurl2") String metaImageUrl, // 상품 상세 설명 이미지
        @Size(max = 200, message = "제품 유형명이 200자를 초과할 수 없습니다.")
        @JsonProperty("prdkind") String typeName, // 제품 종류(유형명)
        @Size(max = 200, message = "제조사 이름이 200자를 초과할 수 없습니다.")
        @JsonProperty("manufacture") String manufacturer, // 제조사
        String seller,                                         // todo: 판매 링크(추후에 사용 될 예정)
        @Size(max = 100, message = "용량이 100을 초과할 수 없습니다.")
        @JsonProperty("capacity") String capacity, // 용량
        @NotNull(message = "상품명이 존재해야 합니다.")
        @Size(max = 50, message = "상품명은 50자를 초과할 수 없습니다.")
        @JsonProperty("prdlstNm") String productName, // 상품명
        @Size(min = 0, message = "원재료 이름이 존재해야 합니다.")
        @JsonProperty("rawmtrl") String ingredients, // 원재료
        Long price,  // 가격(추후에 사용 될 예정)
        @JsonProperty("barcode") String productBarcode, // 상품 바코드(현재 미사용중)
        String tag,
        @Size(max = 20, message = "상품 판매처 이름은 20자 이상이어야 합니다.")
        String mallName                 // 상품 판매처 이름
) {
}
