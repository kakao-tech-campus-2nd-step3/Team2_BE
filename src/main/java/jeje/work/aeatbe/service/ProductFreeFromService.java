package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.dto.product.ProductFreeFromDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductFreeFrom;
import jeje.work.aeatbe.exception.ProductFreeFromNotFoundException;
import jeje.work.aeatbe.mapper.freeFromCategory.FreeFromCategoryMapper;
import jeje.work.aeatbe.mapper.product.ProductFreeFromMapper;
import jeje.work.aeatbe.repository.ProductFreeFromRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품 알레르기 서비스 레이어
 * @see ProductAllergyService
 */
@Service
@RequiredArgsConstructor
public class ProductFreeFromService {
    private final ProductFreeFromRepository productFreeFromRepository;
    private final ProductFreeFromMapper productFreeFromMapper;
    private final FreeFromCategoryMapper freeFromCategoryMapper;

    /**
     * 상품 알레르기 정보를 조회하는 메서드
     *
     * @param id 조회할 상품 알레르기 정보의 ID
     * @return 조회된 상품 알레르기 정보
     * @throws ProductFreeFromNotFoundException 조회된 상품 알레르기 정보가 없을 경우 예외 발생
     */
    @Transactional(readOnly = true)
    protected ProductFreeFrom findById(Long id) {
        return productFreeFromRepository.findById(id).orElseThrow(
                () -> new ProductFreeFromNotFoundException("해당 id의 상품 알레르기 정보가 없습니다. id: " + id)
        );
    }

    /**
     * 상품 ID로 상품 알레르기 정보 리스트를 조회하는 메서드
     *
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 알레르기 정보 리스트
     */
    @Transactional(readOnly = true)
    public List<ProductFreeFrom> getFreeFromListByProductId(Long productId) {
        return productFreeFromRepository.findByProductId(productId);
    }

    /**
     * 상품 ID로 상품 알레르기 정보 DTO 리스트를 조회하는 메서드
     *
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 알레르기 정보 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<ProductFreeFromDTO> getFreeFromDTOList(Long productId) {
        return getFreeFromListByProductId(productId).stream()
                .map(productFreeFromMapper::toDTO)
                .toList();
    }

    /**
     * 상품 ID로 상품 알레르기 태그 리스트를 조회하는 메서드
     * @param productId
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> getFreeFromTags(Long productId) {
        return getFreeFromListByProductId(productId).stream()
                .map(ProductFreeFrom::getFreeFromCategory)
                .map(freeFromCategory -> freeFromCategory.getFreeFromType())
                .toList();
    }

    /**
     * 상품에 프리프롬 정보를 추가하는 메서드
     * @param product
     * @param freeFrom
     */
    @Transactional
    public void createProductFreeFrom(Product product, FreeFromCategoryDTO freeFrom) {
        var productFreeFrom = productFreeFromMapper.toEntity(product, freeFromCategoryMapper.toEntity(freeFrom));
        productFreeFromRepository.save(productFreeFrom);
    }

    /**
     * 상품 프리프롬 정보를 삭제하는 메서드
     * @param id
     */
    @Transactional
    public void deleteProductFreeFrom(Long id) {
        findById(id);
        productFreeFromRepository.deleteById(id);
    }
}
