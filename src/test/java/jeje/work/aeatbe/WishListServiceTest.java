package jeje.work.aeatbe;

import jeje.work.aeatbe.dto.wishlist.WishDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.entity.Wishlist;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.repository.WishlistRepository;
import jeje.work.aeatbe.service.ProductService;
import jeje.work.aeatbe.service.WishListService;
import jeje.work.aeatbe.utility.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private Long userIdFromToken;
    private String kakaoId;
    private User user;

    private Product oldProduct;
    private Product newProduct;
    private Wishlist wishlist;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        userIdFromToken = 1L;
        kakaoId = "kakao123";
        user = User.builder()
                .id(userIdFromToken)
                .userId(kakaoId)
                .build();

        when(jwtUtil.getKakaoId("token")).thenReturn(kakaoId);
        when(userRepository.findByUserId(kakaoId)).thenReturn(Optional.of(user));

        oldProduct = new Product(
                1L,
                "nutrionalInfo",
                "productImageUrl",
                "metaImageUrl",
                "typeName",
                "manufacturer",
                "seller",
                "capacity",
                "productName",
                "ingredients",
                1000L,
                null,
                null,
                null
        );

        newProduct =new Product(
                2L,
                "nutrionalInfo",
                "productImageUrl",
                "metaImageUrl",
                "typeName",
                "manufacturer",
                "seller",
                "capacity",
                "productNameChanged",
                "ingredients",
                1000L,
                null,
                null,
            null
        );

        wishlist = new Wishlist(1L, user, oldProduct);
    }

    @DisplayName("사용자의 위시리스트 조회")
    @Test
    public void testGetWishlist() {

        when(wishlistRepository.findByUserId(userIdFromToken)).thenReturn(List.of(wishlist));

        List<WishDTO> result = wishListService.getWishlist("token");

        assertEquals(1, result.size());
        assertEquals("productName", result.get(0).product().name());
        assertEquals(1000L, result.get(0).product().price());
    }

    @DisplayName("위시리스트의 항목 수정")
    @Test
    public void testUpdateWish() {
        Long wishId = 1L;
        Long newProductId = 2L;
        Wishlist wishlist = new Wishlist(wishId, user, oldProduct);

        when(wishlistRepository.findByIdAndUserId(wishId, userIdFromToken)).thenReturn(Optional.of(wishlist));
        when(productRepository.findById(newProductId)).thenReturn(Optional.of(newProduct));

        wishListService.updateWish("token", wishId, newProductId);

        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @DisplayName("위시리스트의 항목 삭제")
    @Test
    public void testDeleteWish() {
        Long wishId = 1L;
        Wishlist wishlist = new Wishlist(wishId, user, oldProduct);

        when(wishlistRepository.findByIdAndUserId(wishId, userIdFromToken)).thenReturn(Optional.of(wishlist));

        wishListService.deleteWish("token", wishId);

        verify(wishlistRepository, times(1)).delete(wishlist);
    }

    @DisplayName("위시리스트에 새로운 항목 추가")
    @Test
    public void testCreateWish() {
        when(productRepository.findById(2L)).thenReturn(Optional.of(newProduct));

        WishDTO result = wishListService.createWish("token", 2L);

        assertNotNull(result);
        assertEquals(2L, result.product().id());
        assertEquals("productNameChanged", result.product().name());
    }
}
