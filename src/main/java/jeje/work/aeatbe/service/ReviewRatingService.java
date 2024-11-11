package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.entity.Review;
import jeje.work.aeatbe.exception.ReviewNotFoundException;
import jeje.work.aeatbe.mapper.Review.ReviewMapper;
import jeje.work.aeatbe.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewRatingService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    /**
     * 상품에 대한 리뷰 조회
     *
     * @param productId 상품 id
     * @return 리뷰 엔티티 리스트
     */
    @Transactional(readOnly = true)
    protected List<Review> getReviewEntitiesByProduct(Long productId) {
        var ret = reviewRepository.findByProductId(productId);

        if (ret.isEmpty()) {
            throw new ReviewNotFoundException("해당 product_id로 조회된 리뷰가 없습니다.");
        }

        return ret;
    }

    /**
     * 해당 상품의 리뷰 평균 평점 조회
     *
     * @param productId 상품 id
     * @return 리뷰 평균 평점
     */
    public Double getAverageRating(Long productId) {
        List<ReviewDTO> reviews = getReviewEntitiesByProduct(productId).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());

        return reviews.stream()
                .mapToDouble(ReviewDTO::rate)
                .average()
                .orElse(0);
    }

}
