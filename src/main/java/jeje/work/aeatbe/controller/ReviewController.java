package jeje.work.aeatbe.controller;

import java.util.List;
import jeje.work.aeatbe.dto.ReviewDTO;
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

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(
        @RequestParam(required = false) boolean searchByUser,
        @RequestParam(required = true) Long productId,
        @RequestHeader(required = false, value = "Authorization") String token
    ) {
        if (searchByUser && (token == null || token.isEmpty()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        /* token 부분은 추후 토큰에서 필요한 정보를 뽑아서 대체 할 예정입니다.*/
        List<ReviewDTO> reviews = reviewService.getReviews(searchByUser, productId, token);
        return ResponseEntity.ok(reviews);
    }


    @PostMapping
    public ResponseEntity<?> postReviews(@RequestBody ReviewDTO reviewDTO,
        @RequestHeader(required = true, value = "Authorization") String token) {

        // 이 부분은 추후 수정 될 예정
        reviewService.createReview(reviewDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReviews(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReviews(id, reviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviews(@PathVariable Long id) {
        reviewService.deleteReviews(id);
        return ResponseEntity.noContent().build();
    }

}
