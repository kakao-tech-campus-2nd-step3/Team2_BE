package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.entity.Product;
import org.springframework.stereotype.Component;

/**
 * 상품 매퍼
 */
@Component
public class ProductMapper {

    /**
     * Entity -> DTO
     * @param product
     * @return DTO
     */
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
//                .allergens(product.getAllergens())
                .nutritionalInfo(product.getNutritionalInfo())
                .productImageUrl(product.getProductImageUrl())
                .metaImageUrl(product.getMetaImageUrl())
                .typeName(product.getTypeName())
                .manufacturer(product.getManufacturer())
                .seller(product.getSeller())
                .capacity(product.getCapacity())
                .productName(product.getProductName())
                .ingredients(product.getIngredients())
                .price(product.getPrice())
                .build();
    }

    /**
     * DTO -> Entity
     * @param productDTO
     * @return Entity
     */
    public Product toEntity(ProductDTO productDTO, boolean idRequired) {
        Product product = Product.builder()
                .id(idRequired ? productDTO.id() : null)
//                .allergens(productDTO.allergens())
                .nutritionalInfo(productDTO.nutritionalInfo())
                .productImageUrl(productDTO.productImageUrl())
                .metaImageUrl(productDTO.metaImageUrl())
                .typeName(productDTO.typeName())
                .manufacturer(productDTO.manufacturer())
                .seller(productDTO.seller())
                .capacity(productDTO.capacity())
                .productName(productDTO.productName())
                .ingredients(productDTO.ingredients())
                .price(productDTO.price())
                .build();

        return product;
    }

    /**
     * DTO -> Entity
     * @param productDTO
     * @return Entity
     */
    public Product toEntity(ProductDTO productDTO) {
        return toEntity(productDTO, false);
    }
}
