package jeje.work.aeatbe.service;

import java.util.List;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.User.UserDTO;
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
     * @param searchByUser 마이페이지 여부(true = 마이페이지, false = 단순 상품 리뷰)
     * @param productId    상품 id
     * @param token        유저 토큰
     * @return list 형식의 reviewDTO
     *
     * @todo 토큰에서 파싱된 정보를 기반으로 userId에 들어갈 값을 수정 해야 함
     */
    public List<ReviewDTO> getReviews(boolean searchByUser, Long productId, String token) {
        List<Review> reviews = List.of();
        if (searchByUser) {
            if (token == null || token.isEmpty()) {
                throw new IllegalStateException("로그인이 필요합니다.");
            }

            Long userId = 1L;
            reviews = reviewRepository.findByUserId(userId);

        } else{
            reviews = reviewRepository.findByProductId(productId);
        }

        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("해당 product_id로 조회된 리뷰가 없습니다.");
        }

        return reviews.stream()
            .map(review -> new ReviewDTO(
                review.getId(),
                review.getRate(),
                review.getContent(),
                new UserDTO(
                    review.getUser().getId(),
                    review.getUser().getUserName(),
                    review.getUser().getUserImgUrl()
                ),
                review.getProduct().getId()
            ))
            .collect(Collectors.toList());
    }

    /**
     * 상품을 기반으로 리뷰 조회
     * @param productId 상품 id
     * @return list 형식의 reviewDTO
     */
    public List<ReviewDTO> getReviewsByProduct(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("해당 product_id로 조회된 리뷰가 없습니다.");
        }

        return reviews.stream()
            .map(review -> new ReviewDTO(
                review.getId(),
                review.getRate(),
                review.getContent(),
                new UserDTO(
                    review.getUser().getId(),
                    review.getUser().getUserName(),
                    review.getUser().getUserImgUrl()
                ),
                review.getProduct().getId()
            ))
            .collect(Collectors.toList());
    }

    /**
     * 해당 상품의 리뷰 평균 평점 조회
     * @param productId 상품 id
     * @return 리뷰 평균 평점
     */
    public Double getAverageRating(Long productId) {
        List<ReviewDTO> reviews = getReviewsByProduct(productId);
        return reviews.stream()
            .mapToDouble(ReviewDTO::rate)
            .average()
            .orElse(0);
    }

    /**
     * 새 리뷰 생성
     *
     * @param reviewDTO 리뷰 DTO
     * @param token     유저 토큰
     *
     * @todo 파싱된 정보에서 user에 대한 정보를 기반으로 userId와 하위 로직을 수정해야 함
     */
    public void createReview(ReviewDTO reviewDTO, String token) {
        Long userId = reviewDTO.user().id();

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
     */
    public void updateReviews(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("해당 상품의 리뷰가 존재하지 않습니다."));

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
     * @param id 리뷰 id
     */
    public void deleteReviews(Long id) {
        Review existingReview = reviewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID: " + id + "에 대한 리뷰를 찾을 수 없습니다."));

        reviewRepository.delete(existingReview);
    }

    /**
     * 상품 ID로 리뷰 삭제
     * @param productId 상품 id
     */
    public void deleteReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        reviewRepository.deleteAll(reviews);
    }

}
