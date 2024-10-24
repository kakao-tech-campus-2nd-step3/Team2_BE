package jeje.work.aeatbe.dto.User;

import lombok.*;

@Builder
public record UserFreeFromDTO(
    Long id,
    Long userId,
    Long freeFromId
) {
}