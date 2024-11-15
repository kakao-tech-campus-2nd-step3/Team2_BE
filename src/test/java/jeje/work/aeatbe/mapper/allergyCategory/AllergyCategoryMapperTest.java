package jeje.work.aeatbe.mapper.allergyCategory;

import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AllergyCategoryMapperTest {

    private final AllergyCategoryMapper mapper = new AllergyCategoryMapper();

    @Test
    @DisplayName("엔티티를 DTO로 변환 테스트")
    public void testToDTO() {
        // Arrange
        AllergyCategory entity = AllergyCategory.builder()
                .id(1L)
                .allergyType("Peanut")
                .build();

        // Act
        AllergyCategoryDTO dto = mapper.toDTO(entity);

        // Assert
        assertNotNull(dto, "DTO는 null이 아님");
        assertEquals(entity.getId(), dto.id(), "ID 일치");
        assertEquals(entity.getAllergyType(), dto.type(), "알레르기 유형 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요한 경우) 테스트")
    public void testToEntityWithId() {
        // Arrange
        AllergyCategoryDTO dto = AllergyCategoryDTO.builder()
                .id(1L)
                .type("Peanut")
                .build();

        // Act
        AllergyCategory entity = mapper.toEntity(dto, true);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertEquals(dto.id(), entity.getId(), "ID 일치");
        assertEquals(dto.type(), entity.getAllergyType(), "알레르기 유형 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요하지 않은 경우) 테스트")
    public void testToEntityWithoutId() {
        // Arrange
        AllergyCategoryDTO dto = AllergyCategoryDTO.builder()
                .id(1L)
                .type("Peanut")
                .build();

        // Act
        AllergyCategory entity = mapper.toEntity(dto, false);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertNull(entity.getId(), "idRequired가 false인 경우 ID는 null");
        assertEquals(dto.type(), entity.getAllergyType(), "알레르기 유형 일치");
    }
}
