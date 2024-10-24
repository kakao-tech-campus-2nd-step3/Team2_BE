package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductFreeFromDTO;
import jeje.work.aeatbe.entity.ProductFreeFrom;
import jeje.work.aeatbe.mapper.BaseEntityMapper;
import jeje.work.aeatbe.service.FreeFromCategoryService;
import jeje.work.aeatbe.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductFreeFromMapper implements BaseEntityMapper<ProductFreeFromDTO, ProductFreeFrom>{

    private final ProductService productService;
    private final FreeFromCategoryService freeFromCategoryService;

    public ProductFreeFromMapper(ProductService productService, FreeFromCategoryService freeFromCategoryService) {
        this.productService = productService;
        this.freeFromCategoryService = freeFromCategoryService;
    }

    public ProductFreeFromDTO toDTO(ProductFreeFrom entity) {
        return ProductFreeFromDTO.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .freeFromId(entity.getFreeFromCategory().getId())
                .build();
    }

    public ProductFreeFrom toEntity(ProductFreeFromDTO dto, boolean idRequired) {
        return ProductFreeFrom.builder()
                .id(idRequired ? dto.id() : null)
                .product(productService.findById(dto.productId()))
                .freeFromCategory(freeFromCategoryService.findById(dto.freeFromId()))
                .build();
    }

    public ProductFreeFrom toEntity(ProductFreeFromDTO dto) {
        return toEntity(dto, false);
    }
}
