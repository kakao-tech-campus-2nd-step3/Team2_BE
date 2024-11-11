package jeje.work.aeatbe.dto.user;

import java.util.List;
import lombok.Builder;

@Builder
public record UserInfoUpdateReqeustDTO(
    String userName,
    String userImageUrl,
    List<String> allergies,
    List<String> freefrom
) {

}
