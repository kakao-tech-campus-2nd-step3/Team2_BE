package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.exception.ProductNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.mapper.product.ProductResponseMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final ProductResponseMapper productResponseMapper;
    private final ProductAllergyService productAllergyService;
    private final ProductFreeFromService productFreeFromService;
    private final ReviewService reviewService;
    private final AllergyCategoryService allergyCategoryService;
    private final FreeFromCategoryService freeFromCategoryService;

    public ProductService(
            ProductRepository productRepository,
            ProductMapper productMapper,
            ProductResponseMapper productResponseMapper,
            ProductAllergyService productAllergyService,
            ProductFreeFromService productFreeFromService,
            ReviewService reviewService,
            AllergyCategoryService allergyCategoryService,
            FreeFromCategoryService freeFromCategoryService
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productResponseMapper = productResponseMapper;
        this.productAllergyService = productAllergyService;
        this.productFreeFromService = productFreeFromService;
        this.reviewService = reviewService;
        this.allergyCategoryService = allergyCategoryService;
        this.freeFromCategoryService = freeFromCategoryService;
    }

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
     * @param productDTO 상품 정보
     * @param allegies 알레르기 정보
     * @param freeFroms 프리프롬 정보
     * @return ProductResponseDTO 상품 상세 정보
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductDTO productDTO, List<String> allegies, List<String> freeFroms) {
        var product = productRepository.save(productMapper.toEntity(productDTO));

        var wantAllergiesEntity = allegies.stream()
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
     * @param id 상품 id
     * @param productDTO 상품 정보
     * @param allegies 알레르기 정보
     * @param freeFroms 프리프롬 정보
     * @return ProductResponseDTO 상품 상세 정보
     */
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductDTO productDTO, List<String> allegies, List<String> freeFroms) {
        var product = getProductEntity(id);

        var nowAllergiesEntity = product.getProductAllergies();
        var nowFreeFromsEntity = product.getProductFreeFroms();

        var wantAllergiesEntity = allegies.stream()
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
     * @param id 상품 id
     * @return ProductResponseDTO 상품 상세 정보
     */
    @Transactional
    public void deleteProduct(Long id) {
        var product = getProductEntity(id);
        reviewService.deleteReviewsByProductId(id);
        productRepository.delete(product);
    }
}
