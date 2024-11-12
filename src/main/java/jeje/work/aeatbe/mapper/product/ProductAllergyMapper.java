package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductAllergy;
import org.springframework.stereotype.Component;

/**
 * 상품 알레르기 매퍼
 */
@Component
public class ProductAllergyMapper {

    /**
     * Entity -> DTO
     *
     * @param productAllergy
     * @return DTO
     */
    public ProductAllergyDTO toDTO(ProductAllergy productAllergy) {
        return ProductAllergyDTO.builder()
                .id(productAllergy.getId())
                .productId(productAllergy.getProduct().getId())
                .allergyId(productAllergy.getAllergy().getId())
                .build();
    }

    /**
     * DTO -> Entity
     *
     * @param productAllergyDTO
     * @return Entity
     */
    public ProductAllergy toEntity(ProductAllergyDTO productAllergyDTO, Product product, AllergyCategory allergyCategory, boolean idRequired) {
        ProductAllergy productAllergy = ProductAllergy.builder()
                .id(idRequired ? productAllergyDTO.id() : null)
                .product(product)
                .allergy(allergyCategory)
                .build();

        return productAllergy;
    }

    /**
     * DTO -> Entity
     *
     * @param productAllergyDTO
     * @return Entity
     */
    public ProductAllergy toEntity(ProductAllergyDTO productAllergyDTO, Product product, AllergyCategory allergyCategory) {
        return toEntity(productAllergyDTO, product, allergyCategory, false);
    }

    /**
     * DTO -> Entity
     *
     * @param product
     * @return Entity
     */
    public ProductAllergy toEntity(Product product, AllergyCategory allergyCategory) {
        return toEntity(null, product, allergyCategory, false);
    }
}
