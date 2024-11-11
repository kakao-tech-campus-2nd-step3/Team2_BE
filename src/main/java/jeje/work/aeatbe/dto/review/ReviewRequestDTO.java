package jeje.work.aeatbe.dto.review;

import lombok.Builder;

@Builder
public record ReviewRequestDTO(
        Long rate,
        String content,
        Long productId
) {
}
