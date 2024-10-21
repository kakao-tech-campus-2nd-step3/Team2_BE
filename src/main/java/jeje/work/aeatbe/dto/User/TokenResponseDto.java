package jeje.work.aeatbe.dto.User;

import lombok.Builder;

@Builder
public record TokenResponseDto(
    String token
) {
}
