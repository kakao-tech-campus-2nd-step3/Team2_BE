package jeje.work.aeatbe.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTest {

    private Product product;

    @BeforeEach
    void setup() {
        product = Product.builder()
            .id(1L)
            .productName("Sample Product")
            .price(1000L)
            .nutritionalInfo("Sample nutritional info")
            .productImageUrl("http://example.com/product.jpg")
            .metaImageUrl("http://example.com/meta.jpg")
            .typeName("Snack")
            .manufacturer("Sample Manufacturer")
            .seller("Sample Seller")
            .capacity("200g")
            .ingredients("Sample ingredients")
            .build();
    }

    @Test
    @DisplayName("Product 객체 생성 테스트")
    void testProductCreation() {
        Product newProduct = Product.builder()
            .id(1L)
            .productName("Sample Product")
            .price(1000L)
            .nutritionalInfo("Sample nutritional info")
            .productImageUrl("http://example.com/product.jpg")
            .metaImageUrl("http://example.com/meta.jpg")
            .typeName("Snack")
            .manufacturer("Sample Manufacturer")
            .seller("Sample Seller")
            .capacity("200g")
            .ingredients("Sample ingredients")
            .build();

        assertThat(newProduct.getProductName()).isEqualTo("Sample Product");
        assertThat(newProduct.getPrice()).isEqualTo(1000L);
        assertThat(newProduct.getNutritionalInfo()).isEqualTo("Sample nutritional info");
        assertThat(newProduct.getProductImageUrl()).isEqualTo("http://example.com/product.jpg");
        assertThat(newProduct.getMetaImageUrl()).isEqualTo("http://example.com/meta.jpg");
        assertThat(newProduct.getTypeName()).isEqualTo("Snack");
        assertThat(newProduct.getManufacturer()).isEqualTo("Sample Manufacturer");
        assertThat(newProduct.getSeller()).isEqualTo("Sample Seller");
        assertThat(newProduct.getCapacity()).isEqualTo("200g");
        assertThat(newProduct.getIngredients()).isEqualTo("Sample ingredients");
    }

    @Test
    @DisplayName("Product 기본 생성자 검증")
    void testProductNoArgsConstructor() {
        Product product = new Product();

        assertNotNull(product);
        assertNull(product.getProductName());
        assertNull(product.getProductImageUrl());
        assertNull(product.getTypeName());
        assertNull(product.getPrice());
    }
}