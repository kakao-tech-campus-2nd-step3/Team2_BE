package jeje.work.aeatbe.dto.review;

import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReviewDTO(
        Long id,
        Long rate,
        String content,
        Long userId,
        Long productId,
        LocalDateTime date,
        @Nullable
        String productImgUrl) {
}
