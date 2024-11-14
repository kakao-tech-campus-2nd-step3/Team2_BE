package jeje.work.aeatbe.dto.review;

import java.time.LocalDateTime;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import lombok.Builder;

@Builder
public record ReviewResponseDTO(
        Long id,
        Long rate,
        String content,
        UserInfoResponseDTO user,
        Long productId,
        String productImgUrl,
        String productName,
        LocalDateTime date
) {
}
