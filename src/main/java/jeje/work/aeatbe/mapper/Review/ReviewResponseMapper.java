package jeje.work.aeatbe.mapper.Review;

import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.review.ReviewResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewResponseMapper {
    public ReviewResponseDTO toDTO(ReviewDTO reviewDTO, UserInfoResponseDTO userInfoResponseDTO) {
        return ReviewResponseDTO.builder()
            .id(reviewDTO.id())
            .rate(reviewDTO.rate())
            .content(reviewDTO.content())
            .user(userInfoResponseDTO)
            .productId(reviewDTO.productId())
            .productImgUrl(reviewDTO.productImgUrl())
            .build();
    }
}