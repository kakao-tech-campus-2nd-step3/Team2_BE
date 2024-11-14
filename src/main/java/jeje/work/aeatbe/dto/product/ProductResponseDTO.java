package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record ProductResponseDTO(
        Long id,
        String name,
        Long price,
        String imgUrl,
        Double rating,
        String ProductUrl,
        String description,
        String[] freeFrom,
        String[] allergy,
        String tag,
        String mallName,               // 쇼핑몰 이름
        String nutritionalInfo,      // 영양성분
        String manufacturer,         // 제조사
        String capacity,             // 용량
        String ingredients          // 원재료
) {
}
