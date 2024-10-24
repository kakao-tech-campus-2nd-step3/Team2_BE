package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record ProductDTO(
        Long id,
        String allergens,
        String nutritionalInfo,
        String productImageUrl,
        String metaImageUrl,
        String typeName,
        String manufacturer,
        String seller,
        String capacity,
        String productName,
        String ingredients,
        Long price
) {
}
