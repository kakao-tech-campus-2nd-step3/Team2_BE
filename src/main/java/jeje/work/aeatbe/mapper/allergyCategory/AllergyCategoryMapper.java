package jeje.work.aeatbe.mapper.allergyCategory;

import jeje.work.aeatbe.dto.AllergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import org.springframework.stereotype.Component;

/**
 * 알레르기 카테고리 매퍼
 */
@Component
public class AllergyCategoryMapper{

    /**
     * Entity -> DTO
     * @param entity
     * @return DTO
     */
    public AllergyCategoryDTO toDTO(AllergyCategory entity) {
        return AllergyCategoryDTO.builder()
            .id(entity.getId())
            .allergyType(entity.getAllergyType())
            .build();
    }

    /**
     * DTO -> Entity
     * @param dto
     * @return Entity
     */
    public AllergyCategory toEntity(AllergyCategoryDTO dto, boolean idRequired) {
        return AllergyCategory.builder()
            .id(idRequired ? dto.id() : null)
            .allergyType(dto.allergyType())
            .build();
    }

    /**
     * DTO -> Entity
     * @param dto
     * @return Entity
     */
    public AllergyCategory toEntity(AllergyCategoryDTO dto) {
        return toEntity(dto, false);
    }
}
