package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.exception.ProductAllergyNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductAllergyMapper;
import jeje.work.aeatbe.repository.ProductAllergyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품 알레르기 서비스 레이어
 *
 * @see ProductAllergyService
 */
@Service
public class ProductAllergyService {
    private final ProductAllergyRepository ProductAllergyRepository;
    private final ProductAllergyMapper ProductAllergyMapper;

    public ProductAllergyService(jeje.work.aeatbe.repository.ProductAllergyRepository productAllergyRepository, jeje.work.aeatbe.mapper.product.ProductAllergyMapper productAllergyMapper, jeje.work.aeatbe.mapper.product.ProductAllergyMapper productAllergyMapper1) {
        ProductAllergyRepository = productAllergyRepository;
        ProductAllergyMapper = productAllergyMapper1;
    }

    public ProductAllergy findById(Long id) {
        return ProductAllergyRepository.findById(id).orElseThrow(
                () -> new ProductAllergyNotFoundException("해당 id의 상품 알레르기가 존재하지 않습니다." + id)
        );
    }

    public List<ProductAllergy> getAllergyListByProductId(Long productId) {
        return ProductAllergyRepository.findByProductId(productId);
    }

    public List<ProductAllergyDTO> getAllergyDTOList(Long productId) {
        return getAllergyListByProductId(productId).stream()
                .map(ProductAllergyMapper::toDTO)
                .toList();
    }

    public List<String> getAllergyTags(Long productId) {
        return getAllergyListByProductId(productId).stream()
                .map(ProductAllergy::getAllergy)
                .map(allergyCategory -> allergyCategory.getAllergyType())
                .toList();
    }
}
