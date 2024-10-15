package jeje.work.aeatbe.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductFreeFromTest {

    @Test
    @DisplayName("ProductfreeFrom 객체 생성 검증")
    void testProdctFreeFromCreation() {
        Product product = new Product();
        FreeFromCategory freeFromCategory = new FreeFromCategory();

        ProductFreeFrom productFreeFrom = new ProductFreeFrom(1L, product, freeFromCategory);

        assertEquals(1, productFreeFrom.getId());
        assertEquals(product, productFreeFrom.getProduct());
        assertEquals(freeFromCategory, productFreeFrom.getFreeFromCategory());
    }

    @Test
    @DisplayName("ProductFreeFrom 기본 생성자 검증")
    void testProductFreeFromNoArgsConstructor() {
        ProductFreeFrom productFreeFrom = new ProductFreeFrom();

        assertNotNull(productFreeFrom);
        assertNull(productFreeFrom.getProduct());
        assertNull(productFreeFrom.getFreeFromCategory());
    }

    @Test
    @DisplayName("ProductFreeFromAllergy 객체 equals 검증")
    void testProductFreeFromEquals() {
        Product product1 = new Product();
        Product product2 = new Product();
        FreeFromCategory freeFromCategory1 = new FreeFromCategory();
        FreeFromCategory freeFromCategory2 = new FreeFromCategory();

        ProductFreeFrom productFreeFrom1 = new ProductFreeFrom(1L, product1, freeFromCategory1);
        ProductFreeFrom productFreeFrom2 = new ProductFreeFrom(1L, product1, freeFromCategory1);
        ProductFreeFrom productFreeFrom3 = new ProductFreeFrom(2L, product2, freeFromCategory2);

        assertEquals(productFreeFrom1, productFreeFrom2);
        assertNotEquals(productFreeFrom1, productFreeFrom3);
    }

}
