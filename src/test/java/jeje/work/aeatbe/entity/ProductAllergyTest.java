package jeje.work.aeatbe.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductAllergyTest {

    @Test
    @DisplayName("ProductAllergy 객체 생성 검증")
    void testProuctAllergyCreation() {
        Product product = new Product();
        AllergyCategory allergyCategory = new AllergyCategory();

        ProductAllergy productAllergy = new ProductAllergy(1L, product, allergyCategory);

        assertEquals(1, productAllergy.getId());
        assertEquals(product, productAllergy.getProduct());
        assertEquals(allergyCategory, productAllergy.getAllergy());
    }

    @Test
    @DisplayName("ProductAllergy 기본 생성자 검증")
    void testProductAllergyNoArgsConstructor() {
        ProductAllergy productAllergy = new ProductAllergy();

        assertNotNull(productAllergy);
        assertNull(productAllergy.getProduct());
        assertNull(productAllergy.getAllergy());
    }

    @Test
    @DisplayName("ProductAllergy 객체 equals 검증")
    void testProductAllergyEquals() {
        Product product1 = new Product();
        Product product2 = new Product();
        AllergyCategory allergy1 = new AllergyCategory();
        AllergyCategory allergy2 = new AllergyCategory();

        ProductAllergy productAllergy1 = new ProductAllergy(1L, product1, allergy1);
        ProductAllergy productAllergy2 = new ProductAllergy(1L, product1, allergy1);
        ProductAllergy productAllergy3 = new ProductAllergy(2L, product2, allergy2);

        assertEquals(productAllergy1, productAllergy2);
        assertNotEquals(productAllergy1, productAllergy3);
    }
}
