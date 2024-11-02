package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        Long id,
        String userName,
        String userImageUrl
) {
}
