package jeje.work.aeatbe.mapper.Review;

import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.Review;
import jeje.work.aeatbe.entity.User;

public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
            .id(review.getId())
            .rate(review.getRate())
            .content(review.getContent())
            .userId(review.getUser().getId())
            .productId(review.getProduct().getId())
            .productImgUrl(review.getProduct().getProductImageUrl())
            .build();
    }

    public static Review toEntity(ReviewDTO reviewDTO, User user, Product product, boolean idRequired) {
        return Review.builder()
            .id(idRequired ? reviewDTO.id() : null)
            .rate(reviewDTO.rate())
            .content(reviewDTO.content())
            .user(user)
            .product(product)
            .build();
    }

    public static Review toEntity(ReviewDTO reviewDTO, User user, Product product) {
        return toEntity(reviewDTO, user, product, false);
    }
}
