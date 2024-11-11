package jeje.work.aeatbe.dto.review;

import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import lombok.Builder;

@Builder
public record ReviewResponseDTO(
        Long id,
        Long rate,
        String content,
        UserInfoResponseDTO user,
        Long productId,
        String productImgUrl
) {
}
