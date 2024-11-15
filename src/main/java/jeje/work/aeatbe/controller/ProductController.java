package jeje.work.aeatbe.controller;


import jakarta.validation.Valid;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 컨트롤러
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * 전체 상품 조회
     *
     * @param q          검색어
     * @param allergy    알러지 카테고리
     * @param freeFroms  프리프롬 카테고리
     * @param priceMin   상품 최소 가격
     * @param priceMax   상품 최대 가격
     * @param sortBy     정렬 기준
     * @param pageToken  페이지 번호
     * @param maxResults 페이지 내 상품 개수
     * @return 전체 상품 목록
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@RequestParam(required = false) String q,
                                                                   @RequestParam(required = false) List<String> allergy,
                                                                   @RequestParam(required = false) List<String> freeFroms,
                                                                   @RequestParam(required = false, defaultValue = "0") int priceMin,
                                                                   @RequestParam(required = false, defaultValue = "1000000") int priceMax,
                                                                   @RequestParam(required = false, defaultValue = "new") String sortBy,
                                                                   @RequestParam(required = true, defaultValue = "1") String pageToken,
                                                                   @RequestParam(required = true) int maxResults) {

        Pageable pageable = PageRequest.of(Integer.parseInt(pageToken), maxResults);
        Page<ProductResponseDTO> products = productService.getAllProducts(q, allergy, freeFroms, priceMin, priceMax, sortBy, pageable);
        return ResponseEntity.ok(products);
    }

    /**
     * 상품 상세 조회
     *
     * @param id 상품 id
     * @return 상세 상품 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductsById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductResponseDTO(id);
        return ResponseEntity.ok(product);
    }


    /**
     * 새 상품 추가
     *
     * @param productDTO 상품 DTO
     * @param allergies  알러지 카테고리
     * @param freeFroms  프리프롬 카테고리
     * @return 201 응답 코드 반환
     * @ todo:  토큰 사용 로직
     */
    @PostMapping
    public ResponseEntity<?> postProducts(
            @RequestBody @Valid ProductDTO productDTO,
            @RequestParam List<String> allergies,
            @RequestParam List<String> freeFroms
//        ,@LoginUser Long userId
    ) {
        ProductResponseDTO product = productService.createProduct(productDTO, allergies, freeFroms);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * 상품 정보 수정
     *
     * @param id         상품 id
     * @param productDTO 상품 DTO
     * @param allergies  알러지 카테고리
     * @param freeFroms  프리프롬 카테고리
     * @return 200 응답 코드
     * @ todo:  토큰 사용 로직
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProducts(@PathVariable Long id,
                                                             @RequestBody @Valid ProductDTO productDTO,
                                                             @RequestParam List<String> allergies,
                                                             @RequestParam List<String> freeFroms
                                                             //        ,@LoginUser Long userId
    ) {

        ProductResponseDTO product = productService.updateProduct(id, productDTO, allergies, freeFroms);
        return ResponseEntity.ok(product);
    }


    /**
     * 상품 삭제
     *
     * @param id 상품 id
     * @return 204 응답 코드
     * @ todo:  토큰 사용 로직
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducts(@PathVariable Long id
                                            //        ,@LoginUser Long userId
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/initialize-allergies")
    public ResponseEntity<String> initializeAllergiesMapping() {
        productService.initializeProductAllergiesMapping();
        return ResponseEntity.ok("Product-Allergy 연관 관계가 초기화되었습니다.");
    }

    @PostMapping("/initialize-freeFrom")
    public ResponseEntity<String> initializeFreeFromMapping() {
        productService.initializeProductFreeFromMapping();
        return ResponseEntity.ok("Product-Allergy 연관 관계가 초기화되었습니다.");
    }
}
