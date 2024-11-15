package jeje.work.aeatbe.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import org.hibernate.validator.constraints.URL;

@Builder
public record UserInfoResponseDTO(
        Long id,
        @Size(max = 15, message = "유저이름이 15자를 초과할 수 없습니다.")
        String userName,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String userImageUrl,
        List<String> allergies,
        List<String> freefrom
) {
}
