package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductFreeFromDTO;
import jeje.work.aeatbe.entity.ProductFreeFrom;
import jeje.work.aeatbe.mapper.BaseEntityMapper;
import jeje.work.aeatbe.service.FreeFromCategoryService;
import org.springframework.stereotype.Component;

@Component
public class ProductFreeFromMapper implements BaseEntityMapper<ProductFreeFromDTO, ProductFreeFrom>{

    private final FreeFromCategoryService freeFromCategoryService;

    public ProductFreeFromMapper(FreeFromCategoryService freeFromCategoryService) {
        this.freeFromCategoryService = freeFromCategoryService;
    }

    public ProductFreeFromDTO toDTO(ProductFreeFrom entity) {
        return ProductFreeFromDTO.builder()
                .id(entity.getId())
                .freeFromId(entity.getFreeFromCategory().getId())
                .build();
    }

    public ProductFreeFrom toEntity(ProductFreeFromDTO dto, boolean idRequired) {
        return ProductFreeFrom.builder()
                .id(idRequired ? dto.id() : null)
                .freeFromCategory(freeFromCategoryService.findById(dto.freeFromId()))
                .build();
    }

    public ProductFreeFrom toEntity(ProductFreeFromDTO dto) {
        return toEntity(dto, false);
    }
}
