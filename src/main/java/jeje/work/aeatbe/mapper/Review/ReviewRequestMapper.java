package jeje.work.aeatbe.mapper.Review;

import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.review.ReviewRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewRequestMapper {
    public ReviewDTO toDTO(ReviewRequestDTO reviewRequestDTO) {
        return ReviewDTO.builder()
                .rate(reviewRequestDTO.rate())
                .content(reviewRequestDTO.content())
                .productId(reviewRequestDTO.productId())
                .build();
    }
}
