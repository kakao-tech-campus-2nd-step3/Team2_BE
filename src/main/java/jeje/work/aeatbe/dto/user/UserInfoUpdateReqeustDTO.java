package jeje.work.aeatbe.dto.user;

import lombok.Builder;

@Builder
public record UserInfoUpdateReqeustDTO(
    String userName,
    String userImageUrl
) {

}
