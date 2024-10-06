package jeje.work.aeatbe.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FreeFromCategoryTest {

    private FreeFromCategory freeFromCategory;

    @BeforeEach
    void setup() {
        freeFromCategory = new FreeFromCategory(1, "Gluten-Free");
    }

    @Test
    @DisplayName("FreeFromCategory 객체 생성 테스트")
    void testFreeFromCategoryCreation() {
        FreeFromCategory newCategory = new FreeFromCategory(1, "Gluten-Free");

        assertThat(newCategory.getId()).isEqualTo(1);
        assertThat(newCategory.getFreeFromType()).isEqualTo("Gluten-Free");
    }

    @Test
    @DisplayName("Null 필드 검증 테스트")
    void testFreeFromCategoryNullalbe() {
        freeFromCategory = new FreeFromCategory(1, null);

        assertThat(freeFromCategory.getFreeFromType()).isNull();
    }
}
