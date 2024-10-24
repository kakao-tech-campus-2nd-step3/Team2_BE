package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record ProductResponseDTO(
        Long id,
        String name,
        Long price,
        String imgUrl,
        Float rating,
        String ProductUrl,
        String description,
        String[] freeFrom,
        String[] allergy
) {
}
