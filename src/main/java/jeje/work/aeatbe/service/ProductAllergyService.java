package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.exception.ProductAllergyNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductAllergyMapper;
import jeje.work.aeatbe.repository.ProductAllergyRepository;
import org.springframework.stereotype.Service;

/**
 * 상품 알레르기 서비스 레이어
 *
 * @see ProductAllergyService
 */
@Service
public class ProductAllergyService {
    private final ProductAllergyRepository ProductAllergyRepository;

    public ProductAllergyService(jeje.work.aeatbe.repository.ProductAllergyRepository productAllergyRepository, jeje.work.aeatbe.mapper.product.ProductAllergyMapper productAllergyMapper) {
        ProductAllergyRepository = productAllergyRepository;
    }

    public ProductAllergy findById(Long id) {
        return ProductAllergyRepository.findById(id).orElseThrow(
                () -> new ProductAllergyNotFoundException("해당 id의 상품 알레르기가 존재하지 않습니다." + id)
        );
    }
}
