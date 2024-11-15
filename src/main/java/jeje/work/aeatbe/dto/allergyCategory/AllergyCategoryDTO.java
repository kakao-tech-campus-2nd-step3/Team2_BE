package jeje.work.aeatbe.dto.allergyCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * 알러지 카테고리에 대한 DTO
 *
 * @param id
 * @param type
 * @see jeje.work.aeatbe.service.AllergyCategoryService
 */
@Builder
public record AllergyCategoryDTO(
        Long id,
        @NotNull(message = "입력값이 존재해야 합니다.")
        @Size(max = 25, message = "알러지 타입이 25자를 초과할 수 없습니다.")
        String type
) {
}
