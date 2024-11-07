package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record UserInfoResponseDTO(
        Long id,
        String userName,
        String userImageUrl
) {
}
