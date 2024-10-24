package jeje.work.aeatbe.mapper.freeFromCategory;

import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.service.FreeFromCategoryService;
import org.springframework.stereotype.Component;

@Component
public class FreeFromCategoryMapper {
    final private FreeFromCategoryService freeFromCategoryService;

    public FreeFromCategoryMapper(FreeFromCategoryService freeFromCategoryService) {
        this.freeFromCategoryService = freeFromCategoryService;
    }

    public FreeFromCategoryDTO toDTO(FreeFromCategory freeFromCategory) {
        return FreeFromCategoryDTO.builder()
                .id(freeFromCategory.getId())
                .freeFromType(freeFromCategory.getFreeFromType())
                .build();
    }

    public FreeFromCategory toEntity(FreeFromCategoryDTO freeFromCategoryDTO, boolean idRequired) {
        FreeFromCategory freeFromCategory = FreeFromCategory.builder()
                .id(idRequired ? freeFromCategoryDTO.id() : null)
                .freeFromType(freeFromCategoryDTO.freeFromType())
                .build();

        return freeFromCategory;
    }

    public FreeFromCategory toEntity(FreeFromCategoryDTO freeFromCategoryDTO){
        return toEntity(freeFromCategoryDTO, false);
    }
}
