package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.product.ProductFreeFromDTO;
import jeje.work.aeatbe.entity.ProductFreeFrom;
import jeje.work.aeatbe.exception.ProductFreeFromNotFoundException;
import jeje.work.aeatbe.mapper.BaseEntityMapper;
import jeje.work.aeatbe.mapper.product.ProductFreeFromMapper;
import jeje.work.aeatbe.repository.ProductFreeFromRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품 알레르기 서비스 레이어
 * @see ProductAllergyService
 */
@Service
public class ProductFreeFromService {
    private final ProductFreeFromRepository productFreeFromRepository;
    private final ProductFreeFromMapper productFreeFromMapper;

    public ProductFreeFromService(ProductFreeFromRepository productFreeFromRepository, ProductFreeFromMapper productFreeFromMapper) {
        this.productFreeFromRepository = productFreeFromRepository;
        this.productFreeFromMapper = productFreeFromMapper;
    }

    public ProductFreeFrom getById(Long id) {
        return productFreeFromRepository.findById(id).orElseThrow(
                () -> new ProductFreeFromNotFoundException("해당 id의 상품 알레르기 정보가 없습니다. id: " + id)
        );
    }

    public List<ProductFreeFrom> getFreeFromListByProductId(Long productId) {
        return productFreeFromRepository.findByProductId(productId);
    }

    public List<ProductFreeFromDTO> getFreeFromDTOList(Long productId) {
        return getFreeFromListByProductId(productId).stream()
                .map(productFreeFromMapper::toDTO)
                .toList();
    }

    public List<String> getFreeFromTags(Long productId) {
        return getFreeFromListByProductId(productId).stream()
                .map(ProductFreeFrom::getFreeFromCategory)
                .map(freeFromCategory -> freeFromCategory.getFreeFromType())
                .toList();
    }
}
