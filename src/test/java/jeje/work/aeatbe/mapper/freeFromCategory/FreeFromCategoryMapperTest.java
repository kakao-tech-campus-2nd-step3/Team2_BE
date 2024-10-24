package jeje.work.aeatbe.mapper.freeFromCategory;

import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.mapper.freeFromCategory.FreeFromCategoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FreeFromCategoryMapperTest {

    private final FreeFromCategoryMapper mapper = new FreeFromCategoryMapper(null);

    @Test
    @DisplayName("엔티티를 DTO로 변환 테스트")
    public void testToDTO() {
        // Arrange
        FreeFromCategory entity = FreeFromCategory.builder()
                .id(1L)
                .freeFromType("Gluten")
                .build();

        // Act
        FreeFromCategoryDTO dto = mapper.toDTO(entity);

        // Assert
        assertNotNull(dto, "DTO는 null이 아님");
        assertEquals(entity.getId(), dto.id(), "ID 일치");
        assertEquals(entity.getFreeFromType(), dto.freeFromType(), "freeFromType 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요한 경우) 테스트")
    public void testToEntityWithId() {
        // Arrange
        FreeFromCategoryDTO dto = FreeFromCategoryDTO.builder()
                .id(1L)
                .freeFromType("Gluten")
                .build();

        // Act
        FreeFromCategory entity = mapper.toEntity(dto, true);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertEquals(dto.id(), entity.getId(), "ID 일치");
        assertEquals(dto.freeFromType(), entity.getFreeFromType(), "freeFromType 일치");
    }

    @Test
    @DisplayName("DTO를 엔티티로 변환(ID 필요하지 않은 경우) 테스트")
    public void testToEntityWithoutId() {
        // Arrange
        FreeFromCategoryDTO dto = FreeFromCategoryDTO.builder()
                .id(1L)
                .freeFromType("Gluten")
                .build();

        // Act
        FreeFromCategory entity = mapper.toEntity(dto, false);

        // Assert
        assertNotNull(entity, "엔티티는 null이 아님");
        assertNull(entity.getId(), "idRequired가 false인 경우 ID는 null");
        assertEquals(dto.freeFromType(), entity.getFreeFromType(), "freeFromType 일치");
    }
}
