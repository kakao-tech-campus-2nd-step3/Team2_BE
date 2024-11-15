package jeje.work.aeatbe.dto.review;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record ReviewResponseDTO(
        Long id,
        @Size(min = 0, max = 5, message = "평점이 0점이상, 5점이하여야 합니다.")
        @NotNull(message = "평점을 입력해주셔야 합니다.")
        Long rate,
        String content,
        UserInfoResponseDTO user,
        @NotNull(message = "입력값이 존재해야 합니다.")
        Long productId,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String productImgUrl,
        String productName,
        LocalDateTime date
) {
}
