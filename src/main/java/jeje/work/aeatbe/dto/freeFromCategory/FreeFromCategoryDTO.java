package jeje.work.aeatbe.dto.freeFromCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FreeFromCategoryDTO(
        Long id,
        @NotNull
        @Size(max = 25)
        String freeFromType
) {
}
