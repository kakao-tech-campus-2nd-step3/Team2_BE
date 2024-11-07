package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.exception.ProductNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.mapper.product.ProductResponseMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductResponseMapper productResponseMapper;

    @Mock
    private ReviewService reviewService;

    @Mock
    private AllergyCategoryService allergyCategoryService;

    @Mock
    private FreeFromCategoryService freeFromCategoryService;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private Product product;
    private ProductResponseDTO productResponseDTO;
    private ProductDTO updatedProductDTO;
    private Product updatedProduct;
    private List<FreeFromCategoryDTO> freeFromCategories = new ArrayList<>(), oldFreeFromCategories = new ArrayList<>() , updatedFreeFromCategories = new ArrayList<>();
    private List<AllergyCategoryDTO> allergyCategories = new ArrayList<>(), oldAllergyCategories = new ArrayList<>(), updatedAllergyCategories = new ArrayList<>();


    @BeforeEach
    void setUp() {
        productDTO = ProductDTO.builder()
                .id(1L)
                .productName("Test Product")
                .price(100L)
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .seller("Test Seller")
                .build();

        product = Product.builder()
                .id(1L)
                .productName("Test Product")
                .price(100L)
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .seller("Test Seller")
                .build();

        productResponseDTO = ProductResponseDTO.builder()
                .id(1L)
                .name("Test Product")
                .price(100L)
                .imgUrl("http://example.com/product.jpg")
                .rating(4.5)
                .ProductUrl("http://example.com/product")
                .description("Test Description")
                .freeFrom(new String[]{"Gluten", "Dairy"})
                .allergy(new String[]{"Peanuts"})
                .build();

        updatedProductDTO = ProductDTO.builder()
                .id(1L)
                .productName("Updated Product")
                .price(150L)
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .seller("Test Seller")
                .build();

        updatedProduct = Product.builder()
                .id(1L)
                .productName("Updated Product")
                .price(150L)
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .seller("Test Seller")
                .build();
    }

    @Test
    @DisplayName("상품 엔티티 조회 - 성공")
    void getProductEntity_Success() {
        // given
        Long productId = product.getId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        Product result = productService.getProductEntity(productId);

        // then
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getProductName(), result.getProductName());
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("상품 엔티티 조회 - 실패 (상품 없음)")
    void getProductEntity_NotFound() {
        // given
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ProductNotFoundException.class, () -> productService.getProductEntity(productId));
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("상품 생성 - 성공")
    void createProduct_Success() {
        //TODO: Implement this test
    }

    @Test
    @DisplayName("상품 업데이트 - 성공")
    void updateProduct_Success() {
        //TODO: Implement this test
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    void deleteProduct_Success() {
        // given
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        // when
        productService.deleteProduct(product.getId());

        // then
        verify(productRepository).findById(product.getId());
        verify(productRepository).delete(product);
    }
}