package jeje.work.aeatbe.mapper.Review;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.review.ReviewResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewResponseMapper {
    public ReviewResponseDTO toDTO(ReviewDTO reviewDTO, UserInfoResponseDTO userInfoResponseDTO, ProductDTO productDto) {
        return ReviewResponseDTO.builder()
                .id(reviewDTO.id())
                .rate(reviewDTO.rate())
                .content(reviewDTO.content())
                .user(userInfoResponseDTO)
                .productId(reviewDTO.productId())
                .productImgUrl(productDto.productImageUrl())
                .productName(productDto.productName())
                .date(reviewDTO.date())
                .build();
    }
}
