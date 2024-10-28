package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductFreeFromDTO;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductFreeFrom;
import org.springframework.stereotype.Component;

/**
 * 상품 알레르기 매퍼
 */
@Component
public class ProductFreeFromMapper{

    /**
     * Entity -> DTO
     * @param entity
     * @return DTO
     */
    public ProductFreeFromDTO toDTO(ProductFreeFrom entity) {
        return ProductFreeFromDTO.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .freeFromId(entity.getFreeFromCategory().getId())
                .build();
    }

    /**
     * DTO -> Entity
     * @param dto
     * @return Entity
     */
    public ProductFreeFrom toEntity(ProductFreeFromDTO dto, Product product, FreeFromCategory freeFromCategory, boolean idRequired) {
        return ProductFreeFrom.builder()
                .id(idRequired ? dto.id() : null)
                .product(product)
                .freeFromCategory(freeFromCategory)
                .build();
    }

    /**
     * DTO -> Entity
     * @param dto
     * @return Entity
     */
    public ProductFreeFrom toEntity(ProductFreeFromDTO dto, Product product, FreeFromCategory freeFromCategory) {
        return toEntity(dto, product, freeFromCategory, false);
    }

    /**
     * DTO -> Entity
     * @param product
     * @return Entity
     */
    public ProductFreeFrom toEntity(Product product, FreeFromCategory freeFromCategory) {
        return toEntity(null, product, freeFromCategory, false);
    }
}
