package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.review.ReviewRequestDTO;
import jeje.work.aeatbe.dto.review.ReviewResponseDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.Review;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.ReviewNotFoundException;
import jeje.work.aeatbe.mapper.Review.ReviewMapper;
import jeje.work.aeatbe.mapper.Review.ReviewRequestMapper;
import jeje.work.aeatbe.mapper.Review.ReviewResponseMapper;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 리뷰 서비스 레이어
 */
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final ReviewResponseMapper reviewResponseMapper;
    private final ReviewMapper reviewMapper;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UserService userService;
    private final ReviewRequestMapper reviewRequestMapper;

    /**
     * 리뷰 엔티티 조회
     * @param id 리뷰 id
     * @return 리뷰 엔티티
     */
    protected Review getReviewEntity(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    }

    /**
     * 상품에 대한 리뷰 조회
     * @param productId 상품 id
     * @return 리뷰 엔티티 리스트
     */
    protected List<Review> getReviewEntitiesByProduct(Long productId) {
        var ret = reviewRepository.findByProductId(productId);

        if (ret.isEmpty()) {
            throw new ReviewNotFoundException("해당 product_id로 조회된 리뷰가 없습니다.");
        }

        return ret;
    }

    /**
     * 사용자를 기반으로 리뷰 조회
     * @param userId 사용자 id
     * @return list 형식의 reviewDTO
     */
    protected List<Review> getReviewEntitiesByUser(Long userId) {
        userService.findById(userId);
        var ret = reviewRepository.findByUserId(userId);

        if (ret.isEmpty()) {
            throw new ReviewNotFoundException("해당 user_id로 조회된 리뷰가 없습니다.");
        }

        return ret;
    }

    /**
     * 리뷰 응답 DTO 생성
     * @param review 리뷰 DTO
     * @return 리뷰 응답 DTO
     */
    public ReviewResponseDTO getReviewResponseDTO(ReviewDTO review) {
        var userDTO = userService.getUserInfo(review.userId());
        return reviewResponseMapper.toDTO(review, userDTO);
    }

    /**
     * 리뷰 조회
     *
     * @param productId    상품 id
     * @return list 형식의 reviewDTO
     *
     * @todo 토큰에서 파싱된 정보를 기반으로 userId에 들어갈 값을 수정 해야 함
     */
    public List<ReviewResponseDTO> getReviews(Long productId) {
        List<Review> reviews = getReviewEntitiesByProduct(productId);

        return reviews.stream()
            .map(reviewMapper::toDTO)
            .map(this::getReviewResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * 특정 유저의 리뷰 조회
     *
     * @param userId 카카오 id
     * @return 특정 유저에 대한 list 형식의 reviewDTO
     *
     * @todo user를 사용하여 리뷰 삭제 권한 확인....
     */
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {

        List<Review> reviews = getReviewEntitiesByUser(userId);

        return reviews.stream()
            .map(reviewMapper::toDTO)
            .map(this::getReviewResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * 새 리뷰 생성
     *
     * @param reviewRequestDTO 리뷰 DTO
     * @param userId   카카오 id
     */
    public void createReview(ReviewRequestDTO reviewRequestDTO, Long userId) {

        ReviewDTO reviewDTO = reviewRequestMapper.toDTO(reviewRequestDTO);

        User user = userService.findById(userId);
        Product product = productMapper.toEntity(productService.getProductDTO(reviewDTO.productId()), true);

        Review review = reviewMapper.toEntity(reviewDTO, user, product, false);
        reviewRepository.save(review);
    }

    /**
     * 리뷰 수정
     *
     * @param id        리뷰 id
     * @param reviewRequestDTO 리뷰 DTO
     *
     * @todo user를 사용하여 리뷰 삭제 권한 확인....
     */
    public void updateReviews(Long id, ReviewRequestDTO reviewRequestDTO, Long userId) {

        ReviewDTO reviewDTO = reviewRequestMapper.toDTO(reviewRequestDTO);

        userService.findById(userId);
        Review existingReview = getReviewEntity(id);

        if (!existingReview.getUser().getId().equals(userId)) {
            throw new IllegalStateException("해당 리뷰를 수정할 권한이 없습니다.");
        }

        Review updateReview = reviewMapper.toEntity(reviewDTO, existingReview.getUser(), existingReview.getProduct(), true);

        reviewRepository.save(updateReview);
    }

    /**
     * 리뷰 삭제
     *
     * @param id      리뷰 id
     * @param userId 유저 id
     *
     * @todo user를 사용하여 리뷰 삭제 권한 확인....
     */
    public void deleteReviews(Long id, Long userId) {

        userService.findById(userId);
        Review existingReview = getReviewEntity(id);

        if (!existingReview.getUser().getId().equals(userId)) {
            throw new IllegalStateException("해당 리뷰를 삭제할 권한이 없습니다.");
        }

        reviewRepository.delete(existingReview);
    }

}
