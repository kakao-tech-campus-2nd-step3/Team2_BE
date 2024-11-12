package jeje.work.aeatbe.dto.user;


import lombok.Builder;

@Builder
public record UserAllergyDTO(
        Long id,
        Long UserId,
        Long allergyId
) {
}