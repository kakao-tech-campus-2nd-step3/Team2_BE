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

    @Autowired
    private WishListService wishListService;

    private Long userId;
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
        userId = 1L;
        kakaoId = "kakao123";
        user = User.builder()
            .id(userId)
            .userId(kakaoId)
            .build();

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
        when(wishlistRepository.findByUserId(userId)).thenReturn(List.of(wishlist));

        List<WishDTO> result = wishListService.getWishlist(kakaoId);

        assertEquals(1, result.size());
        assertEquals("productName", result.get(0).product().name());
        assertEquals(1000L, result.get(0).product().price());
    }

    @DisplayName("위시리스트의 항목 수정")
    @Test
    public void testUpdateWish() {
        Long wishId = 1L;
        Long newProductId = 2L;

        when(wishlistRepository.findByIdAndUserId(wishId, userId)).thenReturn(Optional.of(wishlist));
        when(productRepository.findById(newProductId)).thenReturn(Optional.of(newProduct));

        wishListService.updateWish(kakaoId, wishId, newProductId);

        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @DisplayName("위시리스트의 항목 삭제")
    @Test
    public void testDeleteWish() {
        Long wishId = 1L;

        when(wishlistRepository.findByIdAndUserId(wishId, userId)).thenReturn(Optional.of(wishlist));

        wishListService.deleteWish(kakaoId, wishId);

        verify(wishlistRepository, times(1)).delete(wishlist);
    }

    @DisplayName("위시리스트에 새로운 항목 추가")
    @Test
    public void testCreateWish() {
        Long productId = 2L;

        when(productRepository.findById(productId)).thenReturn(Optional.of(newProduct));

        WishDTO result = wishListService.createWish(kakaoId, productId);

        assertNotNull(result);
        assertEquals(productId, result.product().id());
        assertEquals("productNameChanged", result.product().name());
    }
}