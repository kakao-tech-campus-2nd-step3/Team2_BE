package jeje.work.aeatbe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.user.UserDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.Review;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.repository.ProductRepository;
import jeje.work.aeatbe.repository.ReviewRepository;
import jeje.work.aeatbe.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * 리뷰 서비스 레이어
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    /**
     * 리뷰 조회
     *
     * @param productId    상품 id
     * @return list 형식의 reviewDTO
     *
     * @todo 토큰에서 파싱된 정보를 기반으로 userId에 들어갈 값을 수정 해야 함
     */
    public List<ReviewDTO> getReviews(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("해당 product_id로 조회된 리뷰가 없습니다.");
        }

        return reviews.stream()
            .map(review -> ReviewDTO.builder()
                .id(review.getId())
                .rate(review.getRate())
                .content(review.getContent())
                .user(new UserDTO(
                    review.getUser().getId(),
                    review.getUser().getUserName(),
                    review.getUser().getUserImgUrl()
                ))
                .productId(review.getProduct().getId())
                .build()
            )
            .collect(Collectors.toList());
    }

    /**
     * 특정 유저의 리뷰 조회
     *
     * @param userId 카카오 id
     * @return 특정 유저에 대한 list 형식의 reviewDTO
     *
     * todo: kakaoId가 아닌 userId를 받아와서 작업
     */
    public List<ReviewDTO> getReviewsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findByUserId(userId);

        return reviews.stream()
            .map(review -> ReviewDTO.builder()
                .id(review.getId())
                .rate(review.getRate())
                .content(review.getContent())
                .productImgUrl(Optional.ofNullable(review.getProduct().getProductImageUrl()))
                .build()
            )
            .collect(Collectors.toList());
    }

    /**
     * 해당 상품의 리뷰 평균 평점 조회
     * @param productId 상품 id
     * @return 리뷰 평균 평점
     */
    public Double getAverageRating(Long productId) {
        List<ReviewDTO> reviews = getReviews(productId);
        return reviews.stream()
            .mapToDouble(ReviewDTO::rate)
            .average()
            .orElse(0);
    }

    /**
     * 새 리뷰 생성
     *
     * @param reviewDTO 리뷰 DTO
     * @param userId   카카오 id
     *
     * @todo: kakaoId가 아닌 userId를 받아와서 작업
     */
    public void createReview(ReviewDTO reviewDTO, Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Product product = productRepository.findById(reviewDTO.productId())
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Review review = new Review(
            reviewDTO.id(),
            reviewDTO.rate(),
            reviewDTO.content(),
            user,
            product
        );

        reviewRepository.save(review);

    }

    /**
     * 리뷰 수정
     *
     * @param id        리뷰 id
     * @param reviewDTO 리뷰 DTO
     *
     * @todo: kakaoId가 아닌 userId를 받아와서 작업
     */
    public void updateReviews(Long id, ReviewDTO reviewDTO, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review existingReview = reviewRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("해당 상품의 리뷰가 존재하지 않습니다."));

        if (!existingReview.getUser().getId().equals(userId)) {
            throw new IllegalStateException("해당 리뷰를 수정할 권한이 없습니다.");
        }

        Review updateReview = new Review(
            existingReview.getId(),
            reviewDTO.rate(),
            reviewDTO.content(),
            existingReview.getUser(),
            existingReview.getProduct()
        );

        reviewRepository.save(updateReview);
    }

    /**
     * 리뷰 삭제
     *
     * @param id      리뷰 id
     * @param userId 유저 id
     *
     * @todo kakaoId가 아닌 userId를 받아와서 작업
     */
    public void deleteReviews(Long id, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review existingReview = reviewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        if (!existingReview.getUser().getId().equals(userId)) {
            throw new IllegalStateException("해당 리뷰를 삭제할 권한이 없습니다.");
        }

        reviewRepository.delete(existingReview);
    }

}
