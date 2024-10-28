package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.AllergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.ProductAllergy;
import jeje.work.aeatbe.exception.ProductAllergyNotFoundException;
import jeje.work.aeatbe.mapper.allergyCategory.AllergyCategoryMapper;
import jeje.work.aeatbe.mapper.product.ProductAllergyMapper;
import jeje.work.aeatbe.repository.ProductAllergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품 알레르기 서비스 레이어
 *
 * @see ProductAllergyService
 */
@Service
@RequiredArgsConstructor
public class ProductAllergyService {
    private final ProductAllergyRepository productAllergyRepository;
    private final ProductAllergyMapper productAllergyMapper;
    private final AllergyCategoryMapper allergyCategoryMapper;

    /**
     * 상품 알레르기 정보를 조회하는 메서드
     *
     * @param id 조회할 상품 알레르기 정보의 ID
     * @return 조회된 상품 알레르기 정보
     * @throws ProductAllergyNotFoundException 조회된 상품 알레르기 정보가 없을 경우 예외 발생
     */
    protected ProductAllergy findById(Long id) {
        return productAllergyRepository.findById(id).orElseThrow(
                () -> new ProductAllergyNotFoundException("해당 id의 상품 알레르기가 존재하지 않습니다." + id)
        );
    }

    /**
     * 상품 ID로 상품 알레르기 정보 리스트를 조회하는 메서드
     *
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 알레르기 정보 리스트
     */
    public List<ProductAllergy> getAllergyListByProductId(Long productId) {
        return productAllergyRepository.findByProductId(productId);
    }

    /**
     * 상품 ID로 상품 알레르기 정보 DTO 리스트를 조회하는 메서드
     *
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 알레르기 정보 DTO 리스트
     */
    public List<ProductAllergyDTO> getAllergyDTOList(Long productId) {
        return getAllergyListByProductId(productId).stream()
                .map(productAllergyMapper::toDTO)
                .toList();
    }

    /**
     * 상품 ID로 상품 알레르기 태그 리스트를 조회하는 메서드
     *
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 알레르기 태그 리스트
     */
    public List<String> getAllergyTags(Long productId) {
        return getAllergyListByProductId(productId).stream()
                .map(ProductAllergy::getAllergy)
                .map(AllergyCategory::getAllergyType)
                .toList();
    }

    /**
     * 상품 알레르기 정보를 생성하는 메서드
     *
     * @param product
     * @param allergyCategory
     */
    public void createProductAllergy(Product product, AllergyCategoryDTO allergyCategory) {
        productAllergyRepository.save(productAllergyMapper.toEntity(product, allergyCategoryMapper.toEntity(allergyCategory)));
    }

    /**
     * 상품 알레르기 정보를 삭제하는 메서드
     *
     * @param id 삭제할 상품 알레르기 정보의 ID
     */
    public void deleteProductAllergy(Long id) {
        findById(id);
        productAllergyRepository.deleteById(id);
    }
}