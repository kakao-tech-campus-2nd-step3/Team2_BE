package jeje.work.aeatbe;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.wishlist.WishDTO;
import jeje.work.aeatbe.dto.wishlist.WishProductDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.entity.Wishlist;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.repository.WishlistRepository;
import jeje.work.aeatbe.service.ProductService;
import jeje.work.aeatbe.service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class WishListServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WishListService wishListService;

    private User user;
    private Product product;
    private Wishlist wishlist;
    private WishDTO wishDTO;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .kakaoId("kakao_12345")
            .allergies("Nuts, Dairy")
            .freeFrom("Gluten")
            .userName("John Doe")
            .userImgUrl("http://example.com/user.jpg")
            .accessToken("access_token")
            .refreshToken("refresh_token")
            .build();
        user.setId(1L);

        product = Product.builder()
            .id(1L)
            .productName("Test Product")
            .price(100L)
            .productImageUrl("http://example.com/product.jpg")
            .typeName("Electronics")
            .build();

        wishlist = new Wishlist(1L, user, product);

        productDTO = ProductDTO.builder()
            .id(1L)
            .productName("Test Product")
            .price(100L)
            .productImageUrl("http://example.com/product.jpg")
            .build();

        wishDTO = WishDTO.builder()
            .id(1L)
            .product(WishProductDTO.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .imgurl(product.getProductImageUrl())
                .tag(product.getTypeName())
                .build())
            .build();
    }

    @Test
    @DisplayName("위시리스트 생성")
    void createWish_Success() {
        // given
        Long userId = user.getId();
        Long productId = product.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productService.getProductDTO(productId)).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO, true)).thenReturn(product);
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        // when
        WishDTO result = wishListService.createWish(userId, productId);

        // then
        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(productService).getProductDTO(productId);
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("위시리스트 조회")
    void getWishlist_Success() {
        // given
        Long userId = user.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(wishlistRepository.findByUserId(userId)).thenReturn(List.of(wishlist));

        // when
        List<WishDTO> result = wishListService.getWishlist(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(wishlist.getId(), result.get(0).id());
        verify(userRepository).findById(userId);
        verify(wishlistRepository).findByUserId(userId);
    }

    @Test
    @DisplayName("위시리스트 업데이트")
    void updateWish_Success() {
        // given
        Long userId = user.getId();
        Long wishId = wishlist.getId();
        Long newProductId = 2L;

        Product newProduct = Product.builder()
            .id(newProductId)
            .productName("Updated Product")
            .price(150L)
            .productImageUrl("http://example.com/product_updated.jpg")
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(wishlistRepository.findByIdAndUserId(wishId, userId)).thenReturn(Optional.of(wishlist));
        when(productService.getProductDTO(newProductId)).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO, true)).thenReturn(newProduct);

        // when
        wishListService.updateWish(userId, wishId, newProductId);

        // then
        verify(wishlistRepository).findByIdAndUserId(wishId, userId);
        verify(productService).getProductDTO(newProductId);
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("위시리스트 삭제")
    void deleteWish_Success() {
        // given
        Long userId = user.getId();
        Long wishId = wishlist.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(wishlistRepository.findByIdAndUserId(wishId, userId)).thenReturn(Optional.of(wishlist));

        // when
        wishListService.deleteWish(userId, wishId);

        // then
        verify(wishlistRepository).findByIdAndUserId(wishId, userId);
        verify(wishlistRepository).delete(wishlist);
    }
}