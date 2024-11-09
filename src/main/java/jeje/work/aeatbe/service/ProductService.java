package jeje.work.aeatbe.service;


import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.exception.ProductNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.mapper.product.ProductResponseMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품 서비스 레이어
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final ProductResponseMapper productResponseMapper;
    private final ProductAllergyService productAllergyService;
    private final ProductFreeFromService productFreeFromService;
    private final ReviewRatingService reviewRatingService;
    private final AllergyCategoryService allergyCategoryService;
    private final FreeFromCategoryService freeFromCategoryService;


    public ProductResponseDTO getProductResponseDTO(ProductDTO productDTO) {
        return productResponseMapper.toEntity(
                productDTO,
                reviewRatingService.getAverageRating(productDTO.id()),
                productFreeFromService.getFreeFromTags(productDTO.id()),
                productAllergyService.getAllergyTags(productDTO.id()),
                true
        );
    }


    /**
     * 모든 상품 조회
     *
     * @param q         검색어
     * @param allergies 알러지 카테고리
     * @param freeFroms 프리프롬 카테고리
     * @param priceMin  상품 최소 가격
     * @param priceMax  상품 최대 가격
     * @param sortBy    the sort by
     * @param pageable  the pageable
     * @return the all products
     */
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(String q, List<String> allergies,
        List<String> freeFroms, int priceMin, int priceMax, String sortBy, Pageable pageable) {
        Pageable sortedPageable = createSortedPageable(pageable, sortBy);

        Page<Product> productsPage = findProductsByCriteria(q, allergies, freeFroms, priceMin, priceMax, sortedPageable);

        List<ProductResponseDTO> productDTOs = mapToResponseDTO(productsPage.getContent());

        return new PageImpl<>(productDTOs, sortedPageable, productsPage.getTotalElements());
    }


    /**
     * id에 해당하는 상품 엔티티를 DB에서 조회합니다.
     *
     * @param id the id
     * @return Product entity
     */
    @Transactional(readOnly = true)
    protected Product getProductEntity(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("해당 id의 상품이 존재하지 않습니다.")
        );
    }

    /**
     * 상품 엔티티를 업데이트합니다.
     *
     * @param productDTO the product dto
     * @return Product entity
     */
    @Transactional
    protected Product updateProductEntity(ProductDTO productDTO){
        var product = getProductEntity(productDTO.id());
        productRepository.save(productMapper.toEntity(productDTO, true));
        return product;
    }

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id the id
     * @return ProductDTO product dto
     */
    public ProductDTO getProductDTO(Long id) {
        return productMapper.toDTO(getProductEntity(id));
    }

    /**
     * id에 해당하는 상품의 상세 정보를 반환합니다.
     *
     * @param id the id
     * @return ProductResponseDTO product response dto
     */
    public ProductResponseDTO getProductResponseDTO(Long id) {
        return getProductResponseDTO(getProductDTO(id));
    }

    /**
     * 상품을 생성합니다.
     *
     * @param productDTO 상품 정보
     * @param allergies  알러지 카테고리
     * @param freeFroms  프리프롬 카테고리
     * @return ProductResponseDTO 상품 상세 정보
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductDTO productDTO, List<String> allergies, List<String> freeFroms) {
        var product = productRepository.save(productMapper.toEntity(productDTO));

        var wantAllergiesEntity = allergies.stream()
            .map(allergyCategoryService::getProductAllergyByType)
            .toList();

        var wantFreeFromsEntity = freeFroms.stream()
            .map(freeFromCategoryService::getProductFreeFromByType)
            .toList();

        for (var wantAllergy : wantAllergiesEntity) {
            productAllergyService.createProductAllergy(product, wantAllergy);
        }

        for (var wantFreeFrom : wantFreeFromsEntity) {
            productFreeFromService.createProductFreeFrom(product, wantFreeFrom);
        }

        return getProductResponseDTO(productMapper.toDTO(product));
    }

    /**
     * 상품을 업데이트합니다.
     *
     * @param id         상품 id
     * @param productDTO 상품 정보
     * @param allergies  알러지 카테고리
     * @param freeFroms  프리프롬 카테고리
     * @return ProductResponseDTO 상품 상세 정보
     */
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductDTO productDTO, List<String> allergies, List<String> freeFroms) {
        var product = getProductEntity(id);

        var nowAllergiesEntity = product.getProductAllergies();
        var nowFreeFromsEntity = product.getProductFreeFroms();

        var wantAllergiesEntity = allergies.stream()
            .map(allergyCategoryService::getProductAllergyByType)
            .toList();

        var wantFreeFromsEntity = freeFroms.stream()
            .map(freeFromCategoryService::getProductFreeFromByType)
            .toList();

        for (var nowAllergy : nowAllergiesEntity) {
            if (!wantAllergiesEntity.contains(nowAllergy.getAllergy())) {
                productAllergyService.deleteProductAllergy(nowAllergy.getId());
            }
        }

        for (var wantAllergy : wantAllergiesEntity) {
            if (!nowAllergiesEntity.stream().anyMatch(entity -> entity.getAllergy().equals(wantAllergy))) {
                productAllergyService.createProductAllergy(product, wantAllergy);
            }
        }

        for (var nowFreeFrom : nowFreeFromsEntity) {
            if (!wantFreeFromsEntity.contains(nowFreeFrom.getFreeFromCategory())) {
                productFreeFromService.deleteProductFreeFrom(nowFreeFrom.getId());
            }
        }

        for (var wantFreeFrom : wantFreeFromsEntity) {
            if (!nowFreeFromsEntity.stream().anyMatch(entity -> entity.getFreeFromCategory().equals(wantFreeFrom))) {
                productFreeFromService.createProductFreeFrom(product,wantFreeFrom);
            }
        }

        var ret = updateProductEntity(productDTO);

        return getProductResponseDTO(productMapper.toDTO(ret));
    }


    /**
     * 상품을 삭제합니다.
     *
     * @param id 상품 id
     * @return ProductResponseDTO 상품 상세 정보
     */
    @Transactional
    public void deleteProduct(Long id) {
        var product = getProductEntity(id);
        productRepository.delete(product);
    }

    private Sort determineSortOrder(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "new":
                return Sort.by(Sort.Direction.DESC, "createdAt");
            case "price":
                return Sort.by(Sort.Direction.ASC, "price");
            case "sales":
                return Sort.by(Sort.Direction.DESC, "salesVolume");
            default:
                return Sort.by(Sort.Direction.DESC, "rating");
        }
    }

    private Pageable createSortedPageable(Pageable pageable, String sortBy) {
        Sort sort = determineSortOrder(sortBy);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    private Page<Product> findProductsByCriteria(String q, List<String> allergies,
        List<String> freeFroms, int priceMin, int priceMax, Pageable pageable) {

        if ((allergies == null || allergies.isEmpty()) && (freeFroms == null || freeFroms.isEmpty())) {
            Pageable sortedByProductName = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by("productName").ascending());
            return productRepository.findByPriceBetween(priceMin, priceMax, sortedByProductName);
        }

        if (q != null) {
            return productRepository.findByProductNameContaining(q, pageable);
        }
        if (allergies != null && !allergies.isEmpty()) {
            return productRepository.findByAllergy(allergies, pageable);
        }
        if (freeFroms != null && !freeFroms.isEmpty()) {
            return productRepository.findByFreeFrom(freeFroms, pageable);
        }
        return productRepository.findByPriceBetween(priceMin, priceMax, pageable);
    }

    private List<ProductResponseDTO> mapToResponseDTO(List<Product> products) {
        return products.stream()
            .map(product -> productResponseMapper.toEntity(
                productMapper.toDTO(product),
                reviewRatingService.getAverageRating(product.getId()),
                productFreeFromService.getFreeFromTags(product.getId()),
                productAllergyService.getAllergyTags(product.getId()),
                true
            ))
            .collect(Collectors.toList());
    }
}
