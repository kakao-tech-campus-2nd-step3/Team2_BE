package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductFreeFromDTO;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductFreeFrom;
import jeje.work.aeatbe.service.FreeFromCategoryService;
import jeje.work.aeatbe.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductFreeFromMapperTest {

    private final ProductService productService = Mockito.mock(ProductService.class);
    private final FreeFromCategoryService freeFromCategoryService = Mockito.mock(FreeFromCategoryService.class);
    private final ProductFreeFromMapper mapper = new ProductFreeFromMapper(productService, freeFromCategoryService);

    private Product product;
    private FreeFromCategory freeFromCategory;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1L)
                .productName("테스트 제품")
                .build();

        freeFromCategory = FreeFromCategory.builder()
                .id(1L)
                .freeFromType("Gluten-Free")
                .build();

        // Mock behavior
        when(productService.findById(1L)).thenReturn(product);
        when(freeFromCategoryService.findById(1L)).thenReturn(freeFromCategory);
    }

    @Test
    @DisplayName("엔티티를 DTO로 변환 테스트")
    public void testToDTO() {
        // Arrange
        ProductFreeFrom entity = ProductFreeFrom.builder()
                .id(1L)
                .product(product)
                .freeFromCategory(freeFromCategory)
                .build();

        // Act
        ProductFreeFromDTO dto = mapper.toDTO(entity);

        // Assert
        assertNotNull(dto, "DTO는 null이 아님");
        assertEquals(entity.getId(), dto.id(), "ID 일치");
        assertEquals(entity.getProduct().getId(), dto.productId(), "productId 일치");
        assertEquals(entity.getFreeFromCategory().getId(), dto.freeFromId(), "freeFromId 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요한 경우) 테스트")
    public void testToEntityWithId() {
        // Arrange
        ProductFreeFromDTO dto = ProductFreeFromDTO.builder()
                .id(1L)
                .productId(1L)
                .freeFromId(1L)
                .build();

        // Act
        ProductFreeFrom entity = mapper.toEntity(dto, true);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertEquals(dto.id(), entity.getId(), "ID 일치");
        assertNotNull(entity.getProduct(), "Product는 null이 아님");
        assertNotNull(entity.getFreeFromCategory(), "FreeFromCategory는 null이 아님");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요하지 않은 경우) 테스트")
    public void testToEntityWithoutId() {
        // Arrange
        ProductFreeFromDTO dto = ProductFreeFromDTO.builder()
                .id(1L)
                .productId(1L)
                .freeFromId(1L)
                .build();

        // Act
        ProductFreeFrom entity = mapper.toEntity(dto, false);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertNull(entity.getId(), "idRequired가 false인 경우 ID는 null");
        assertNotNull(entity.getProduct(), "Product는 null이 아님");
        assertNotNull(entity.getFreeFromCategory(), "FreeFromCategory는 null이 아님");
    }
}
