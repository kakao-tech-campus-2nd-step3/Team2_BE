package jeje.work.aeatbe.dto.User;
import lombok.*;


@Builder
public record UserDTO(
    Long id,
    String userId,
    String allergies
) {

}