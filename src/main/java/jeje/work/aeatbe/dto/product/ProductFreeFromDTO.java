package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record ProductFreeFromDTO(
        Long id,
        Long productId,
        Long freeFromId
) {
}
