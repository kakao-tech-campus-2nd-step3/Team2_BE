package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record LoginUserInfo(
        Long userId,
        String kakaoId
) {
}
