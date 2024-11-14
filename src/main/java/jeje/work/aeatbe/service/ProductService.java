package jeje.work.aeatbe.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.exception.ProductNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.mapper.product.ProductResponseMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import jakarta.persistence.metamodel.Attribute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 상품 서비스 레이어
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @PersistenceContext
    private final EntityManager entityManager;

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
     * 페이지네이션과 정렬 기능을 이용하여 지정된 컬럼들을 선택하는 쿼리를 실행합니다.
     *
     * @param pageable    페이지네이션 및 정렬 정보
     * @param columnNames 선택할 컬럼 이름 리스트
     * @return 선택된 컬럼들을 포함하는 결과의 페이지 객체
     * @throws IllegalArgumentException pageable 또는 columnNames가 유효하지 않을 경우
     * @throws RuntimeException         쿼리 실행 중 오류가 발생한 경우
     */
    @Transactional(readOnly = true)
    protected Page<Object[]> getFilteredProducts(Pageable pageable, List<String> columnNames) {
        validateInput(pageable, columnNames);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = createSelectQuery(cb, columnNames, pageable);
        TypedQuery<Object[]> typedQuery = createTypedQuery(query, pageable);

        List<Object[]> resultList = typedQuery.getResultList();
        long total = executeCountQuery(columnNames);

        return new PageImpl<>(resultList, pageable, total);
    }

    /**
     * pageable과 columnNames가 null이 아닌지, 컬럼 이름이 유효한지 검증합니다.
     *
     * @param pageable    페이지네이션 및 정렬 정보
     * @param columnNames 유효성을 검사할 컬럼 이름 리스트
     * @throws IllegalArgumentException pageable이 null이거나 columnNames가 비어있거나 유효하지 않은 경우
     */
    private void validateInput(Pageable pageable, List<String> columnNames) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null");
        }
        if (columnNames == null || columnNames.isEmpty()) {
            throw new IllegalArgumentException("Column names must not be null or empty");
        }

        Set<String> validColumnNames = getValidColumnNames();
        for (String columnName : columnNames) {
            if (!validColumnNames.contains(columnName)) {
                throw new IllegalArgumentException("Invalid column name: " + columnName);
            }
        }
    }

    /**
     * CriteriaBuilder를 사용하여 지정된 컬럼을 선택하는 쿼리를 생성합니다.
     *
     * @param cb          CriteriaBuilder 인스턴스
     * @param columnNames 선택할 컬럼 이름 리스트
     * @param pageable    페이지네이션 및 정렬 정보
     * @return 생성된 CriteriaQuery 객체
     */
    private CriteriaQuery<Object[]> createSelectQuery(CriteriaBuilder cb, List<String> columnNames, Pageable pageable ) {
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Product> root = query.from(Product.class);

        query.multiselect(columnNames.stream()
                .map(root::get)
                .toArray(Selection[]::new));

        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort().stream()
                    .map(order -> createOrder(cb, root, order))
                    .toList();
            query.orderBy(orders);
        }

        return query;
    }

    /**
     * 정렬 기준에 따라 정렬 조건을 생성합니다.
     *
     * @param cb    CriteriaBuilder 인스턴스
     * @param root  쿼리의 루트 엔티티
     * @param order 정렬 조건
     * @return 정렬을 위한 Order 객체
     */
    private Order createOrder(CriteriaBuilder cb, Root<Product> root, Sort.Order order) {
        return order.isAscending()
                ? cb.asc(root.get(order.getProperty()))
                : cb.desc(root.get(order.getProperty()));
    }

    /**
     * 페이지네이션 조건을 설정하여 TypedQuery 객체를 생성합니다.
     *
     * @param query    CriteriaQuery 객체
     * @param pageable 페이지네이션 정보
     * @return 설정된 TypedQuery 객체
     */
    private TypedQuery<Object[]> createTypedQuery(CriteriaQuery<Object[]> query, Pageable pageable) {
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery;
    }

    /**
     * 총 레코드 수를 계산하기 위한 카운트 쿼리를 실행합니다.
     *
     * @param columnNames 컬럼 이름 리스트
     * @return 총 레코드 수
     */
    private long executeCountQuery(List<String> columnNames) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> root = countQuery.from(Product.class);

        countQuery.select(cb.count(root));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    /**
     * Product 엔티티의 유효한 컬럼 이름을 메타데이터로부터 가져옵니다.
     *
     * @return 유효한 컬럼 이름의 집합
     */
    private Set<String> getValidColumnNames() {
        return entityManager.getMetamodel()
                .entity(Product.class)
                .getAttributes()
                .stream()
                .map(Attribute::getName)
                .collect(Collectors.toSet());
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
    protected Product updateProductEntity(ProductDTO productDTO) {
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
                productFreeFromService.createProductFreeFrom(product, wantFreeFrom);
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
            return productRepository.findByAllergyNotIn(allergies, pageable);
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
