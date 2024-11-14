package jeje.work.aeatbe.dto.review;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record ReviewResponseDTO(
        Long id,
        @NotNull
        Long rate,
        String content,
        UserInfoResponseDTO user,
        @NotNull
        Long productId,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String productImgUrl,
        String productName,
        LocalDateTime date
) {
}
