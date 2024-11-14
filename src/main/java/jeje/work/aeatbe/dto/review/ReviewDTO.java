package jeje.work.aeatbe.dto.review;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReviewDTO(
        Long id,
        @NotNull
        Long rate,
        String content,
        @NotNull
        Long userId,
        @NotNull
        Long productId,
        LocalDateTime date,
        @Nullable
        String productImgUrl) {
}
