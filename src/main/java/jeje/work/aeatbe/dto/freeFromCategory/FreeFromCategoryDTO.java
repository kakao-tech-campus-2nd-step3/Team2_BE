package jeje.work.aeatbe.dto.freeFromCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FreeFromCategoryDTO(
        Long id,
        @NotNull(message = "입력값이 존재해야 합니다.")
        @Size(max = 25, message = "프리프롬 타입은 25자를 초과할 수 없습니다.")
        String freeFromType
) {
}
