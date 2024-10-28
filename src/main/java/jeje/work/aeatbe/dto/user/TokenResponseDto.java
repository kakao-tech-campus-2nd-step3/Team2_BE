package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record TokenResponseDto(
    String token
) {
}
