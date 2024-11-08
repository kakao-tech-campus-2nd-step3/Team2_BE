package jeje.work.aeatbe.dto.user;

import java.util.List;
import jeje.work.aeatbe.entity.UserAllergy;
import lombok.Builder;

@Builder
public record UserInfoResponseDTO(
        Long id,
        String userName,
        String userImageUrl,
        List<String> allergies,
        List<String> freefrom
) {
}
