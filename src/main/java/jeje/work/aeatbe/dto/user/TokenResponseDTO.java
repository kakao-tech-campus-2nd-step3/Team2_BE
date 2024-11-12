package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record TokenResponseDTO(
        String accessToken,
        String refreshToken
) {
}
