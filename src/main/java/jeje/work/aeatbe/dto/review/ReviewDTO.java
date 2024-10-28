package jeje.work.aeatbe.dto.review;

import jeje.work.aeatbe.dto.User.UserDTO;
import lombok.Builder;

@Builder
public record ReviewDTO(Long id,
                        int rate,
                        String content,
                        UserDTO user,
                        Long productId) {

}
