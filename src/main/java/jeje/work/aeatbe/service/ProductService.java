package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
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
    private final ReviewService reviewService;
    private final AllergyCategoryService allergyCategoryService;
    private final FreeFromCategoryService freeFromCategoryService;

    public ProductResponseDTO getProductResponseDTO(ProductDTO productDTO) {
        return productResponseMapper.toEntity(
                productDTO,
                reviewService.getAverageRating(productDTO.id()),
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
     * @param pageable  the pageable
     * @return the all products
     */
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(String q, String allergies, String freeFroms, int priceMin, int priceMax, String sortBy, Pageable pageable) {
        Sort sort = determineSortOrder(sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Product> productPage = getAllProductEntities(sortedPageable);

        List<Product> filteredProducts = productPage.getContent().stream()
            .filter(product -> (q == null || product.getProductName().contains(q)) &&
                (allergies == null || product.getProductAllergies().stream()
                    .anyMatch(allergyEntity -> allergies.contains(
                        (CharSequence) allergyEntity.getAllergy()))) &&
                (freeFroms == null || product.getProductFreeFroms().stream()
                    .anyMatch(freeFromEntity -> freeFroms.contains(
                        (CharSequence) freeFromEntity.getFreeFromCategory()))) &&
                (product.getPrice() >= priceMin && product.getPrice() <= priceMax))
            .collect(Collectors.toList());

        List<ProductResponseDTO> productDTOs = filteredProducts.stream()
            .map(product -> productResponseMapper.toEntity(
                productMapper.toDTO(product),
                reviewService.getAverageRating(product.getId()),
                productFreeFromService.getFreeFromTags(product.getId()),
                productAllergyService.getAllergyTags(product.getId()),
                true
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(productDTOs, sortedPageable, productPage.getTotalElements());
    }

    /**
     * 모든 상품에 대한 엔티티 조회
     *
     * @param pageable the pageable
     * @return the list
     */
    @Transactional(readOnly = true)
    protected Page<Product> getAllProductEntities(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    /**
     * id에 해당하는 상품 엔티티를 DB에서 조회합니다.
     * @param id
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
     * @param productDTO
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
     * @param id
     * @return ProductDTO
     */
    public ProductDTO getProductDTO(Long id) {
        return productMapper.toDTO(getProductEntity(id));
    }

    /**
     * id에 해당하는 상품의 상세 정보를 반환합니다.
     * @param id
     * @return ProductResponseDTO
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
    public ProductResponseDTO createProduct(ProductDTO productDTO, String allergies, String freeFroms) {
        var product = productRepository.save(productMapper.toEntity(productDTO));

        List<String> allergyList = Arrays.asList(allergies.split(","));
        List<String> freeFromList = Arrays.asList(freeFroms.split(","));

        for (String allergy : allergyList) {
            var wantAllergy = allergyCategoryService.getProductAllergyByType(allergy.trim());
            productAllergyService.createProductAllergy(product, wantAllergy);
        }

        for (String freeFrom : freeFromList) {
            var wantFreeFrom = freeFromCategoryService.getProductFreeFromByType(freeFrom.trim());
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
    public ProductResponseDTO updateProduct(Long id, ProductDTO productDTO, String allergies, String freeFroms) {
        var product = getProductEntity(id);

        // 현재 알러지 및 프리프롬 항목을 가져와서 null일 경우 빈 리스트로 초기화
        var nowAllergiesEntity = Optional.ofNullable(product.getProductAllergies()).orElse(new ArrayList<>());
        var nowFreeFromsEntity = Optional.ofNullable(product.getProductFreeFroms()).orElse(new ArrayList<>());

        List<AllergyCategoryDTO> wantAllergiesEntity = Arrays.stream(allergies.split(","))
            .map(String::trim)
            .map(allergyCategoryService::getProductAllergyByType)
            .toList();

        List<FreeFromCategoryDTO> wantFreeFromsEntity = Arrays.stream(freeFroms.split(","))
            .map(String::trim)
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
                productFreeFromService.createProductFreeFrom(product, wantFreeFrom);
            }
        }

        var ret = updateProductEntity(productDTO);

        return getProductResponseDTO(productMapper.toDTO(ret));
    }


    /**
     * 상품을 삭제합니다.
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
}
