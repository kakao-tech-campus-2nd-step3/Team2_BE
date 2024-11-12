package jeje.work.aeatbe.dto.user;

import lombok.Builder;


@Builder
public record UserDTO(
        Long id,
        String userId,
        String allergies
) {

}