package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.mapper.BaseResponseDTOMapper;
import jeje.work.aeatbe.service.ProductAllergyService;
import jeje.work.aeatbe.service.ProductFreeFromService;
import jeje.work.aeatbe.service.ReviewService;

public class ProductResponseMapper implements BaseResponseDTOMapper<ProductDTO, ProductResponseDTO> {
    private final ProductAllergyService productAllergyService;
    private final ProductFreeFromService productFreeFromService;
    private final ReviewService reviewService;

    public ProductResponseMapper(ProductAllergyService productAllergyService, ProductFreeFromService productFreeFromService, ReviewService reviewService) {
        this.productAllergyService = productAllergyService;
        this.productFreeFromService = productFreeFromService;
        this.reviewService = reviewService;
    }

    public ProductResponseDTO toEntity(ProductDTO dto, boolean idRequired) {
        return ProductResponseDTO.builder()
                .id(idRequired ? dto.id() : null)
                .name(dto.productName())
                .price(dto.price())
                .imgUrl(dto.productImageUrl())
                .rating(reviewService.getAverageRating(dto.id()))
                .ProductUrl(dto.seller())
                .description(dto.metaImageUrl())
                .freeFrom(productFreeFromService.getFreeFromTags(dto.id()).toArray(new String[0]))
                .allergy(productAllergyService.getAllergyTags(dto.id()).toArray(new String[0]))
                .build();
    }
}
