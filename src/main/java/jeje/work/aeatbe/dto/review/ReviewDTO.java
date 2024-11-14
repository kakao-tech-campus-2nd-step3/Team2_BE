package jeje.work.aeatbe.dto.review;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReviewDTO(
        Long id,
        @Size(min = 0, max = 5, message = "평점이 0점이상, 5점이하여야 합니다.")
        @NotNull(message = "평점을 입력해주셔야 합니다.")
        Long rate,
        String content,
        @NotNull(message = "입력값이 존재해야 합니다.")
        Long userId,
        @NotNull(message = "입력값이 존재해야 합니다.")
        Long productId,
        LocalDateTime date,
        @Nullable
        String productImgUrl) {
}
