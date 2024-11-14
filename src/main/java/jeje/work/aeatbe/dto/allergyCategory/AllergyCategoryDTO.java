package jeje.work.aeatbe.dto.allergyCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * 알러지 카테고리에 대한 DTO
 *
 * @param id
 * @param allergyType
 * @see jeje.work.aeatbe.service.AllergyCategoryService
 */
@Builder
public record AllergyCategoryDTO(
        Long id,
        @NotNull
        @Size(max = 25)
        String allergyType
) {
}
