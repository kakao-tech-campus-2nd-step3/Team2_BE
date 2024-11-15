package jeje.work.aeatbe.controller;

import jakarta.validation.Valid;
import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.review.ReviewRequestDTO;
import jeje.work.aeatbe.dto.review.ReviewResponseDTO;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param productId 상품 id
     * @return list 형식의 reviewDTO
     */
    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(
            @RequestParam(required = true) Long productId) {

        List<ReviewResponseDTO> reviews = reviewService.getReviews(productId);
        return ResponseEntity.ok(reviews);
    }


    /**
     * 특정 유저에 대한 리뷰를 조회
     *
     * @param loginUserInfo userId
     * @return 특정 유저에 관한 리뷰 리스트
     */
    @GetMapping("/my")
    public ResponseEntity<?> getReivewsByUser(@LoginUser LoginUserInfo loginUserInfo) {
        List<ReviewResponseDTO> review = reviewService.getReviewsByUser(loginUserInfo.userId());

        return ResponseEntity.ok(review);
    }


    /**
     * 새 리뷰 생성
     *
     * @param reviewDTO     리뷰 DTO
     * @param loginUserInfo the user id
     * @return 201 created 응답 코드
     */
    @PostMapping
    public ResponseEntity<?> postReviews(@RequestBody @Valid ReviewRequestDTO reviewDTO,
                                         @LoginUser LoginUserInfo loginUserInfo) {
        reviewService.createReview(reviewDTO, loginUserInfo.userId());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 리뷰 수정
     *
     * @param id            리뷰 id
     * @param reviewDTO     리뷰 DTO
     * @param loginUserInfo the user id
     * @return 200 ok 응답 코드
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReviews(@PathVariable Long id,
                                           @RequestBody @Valid ReviewRequestDTO reviewDTO,
                                           @LoginUser LoginUserInfo loginUserInfo) {
        reviewService.updateReviews(id, reviewDTO, loginUserInfo.userId());
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 삭제
     *
     * @param id            리뷰 id
     * @param loginUserInfo the user id
     * @return 204 응답 코드 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviews(@PathVariable Long id,
                                           @LoginUser LoginUserInfo loginUserInfo) {
        reviewService.deleteReviews(id, loginUserInfo.userId());
        return ResponseEntity.noContent().build();
    }

}
