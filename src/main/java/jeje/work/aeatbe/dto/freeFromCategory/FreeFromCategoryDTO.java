package jeje.work.aeatbe.dto.freeFromCategory;

import lombok.Builder;

@Builder
public record FreeFromCategoryDTO(
        Long id,
        String freeFromType,
) {
}
