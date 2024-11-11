package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.review.ReviewDTO;
import jeje.work.aeatbe.dto.review.ReviewRequestDTO;
import jeje.work.aeatbe.dto.review.ReviewResponseDTO;
import jeje.work.aeatbe.dto.user.UserInfoResponseDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.Review;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.ReviewNotFoundException;
import jeje.work.aeatbe.mapper.Review.ReviewMapper;
import jeje.work.aeatbe.mapper.Review.ReviewRequestMapper;
import jeje.work.aeatbe.mapper.Review.ReviewResponseMapper;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    
    @Mock
    private ReviewResponseMapper reviewResponseMapper;
    
    @Mock
    private ReviewMapper reviewMapper;
    
    @Mock
    private ProductService productService;
    
    @Mock
    private ProductMapper productMapper;
    
    @Mock
    private UserService userService;
    
    @Mock
    private ReviewRequestMapper reviewRequestMapper;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private User user;
    private Product product;
    private ReviewDTO reviewDTO;
    private ReviewRequestDTO reviewRequestDTO;
    private ReviewResponseDTO reviewResponseDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .userName("testUser")
            .build();
        user.setId(1L);

        product = Product.builder()
                .id(1L)
                .productName("testProduct")
                .build();

        review = Review.builder()
                .id(1L)
                .rate(5L)
                .content("Great product!")
                .user(user)
                .product(product)
                .build();

        reviewDTO = ReviewDTO.builder()
            .id(1L)
            .rate(5L)
            .content("Great product!")
            .userId(1L)
            .productId(1L)
            .build();

        reviewRequestDTO = ReviewRequestDTO.builder()
            .rate(5L) 
            .content("Great product!")
            .productId(1L)
            .build();

        reviewResponseDTO = ReviewResponseDTO.builder()
            .id(1L)
            .rate(5L)
            .content("Great product!")
            .user(UserInfoResponseDTO.builder()
                .id(1L)
                .userName("testUser")
                .userImageUrl(null)
                .build())
            .build();
    }

    @Test
    @DisplayName("리뷰 엔티티 조회 성공 테스트")
    void getReviewEntity_Success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        
        Review foundReview = reviewService.getReviewEntity(1L);
        
        assertNotNull(foundReview);
        assertEquals(1L, foundReview.getId());
    }

    @Test
    @DisplayName("리뷰 엔티티 조회 실패 테스트")
    void getReviewEntity_ThrowsException() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> reviewService.getReviewEntity(99L));
    }

    @Test
    @DisplayName("상품별 리뷰 조회 성공 테스트")
    void getReviewEntitiesByProduct_Success() {
        when(reviewRepository.findByProductId(1L)).thenReturn(List.of(review));
        
        List<Review> reviews = reviewService.getReviewEntitiesByProduct(1L);
        
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
    }

    @Test
    @DisplayName("상품별 리뷰 조회 실패 테스트")
    void getReviewEntitiesByProduct_ThrowsException() {
        when(reviewRepository.findByProductId(99L)).thenReturn(List.of());
        
        assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewEntitiesByProduct(99L));
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    void createReview_Success() {
        ProductDTO productDTO = ProductDTO.builder()
            .id(1L)
            .build();

        when(reviewRequestMapper.toDTO(any())).thenReturn(reviewDTO);
        when(userService.findById(1L)).thenReturn(user);
        when(productService.getProductDTO(1L)).thenReturn(productDTO);  // 실제 객체 반환
        when(productMapper.toEntity(any(ProductDTO.class), eq(true))).thenReturn(product);
        when(reviewMapper.toEntity(any(ReviewDTO.class), any(User.class), any(Product.class), eq(false))).thenReturn(review);
        
        assertDoesNotThrow(() -> reviewService.createReview(reviewRequestDTO, 1L));
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 수정 성공 테스트")
    void updateReviews_Success() {
        when(reviewRequestMapper.toDTO(any())).thenReturn(reviewDTO);
        when(userService.findById(1L)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewMapper.toEntity(any(), any(), any(), eq(true))).thenReturn(review);
        
        assertDoesNotThrow(() -> reviewService.updateReviews(1L, reviewRequestDTO, 1L));
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 삭제 성공 테스트")
    void deleteReviews_Success() {
        when(userService.findById(1L)).thenReturn(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        
        assertDoesNotThrow(() -> reviewService.deleteReviews(1L, 1L));
        verify(reviewRepository).delete(review);
    }

    @Test
    @DisplayName("권한 없는 리뷰 삭제 실패 테스트")
    void deleteReviews_UnauthorizedFail() {
        User unauthorizedUser = User.builder().build();
        unauthorizedUser.setId(2L);
        when(userService.findById(2L)).thenReturn(unauthorizedUser);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        
        assertThrows(IllegalStateException.class, () -> reviewService.deleteReviews(1L, 2L));
    }
}