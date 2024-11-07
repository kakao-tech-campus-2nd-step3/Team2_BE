package jeje.work.aeatbe.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewTest {

    private User user;
    private Product product;
    private Review review;

    @BeforeEach
    void setup() {
        user = User.builder()
            .kakaoId("12345")
            .userName("TestUser")
            .build();


        product = Product.builder()
            .id(1L)
            .productName("Sample Product")
            .price(1000L)
            .build();

        review = new Review(1L, 5, "Great product!", user, product);
    }

    @Test
    @DisplayName("Review 객체 생성 테스트")
    void testReviewCreation() {
        Review newReview = new Review(1L, 5, "Great product!", user, product);

        assertThat(newReview.getRate()).isEqualTo(5);
        assertThat(newReview.getContent()).isEqualTo("Great product!");
        assertThat(newReview.getUser()).isEqualTo(user);
        assertThat(newReview.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("Review 기본 생성자 검증")
    void testReviewNoArgsConstructor() {
        Review review = new Review();

        assertNotNull(review);
        assertNull(review.getContent());
        assertEquals(0, review.getRate());
        assertNull(review.getUser());
        assertNull(review.getProduct());
    }

    @Test
    @DisplayName("Review - User 관계 매핑 테스트")
    void testReviewUserMapping() {
        assertThat(review.getUser()).isNotNull();
        assertThat(review.getUser().getKakaoId()).isEqualTo("12345");
        assertThat(review.getUser().getUserName()).isEqualTo("TestUser");
    }

    @Test
    @DisplayName("Review - Product 관계 매핑 테스트")
    void testReviewProductMapping() {
        assertThat(review.getProduct()).isNotNull();
        assertThat(review.getProduct().getProductName()).isEqualTo("Sample Product");
        assertThat(review.getProduct().getPrice()).isEqualTo(1000L);
    }
}