package jeje.work.aeatbe.mapper.allergyCategory;

import jeje.work.aeatbe.dto.AllergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.mapper.BaseEntityMapper;

public class AllergyCategoryMapper implements BaseEntityMapper<AllergyCategoryDTO, AllergyCategory> {

    public AllergyCategoryDTO toDTO(AllergyCategory entity) {
        return AllergyCategoryDTO.builder()
            .id(entity.getId())
            .allergyType(entity.getAllergyType())
            .build();
    }

    public AllergyCategory toEntity(AllergyCategoryDTO dto, boolean idRequired) {
        return AllergyCategory.builder()
            .id(idRequired ? dto.id() : null)
            .allergyType(dto.allergyType())
            .build();
    }
}
