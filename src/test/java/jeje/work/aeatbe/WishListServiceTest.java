package jeje.work.aeatbe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import jeje.work.aeatbe.dto.wishlist.WishDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.entity.Wishlist;
import jeje.work.aeatbe.repository.ProductRepository;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.repository.WishlistRepository;
import jeje.work.aeatbe.service.WishListService;
import jeje.work.aeatbe.utility.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.mockito.Mockito.*;

@SpringBootTest
public class WishListServiceTest {

    @MockBean
    private WishlistRepository wishlistRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private WishListService wishListService;

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        registry.add("spring.datasource.driver-class-name", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.H2Dialect");
    }

    @DisplayName("사용자의 위시리스트 조회")
    @Test
    public void testGetWishlist() {
        Long userIdFromToken = 1L;
        String kakaoId = "kakao123";

        User user = User.builder()
            .id(userIdFromToken)
            .userId(kakaoId)
            .build();
        Product product = new Product(1L, "allergens", "nutritionalInfo", "http://image.com", "http://metaimage.com", "Type Name", "Manufacturer", "Seller", "Capacity", "productName", "Ingredients", 1000L);
        Wishlist wishlist = new Wishlist(1L, user, product);

        when(jwtUtil.getKakaoId("token")).thenReturn(kakaoId);

        when(userRepository.findByUserId(kakaoId)).thenReturn(Optional.of(user));
        when(wishlistRepository.findByUserId(userIdFromToken)).thenReturn(List.of(wishlist));

        List<WishDTO> result = wishListService.getWishlist("token");

        assertEquals(1, result.size());
        assertEquals("productName", result.get(0).product().name());
        assertEquals(1000, result.get(0).product().price());
    }

    @DisplayName("위시리스트의 항목 수정")
    @Test
    public void testUpdateWish() {
        Long wishId = 1L;
        Long userIdFromToken = 1L;
        Long newProductId = 2L;
        String kakaoId = "kakao123";

        User user = User.builder()
            .id(userIdFromToken)
            .userId(kakaoId)
            .build();
        Product oldProduct = new Product(1L, "allergens", "nutritionalInfo", "http://image.com", "http://metaimage.com", "Type Name", "Manufacturer", "Seller", "Capacity", "oldProduct", "Ingredients", 500L);
        Product newProduct = new Product(2L, "allergens", "nutritionalInfo", "http://image.com", "http://metaimage.com", "Type Name", "Manufacturer", "Seller", "Capacity", "newProduct", "Ingredients", 1500L);
        Wishlist wishlist = new Wishlist(1L, user, oldProduct);

        when(jwtUtil.getKakaoId("token")).thenReturn(kakaoId);

        when(userRepository.findByUserId(kakaoId)).thenReturn(Optional.of(user));
        when(wishlistRepository.findByIdAndUserId(wishId, userIdFromToken)).thenReturn(Optional.of(wishlist));
        when(productRepository.findById(newProductId)).thenReturn(Optional.of(newProduct));

        wishListService.updateWish("token", wishId, newProductId);

        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @DisplayName("위시리스트의 항목 삭제")
    @Test
    public void testDeleteWish() {
        Long wishId = 1L;
        Long userIdFromToken = 1L;
        String kakaoId = "kakao123";

        User user = User.builder()
            .id(userIdFromToken)
            .userId(kakaoId)
            .build();
        Product product = new Product(1L, "allergens", "nutritionalInfo", "http://image.com", "http://metaimage.com", "Type Name", "Manufacturer", "Seller", "Capacity", "productName", "Ingredients", 1000L);
        Wishlist wishlist = new Wishlist(1L, user, product);

        when(jwtUtil.getKakaoId("token")).thenReturn(kakaoId);

        when(userRepository.findByUserId(kakaoId)).thenReturn(Optional.of(user));
        when(wishlistRepository.findByIdAndUserId(wishId, userIdFromToken)).thenReturn(Optional.of(wishlist));

        wishListService.deleteWish("token", wishId);

        verify(wishlistRepository, times(1)).delete(wishlist);
    }

    @DisplayName("위시리스트에 새로운 항목 추가")
    @Test
    public void testCreateWish() {
        Long userIdFromToken = 1L;
        String kakaoId = "kakao123";

        Product product = new Product(1L, "allergens", "nutritionalInfo", "http://image.com", "http://metaimage.com", "Type Name", "Manufacturer", "Seller", "Capacity", "Product Name", "Ingredients", 1000L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        User user = User.builder()
            .id(userIdFromToken)
            .userId(kakaoId)
            .build();
        when(userRepository.findByUserId(kakaoId)).thenReturn(Optional.of(user));

        when(jwtUtil.getKakaoId("token")).thenReturn(kakaoId);

        WishDTO result = wishListService.createWish("token", 1L);

        assertNotNull(result);
        assertEquals(1L, result.product().id());
        assertEquals(1000, result.product().price());
    }
}