package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.mapper.product.ProductResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductResponseMapperTest {

    private final ProductResponseMapper mapper = new ProductResponseMapper();
    private ProductDTO productDTO;
    private Double averageRating;
    private List<String> freeFromList;
    private List<String> allergyList;

    @BeforeEach
    public void setUp() {
        productDTO = ProductDTO.builder()
                .id(1L)
                .productName("테스트 제품")
                .price(10000L)
                .productImageUrl("http://example.com/product.jpg")
                .seller("http://example.com/seller")
                .metaImageUrl("간단한 설명")
                .build();

        averageRating = 4.5;
        freeFromList = List.of("Gluten-Free", "Sugar-Free");
        allergyList = List.of("Peanuts", "Soy");
    }

    @Test
    @DisplayName("DTO를 ProductResponseDTO로 변환(ID 필요한 경우) 테스트")
    public void testToEntityWithId() {
        // Act
        ProductResponseDTO responseDTO = mapper.toEntity(productDTO, averageRating, freeFromList, allergyList, true);

        // Assert
        assertNotNull(responseDTO, "ProductResponseDTO는 null이 아님");
        assertEquals(productDTO.id(), responseDTO.id(), "ID 일치");
        assertEquals(productDTO.productName(), responseDTO.name(), "제품명 일치");
        assertEquals(productDTO.price(), responseDTO.price(), "가격 일치");
        assertEquals(productDTO.productImageUrl(), responseDTO.imgUrl(), "이미지 URL 일치");
        assertEquals(averageRating, responseDTO.rating(), "평균 평점 일치");
        assertEquals(productDTO.seller(), responseDTO.ProductUrl(), "판매자 URL 일치");
        assertEquals(productDTO.metaImageUrl(), responseDTO.description(), "설명 일치");
        assertArrayEquals(freeFromList.toArray(), responseDTO.freeFrom(), "freeFrom 목록 일치");
        assertArrayEquals(allergyList.toArray(), responseDTO.allergy(), "allergy 목록 일치");
    }

    @Test
    @DisplayName("DTO를 ProductResponseDTO로 변환(ID 필요하지 않은 경우) 테스트")
    public void testToEntityWithoutId() {
        // Act
        ProductResponseDTO responseDTO = mapper.toEntity(productDTO, averageRating, freeFromList, allergyList, false);

        // Assert
        assertNotNull(responseDTO, "ProductResponseDTO는 null이 아님");
        assertNull(responseDTO.id(), "idRequired가 false인 경우 ID는 null");
        assertEquals(productDTO.productName(), responseDTO.name(), "제품명 일치");
        assertEquals(productDTO.price(), responseDTO.price(), "가격 일치");
        assertEquals(productDTO.productImageUrl(), responseDTO.imgUrl(), "이미지 URL 일치");
        assertEquals(averageRating, responseDTO.rating(), "평균 평점 일치");
        assertEquals(productDTO.seller(), responseDTO.ProductUrl(), "판매자 URL 일치");
        assertEquals(productDTO.metaImageUrl(), responseDTO.description(), "설명 일치");
        assertArrayEquals(freeFromList.toArray(), responseDTO.freeFrom(), "freeFrom 목록 일치");
        assertArrayEquals(allergyList.toArray(), responseDTO.allergy(), "allergy 목록 일치");
    }
}
