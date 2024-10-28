package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperTest {

    private final ProductMapper mapper = new ProductMapper();

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1L)
                .nutritionalInfo("Calories: 200, Fat: 10g")
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .typeName("Snack")
                .manufacturer("FoodCorp")
                .seller("SuperMarket")
                .capacity("200g")
                .productName("Delicious Snack")
                .ingredients("Wheat, Sugar, Salt")
                .price(5000L)
                .build();
    }

    @Test
    @DisplayName("엔티티를 DTO로 변환 테스트")
    public void testToDTO() {
        // Act
        ProductDTO dto = mapper.toDTO(product);

        // Assert
        assertNotNull(dto, "DTO는 null이 아님");
        assertEquals(product.getId(), dto.id(), "ID 일치");
        assertEquals(product.getNutritionalInfo(), dto.nutritionalInfo(), "영양 정보 일치");
        assertEquals(product.getProductImageUrl(), dto.productImageUrl(), "제품 이미지 URL 일치");
        assertEquals(product.getMetaImageUrl(), dto.metaImageUrl(), "메타 이미지 URL 일치");
        assertEquals(product.getTypeName(), dto.typeName(), "타입 이름 일치");
        assertEquals(product.getManufacturer(), dto.manufacturer(), "제조사 일치");
        assertEquals(product.getSeller(), dto.seller(), "판매자 일치");
        assertEquals(product.getCapacity(), dto.capacity(), "용량 일치");
        assertEquals(product.getProductName(), dto.productName(), "제품명 일치");
        assertEquals(product.getIngredients(), dto.ingredients(), "성분 일치");
        assertEquals(product.getPrice(), dto.price(), "가격 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요한 경우) 테스트")
    public void testToEntityWithId() {
        // Arrange
        ProductDTO dto = ProductDTO.builder()
                .id(1L)
                .nutritionalInfo("Calories: 200, Fat: 10g")
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .typeName("Snack")
                .manufacturer("FoodCorp")
                .seller("SuperMarket")
                .capacity("200g")
                .productName("Delicious Snack")
                .ingredients("Wheat, Sugar, Salt")
                .price(5000L)
                .build();

        // Act
        Product entity = mapper.toEntity(dto, true);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertEquals(dto.id(), entity.getId(), "ID 일치");
        assertEquals(dto.nutritionalInfo(), entity.getNutritionalInfo(), "영양 정보 일치");
        assertEquals(dto.productImageUrl(), entity.getProductImageUrl(), "제품 이미지 URL 일치");
        assertEquals(dto.metaImageUrl(), entity.getMetaImageUrl(), "메타 이미지 URL 일치");
        assertEquals(dto.typeName(), entity.getTypeName(), "타입 이름 일치");
        assertEquals(dto.manufacturer(), entity.getManufacturer(), "제조사 일치");
        assertEquals(dto.seller(), entity.getSeller(), "판매자 일치");
        assertEquals(dto.capacity(), entity.getCapacity(), "용량 일치");
        assertEquals(dto.productName(), entity.getProductName(), "제품명 일치");
        assertEquals(dto.ingredients(), entity.getIngredients(), "성분 일치");
        assertEquals(dto.price(), entity.getPrice(), "가격 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요하지 않은 경우) 테스트")
    public void testToEntityWithoutId() {
        // Arrange
        ProductDTO dto = ProductDTO.builder()
                .id(1L)
                .nutritionalInfo("Calories: 200, Fat: 10g")
                .productImageUrl("http://example.com/product.jpg")
                .metaImageUrl("http://example.com/meta.jpg")
                .typeName("Snack")
                .manufacturer("FoodCorp")
                .seller("SuperMarket")
                .capacity("200g")
                .productName("Delicious Snack")
                .ingredients("Wheat, Sugar, Salt")
                .price(5000L)
                .build();

        // Act
        Product entity = mapper.toEntity(dto, false);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertNull(entity.getId(), "idRequired가 false인 경우 ID는 null");
        assertEquals(dto.nutritionalInfo(), entity.getNutritionalInfo(), "영양 정보 일치");
        assertEquals(dto.productImageUrl(), entity.getProductImageUrl(), "제품 이미지 URL 일치");
        assertEquals(dto.metaImageUrl(), entity.getMetaImageUrl(), "메타 이미지 URL 일치");
        assertEquals(dto.typeName(), entity.getTypeName(), "타입 이름 일치");
        assertEquals(dto.manufacturer(), entity.getManufacturer(), "제조사 일치");
        assertEquals(dto.seller(), entity.getSeller(), "판매자 일치");
        assertEquals(dto.capacity(), entity.getCapacity(), "용량 일치");
        assertEquals(dto.productName(), entity.getProductName(), "제품명 일치");
        assertEquals(dto.ingredients(), entity.getIngredients(), "성분 일치");
        assertEquals(dto.price(), entity.getPrice(), "가격 일치");
    }
}
