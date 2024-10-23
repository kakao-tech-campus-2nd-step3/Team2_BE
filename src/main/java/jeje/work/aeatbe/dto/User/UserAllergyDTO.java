package jeje.work.aeatbe.dto.User;


import lombok.Builder;

@Builder
public record UserAllergyDTO(
    Long id,
    Long UserId,
    Long allergyId
) {
}