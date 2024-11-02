package jeje.work.aeatbe.service;

import java.util.Arrays;
import java.util.Collections;
import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.entity.ProductFreeFrom;
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
import org.mockito.MockitoAnnotations;
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

    @Mock
    private ProductAllergyService productAllergyService;

    @Mock
    private ProductFreeFromService productFreeFromService;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private Product product;
    private ProductResponseDTO productResponseDTO;
    private ProductDTO updatedProductDTO;
    private Product updatedProduct;
    private List<FreeFromCategoryDTO> freeFromCategories = new ArrayList<>(), oldFreeFromCategories = new ArrayList<>() , updatedFreeFromCategories = new ArrayList<>();
    private List<AllergyCategoryDTO> allergyCategories = new ArrayList<>(), oldAllergyCategories = new ArrayList<>(), updatedAllergyCategories = new ArrayList<>();

    private AllergyCategoryDTO allergyDairy;
    private AllergyCategoryDTO allergyNuts;
    private FreeFromCategoryDTO freeFromGluten;
    private FreeFromCategoryDTO freeFromDairy;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 알러지 카테고리 DTO 인스턴스 생성
        AllergyCategoryDTO allergyGluten = new AllergyCategoryDTO(1L, "Gluten-Free");
        AllergyCategoryDTO allergyDairy = new AllergyCategoryDTO(2L, "Dairy-Free");
        AllergyCategoryDTO allergyPeanuts = new AllergyCategoryDTO(3L, "Peanuts");
        AllergyCategoryDTO allergyNuts = new AllergyCategoryDTO(4L, "Nuts");

        // 알러지 엔티티 객체를 생성
        List<ProductAllergy> productAllergies = new ArrayList<>();
        productAllergies.add(new ProductAllergy(1L, product, new AllergyCategory(1L, "Gluten")));
        productAllergies.add(new ProductAllergy(2L, product, new AllergyCategory(2L, "Dairy")));
        productAllergies.add(new ProductAllergy(3L, product, new AllergyCategory(3L, "Peanuts")));
        productAllergies.add(new ProductAllergy(4L, product, new AllergyCategory(4L, "Nuts")));

        // 프리프롬 카테고리 DTO 인스턴스 생성
        FreeFromCategoryDTO freeFromGluten = new FreeFromCategoryDTO(1L, "Gluten-Free");
        FreeFromCategoryDTO freeFromDairy = new FreeFromCategoryDTO(2L, "Dairy-Free");

        // 프리프롬 엔티티 객체를 생성
        List<ProductFreeFrom> productFreeFroms = new ArrayList<>();
        productFreeFroms.add(new ProductFreeFrom(1L, product, new FreeFromCategory(1L, "Gluten-Free")));
        productFreeFroms.add(new ProductFreeFrom(2L, product, new FreeFromCategory(2L, "Dairy-Free")));

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
                .productAllergies(productAllergies)
                .productFreeFroms(productFreeFroms)
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

        freeFromCategories.add(new FreeFromCategoryDTO(1L, "Gluten"));
        freeFromCategories.add(new FreeFromCategoryDTO(1L, "Dairy"));

        allergyCategories.add(new AllergyCategoryDTO(1L, "Peanuts"));
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
        // given
        ProductDTO productDTO1 = productDTO;
        List<String> allergies = Arrays.asList("Nuts", "Dairy");
        List<String> freeFroms = Arrays.asList("Gluten", "Soy");
        Product mockProduct = product;

        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(mockProduct);
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO1);
        when(productResponseMapper.toEntity(any(), any(), any(), any(), anyBoolean()))
            .thenReturn(productResponseDTO);

        // when
        ProductResponseDTO result = productService.createProduct(productDTO1, allergies, freeFroms);

        // then
        assertEquals(productDTO1.id(), result.id());
        verify(productAllergyService, times(2)).createProductAllergy(any(), any());
        verify(productFreeFromService, times(2)).createProductFreeFrom(any(), any());
    }


    @Test
    @DisplayName("상품 업데이트 - 성공")
    void updateProduct_Success() {
//        Long productId = 1L;
//        ProductDTO productDTO1 = updatedProductDTO;
//        List<String> allergies = Collections.singletonList("Nuts");
//        List<String> freeFroms = Collections.singletonList("Gluten-Free");
//
//        Product mockProduct = product;
//        ProductResponseDTO productResponseDTO1 = productResponseDTO;
//
//        // 상품이 존재한다고 가정
//        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
//        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO1);
//        when(productMapper.toEntity(any(ProductDTO.class), anyBoolean())).thenReturn(mockProduct);
//        when(productResponseMapper.toEntity(any(ProductDTO.class), anyDouble(), anyList(), anyList(), any(), anyBoolean()))
//            .thenReturn(productResponseDTO1);
//
//        ProductResponseDTO result = productService.updateProduct(productId, productDTO1, allergies, freeFroms);
//
//        assertNotNull(result, "The result should not be null");
//        verify(productRepository).save(any(Product.class));
//        verify(productAllergyService).deleteProductAllergy(anyLong());
//        verify(productAllergyService).createProductAllergy(any(Product.class), any());
//        verify(productFreeFromService).deleteProductFreeFrom(anyLong());
//        verify(productFreeFromService).createProductFreeFrom(any(Product.class), any());
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