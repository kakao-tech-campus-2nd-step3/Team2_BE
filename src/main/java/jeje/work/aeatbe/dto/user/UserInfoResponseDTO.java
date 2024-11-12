package jeje.work.aeatbe.dto.user;

import lombok.Builder;

import java.util.List;

@Builder
public record UserInfoResponseDTO(
        Long id,
        String userName,
        String userImageUrl,
        List<String> allergies,
        List<String> freefrom
) {
}
