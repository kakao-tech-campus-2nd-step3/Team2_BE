package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.mapper.BaseEntityMapper;
import jeje.work.aeatbe.service.AllergyCategoryService;
import jeje.work.aeatbe.service.ProductAllergyService;
import jeje.work.aeatbe.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductAllergyMapper implements BaseEntityMapper<ProductAllergyDTO, ProductAllergy> {

    private final ProductService productService;
    private final AllergyCategoryService allergyCategoryService;

    public ProductAllergyMapper(ProductService productService, ProductAllergyService productAllergyService, AllergyCategoryService allergyCategoryService) {
        this.productService = productService;
        this.allergyCategoryService = allergyCategoryService;
    }

    public ProductAllergyDTO toDTO(ProductAllergy productAllergy) {
        return ProductAllergyDTO.builder()
                .id(productAllergy.getId())
                .productId(productAllergy.getProduct().getId())
                .allergyId(productAllergy.getAllergy().getId())
                .build();
    }

    public ProductAllergy toEntity(ProductAllergyDTO productAllergyDTO, boolean idRequired) {
        ProductAllergy productAllergy = ProductAllergy.builder()
                .id(idRequired ? productAllergyDTO.id() : null)
                .product(productService.findById(productAllergyDTO.productId()))
                .allergy(allergyCategoryService.findById(productAllergyDTO.allergyId()))
                .build();

        return productAllergy;
    }

    public ProductAllergy toEntity(ProductAllergyDTO productAllergyDTO) {
        return toEntity(productAllergyDTO, false);
    }
}
