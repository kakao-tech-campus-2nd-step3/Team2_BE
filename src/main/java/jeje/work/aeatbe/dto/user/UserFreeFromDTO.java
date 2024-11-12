package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record UserFreeFromDTO(
        Long id,
        Long userId,
        Long freeFromId
) {
}