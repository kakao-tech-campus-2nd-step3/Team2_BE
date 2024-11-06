package jeje.work.aeatbe.controller;

import java.util.List;
import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 리뷰 컨트롤러
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    /**
     * 칼럼 조회
     *
     * @param productId    상품 id
     * @return list 형식의 reviewDTO
     *
     */
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(
        @RequestParam(required = true) Long productId) {

        List<ReviewDTO> reviews = reviewService.getReviews(productId);
        return ResponseEntity.ok(reviews);
    }


    /**
     * 특정 유저에 대한 리뷰를 조회
     *
     * @param userId userId
     * @return 특정 유저에 관한 리뷰 리스트
     *
     * @todo: kakaoId -> userId로 수정 필요
     */
    @GetMapping("/my")
    public ResponseEntity<?> getReivewsByUser(@LoginUser Long userId) {
        List<ReviewDTO> review = reviewService.getReviewsByUser(userId);

        return ResponseEntity.ok(review);
    }


    /**
     * 새 리뷰 생성
     *
     * @param reviewDTO 리뷰 DTO
     * @param kakaoId   the kakao id
     * @return 201 created 응답 코드
     *
     * @todo: kakaoId -> userId로 수정 필요
     */
    @PostMapping
    public ResponseEntity<?> postReviews(@RequestBody ReviewDTO reviewDTO,
        @LoginUser Long userId) {
        reviewService.createReview(reviewDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 리뷰 수정
     *
     * @param id        리뷰 id
     * @param reviewDTO 리뷰 DTO
     * @param kakaoId   the kakao id
     * @return 200 ok 응답 코드
     *
     * @todo: kakaoId -> userId로 수정 필요
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReviews(@PathVariable Long id,
        @RequestBody ReviewDTO reviewDTO,
        @LoginUser Long userId) {
        reviewService.updateReviews(id, reviewDTO, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 삭제
     *
     * @param id      리뷰 id
     * @param kakaoId the kakao id
     * @return 204 응답 코드 반환
     *
     * @todo: kakaoId -> userId로 수정 필요
     *
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviews(@PathVariable Long id,
        @LoginUser Long userId) {
        reviewService.deleteReviews(id, userId);
        return ResponseEntity.noContent().build();
    }

}
