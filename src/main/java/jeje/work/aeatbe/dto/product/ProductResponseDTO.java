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
        String[] allergy
//        String tag
) {
}
