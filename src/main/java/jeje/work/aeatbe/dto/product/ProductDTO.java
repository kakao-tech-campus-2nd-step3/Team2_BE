package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record ProductDTO(
        Long id,
        //String allergens, // 역할 중복을 제거
        String nutritionalInfo, // 현재 사용 안함
        String productImageUrl, // 상품 이미지
        String metaImageUrl, //상품 상세 설명 이미지
        String typeName, // 현재 사용 안함
        String manufacturer, // 제조사, 현재 사용 안함
        String seller, // 판매 링크로 사용중
        String capacity, // 용량, 현재 사용 안함
        String productName, // 상품명
        String ingredients, // 성분, 현재 사용 안함
        Long price // 가격
) {
}
