//package jeje.work.aeatbe.entity;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class WishlistTest {
//
//    private User user;
//    private Product product;
//    private Wishlist wishlist;
//
//    @BeforeEach
//    void setup() {
//        user = User.builder()
//            .kakaoId("12345")
//            .userName("TestUser")
//            .build();
//
//
//        product = Product.builder()
//            .id(1L)
//            .productName("Sample Product")
//            .price(1000L)
//            .build();
//
//        wishlist = new Wishlist(1L, user, product);
//    }
//
//    @Test
//    @DisplayName("Wishlist 객체 생성 테스트")
//    void testWishlistCreation() {
//        Wishlist newWishlist = new Wishlist(1L, user, product);
//
//        assertThat(newWishlist.getUser()).isEqualTo(user);
//        assertThat(newWishlist.getProduct()).isEqualTo(product);
//    }
//
//    @Test
//    @DisplayName("Wishlist 기본 생성자 검증")
//    void testWishlistNoArgsConstructor() {
//        Wishlist wishlist = new Wishlist();
//
//        assertNotNull(wishlist);
//        assertNull(wishlist.getUser());
//        assertNull(wishlist.getProduct());
//    }
//
//    @Test
//    @DisplayName("Wishlist - User 관계 매핑 테스트")
//    void testWishlistUserMapping() {
//        assertThat(wishlist.getUser()).isNotNull();
//        assertThat(wishlist.getUser().getKakaoId()).isEqualTo("12345");
//        assertThat(wishlist.getUser().getUserName()).isEqualTo("TestUser");
//    }
//
//    @Test
//    @DisplayName("Wishlist - Product 관계 매핑 테스트")
//    void testWishlistProductMapping() {
//        assertThat(wishlist.getProduct()).isNotNull();
//        assertThat(wishlist.getProduct().getProductName()).isEqualTo("Sample Product");
//        assertThat(wishlist.getProduct().getPrice()).isEqualTo(1000L);
//    }
//}