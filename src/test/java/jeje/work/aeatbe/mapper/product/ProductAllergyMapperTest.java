package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.service.AllergyCategoryService;
import jeje.work.aeatbe.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductAllergyMapperTest {

    private final ProductService productService = Mockito.mock(ProductService.class);
    private final AllergyCategoryService allergyCategoryService = Mockito.mock(AllergyCategoryService.class);
    private final ProductAllergyMapper mapper = new ProductAllergyMapper(productService, allergyCategoryService);

    private Product product;
    private AllergyCategory allergyCategory;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1L)
                .productName("테스트 제품")
                .build();

        allergyCategory = AllergyCategory.builder()
                .id(1L)
                .allergyType("Peanut")
                .build();

        // Mock 정의
        when(productService.findById(1L)).thenReturn(product);
        when(allergyCategoryService.findById(1L)).thenReturn(allergyCategory);
    }

    @Test
    @DisplayName("엔티티를 DTO로 변환 테스트")
    public void testToDTO() {
        // Arrange
        ProductAllergy entity = ProductAllergy.builder()
                .id(1L)
                .product(product)
                .allergy(allergyCategory)
                .build();

        // Act
        ProductAllergyDTO dto = mapper.toDTO(entity);

        // Assert
        assertNotNull(dto, "DTO는 null이 아님");
        assertEquals(entity.getId(), dto.id(), "ID 일치");
        assertEquals(entity.getProduct().getId(), dto.productId(), "productId 일치");
        assertEquals(entity.getAllergy().getId(), dto.allergyId(), "allergyId 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요한 경우) 테스트")
    public void testToEntityWithId() {
        // Arrange
        ProductAllergyDTO dto = ProductAllergyDTO.builder()
                .id(1L)
                .productId(1L)
                .allergyId(1L)
                .build();

        // Act
        ProductAllergy entity = mapper.toEntity(dto, true);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertEquals(dto.id(), entity.getId(), "ID 일치");
        assertNotNull(entity.getProduct(), "Product는 null이 아님");
        assertNotNull(entity.getAllergy(), "Allergy는 null이 아님");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요하지 않은 경우) 테스트")
    public void testToEntityWithoutId() {
        // Arrange
        ProductAllergyDTO dto = ProductAllergyDTO.builder()
                .id(1L)
                .productId(1L)
                .allergyId(1L)
                .build();

        // Act
        ProductAllergy entity = mapper.toEntity(dto, false);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertNull(entity.getId(), "idRequired가 false인 경우 ID는 null");
        assertNotNull(entity.getProduct(), "Product는 null이 아님");
        assertNotNull(entity.getAllergy(), "Allergy는 null이 아님");
    }
}
