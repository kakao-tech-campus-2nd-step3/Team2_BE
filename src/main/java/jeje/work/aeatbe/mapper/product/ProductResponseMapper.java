package jeje.work.aeatbe.mapper.product;

import jeje.work.aeatbe.dto.product.PageInfoDTO;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResponseMapper{

    /**
     * ProductDTO를 ProductResponseDTO로 변환
     * @param dto ProductDTO
     * @param avergeRating 평균 평점
     * @param freeFromList 프리프롬 리스트
     * @param allergyList 알러지 리스트
     * @param idRequired id 필요 여부
     * @return ProductResponseDTO
     */
    public ProductResponseDTO toEntity(ProductDTO dto, Double avergeRating, List<String> freeFromList, List<String> allergyList, PageInfoDTO pageInfoDTO, boolean idRequired) {
        return ProductResponseDTO.builder()
                .id(idRequired ? dto.id() : null)
                .name(dto.productName())
                .price(dto.price())
                .imgUrl(dto.productImageUrl())
                .rating(avergeRating)
                .ProductUrl(dto.seller())
                .description(dto.metaImageUrl())
                .freeFrom(freeFromList.toArray(new String[0]))
                .allergy(allergyList.toArray(new String[0]))
                .pageInfo(pageInfoDTO)
                .build();
    }
}
