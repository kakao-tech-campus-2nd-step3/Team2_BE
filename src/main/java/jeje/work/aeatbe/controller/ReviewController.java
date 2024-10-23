package jeje.work.aeatbe.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
     * @param searchByUser 마이페이지 여부(true = 마이페이지, false = 단순 상품 리뷰)
     * @param productId    상품 id
     * @param token        유저 토큰
     * @return list 형식의 reviewDTO
     *
     * @todo 토큰에서 필요한 정보를 추출하는 코드를 구현해야 함
     */
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(
        @RequestParam(required = false) boolean searchByUser,
        @RequestParam(required = true) Long productId,
        @RequestHeader(required = false, value = "Authorization") String token
    ) {
        if (searchByUser && (token == null || token.isEmpty()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<ReviewDTO> reviews = reviewService.getReviews(searchByUser, productId, token);
        return ResponseEntity.ok(reviews);
    }


    /**
     * 새 리뷰 생성
     *
     * @param reviewDTO 리뷰 DTO
     * @param token     유저 토큰
     * @return 201 created 응답 코드
     *
     * @todo 토큰에서 필요한 정보를 추출하는 코드를 구현해야 함
     * @todo 토큰 인증하는 로직 필요
     */
    @PostMapping
    public ResponseEntity<?> postReviews(@RequestBody ReviewDTO reviewDTO,
        @RequestHeader(required = true, value = "Authorization") String token) {

        reviewService.createReview(reviewDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 리뷰 수정
     *
     * @param id        리뷰 id
     * @param reviewDTO 리뷰 DTO
     * @param token     유저 토큰
     * @return 200 ok 응답 코드
     *
     * @todo 토큰에서 필요한 정보를 추출하는 코드를 구현해야 함
     * @todo 토큰 인증하는 로직 필요
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReviews(@PathVariable Long id,
        @RequestBody ReviewDTO reviewDTO,
        @RequestHeader(required = true, value = "Authorization") String token) {
        reviewService.updateReviews(id, reviewDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 삭제
     *
     * @param id    리뷰 id
     * @param token 유저 토큰
     * @return 204 응답 코드 반환
     *
     * @todo 토큰에서 필요한 정보를 추출하는 코드를 구현해야 함
     * @todo 토큰 인증하는 로직 필요
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviews(@PathVariable Long id,
        @RequestHeader(required = true, value = "Authorization") String token) {
        reviewService.deleteReviews(id);
        return ResponseEntity.noContent().build();
    }

}
