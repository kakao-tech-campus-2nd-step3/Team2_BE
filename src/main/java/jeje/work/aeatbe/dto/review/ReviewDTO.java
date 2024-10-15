package jeje.work.aeatbe.dto.review;

import jeje.work.aeatbe.dto.UserDTO;
import lombok.Builder;

@Builder
public record ReviewDTO(Long id,
                        int rate,
                        String content,
                        UserDTO user,
                        Long productId) {

}
