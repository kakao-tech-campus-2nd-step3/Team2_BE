package jeje.work.aeatbe.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewRequestDTO(
        @NotNull
        Long rate,
        String content,
        @NotNull
        Long productId
) {
}
