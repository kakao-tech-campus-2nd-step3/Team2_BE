package jeje.work.aeatbe.mapper.freeFromCategory;

import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.entity.FreeFromCategory;
import org.springframework.stereotype.Component;

/**
 * 알레르기 카테고리 매퍼
 */
@Component
public class FreeFromCategoryMapper {

    /**
     * Entity -> DTO
     *
     * @param freeFromCategory
     * @return DTO
     */
    public FreeFromCategoryDTO toDTO(FreeFromCategory freeFromCategory) {
        return FreeFromCategoryDTO.builder()
                .id(freeFromCategory.getId())
                .freeFromType(freeFromCategory.getFreeFromType())
                .build();
    }

    /**
     * DTO -> Entity
     *
     * @param freeFromCategoryDTO
     * @return Entity
     */
    public FreeFromCategory toEntity(FreeFromCategoryDTO freeFromCategoryDTO, boolean idRequired) {
        FreeFromCategory freeFromCategory = FreeFromCategory.builder()
                .id(idRequired ? freeFromCategoryDTO.id() : null)
                .freeFromType(freeFromCategoryDTO.freeFromType())
                .build();

        return freeFromCategory;
    }

    /**
     * DTO -> Entity
     *
     * @param freeFromCategoryDTO
     * @return Entity
     */
    public FreeFromCategory toEntity(FreeFromCategoryDTO freeFromCategoryDTO) {
        return toEntity(freeFromCategoryDTO, false);
    }
}
