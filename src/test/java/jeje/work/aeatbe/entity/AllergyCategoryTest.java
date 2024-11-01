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
public class AllergyCategoryTest {

    private AllergyCategory allergyCategory;

    @BeforeEach
    void setup() {
        allergyCategory = AllergyCategory.builder()
            .id(1L)
            .allergyType("Peanut")
            .build();
    }

    @Test
    @DisplayName("AllergyCategory 객체 생성 테스트")
    void testAllergyCategoryCreation() {
        AllergyCategory newCategory = AllergyCategory.builder()
            .id(1L)
            .allergyType("Peanut")
            .build();

        assertThat(newCategory.getId()).isEqualTo(1L);
        assertThat(newCategory.getAllergyType()).isEqualTo("Peanut");
    }

    @Test
    @DisplayName("AllergyCategory 기본 생성자 검증")
    void testAllergyCategoryNoArgsConstructor() {
        AllergyCategory allergyCategory = new AllergyCategory();

        assertNotNull(allergyCategory);
        assertNull(allergyCategory.getAllergyType());
    }

    @Test
    @DisplayName("Null 필드 검증 테스트")
    void testAllergyCategoryNullable() {
        allergyCategory = AllergyCategory.builder()
            .id(1L)
            .allergyType(null)
            .build();

        assertThat(allergyCategory.getAllergyType()).isNull();
    }

}