package jeje.work.aeatbe.dto.review;

import java.util.Optional;
import jeje.work.aeatbe.dto.user.UserDTO;
import lombok.Builder;

@Builder
public record ReviewDTO(Long id,
                        int rate,
                        String content,
                        UserDTO user,
                        Long productId,
                        Optional<String> productImgUrl) {

}
