package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record ProductAllergyDTO(
    Long id,
    Long productId,
    Long allergyId
) {
}
