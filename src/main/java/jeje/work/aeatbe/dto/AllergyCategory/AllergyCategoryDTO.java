package jeje.work.aeatbe.dto.AllergyCategory;

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
        String allergyType
) {
}
