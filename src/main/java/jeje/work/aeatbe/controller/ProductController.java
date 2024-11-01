package jeje.work.aeatbe.controller;


import java.util.List;
import jeje.work.aeatbe.dto.product.ProductDTO;
import jeje.work.aeatbe.dto.product.ProductResponseDTO;
import jeje.work.aeatbe.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@RequestParam String q,
        @RequestParam(required = false) String allergy,
        @RequestParam(required = false) String freeFroms,
        @RequestParam(required = false, defaultValue = "0") int priceMin,
        @RequestParam(required = false, defaultValue = "1000000") int priceMax,
        @RequestParam(required = false, defaultValue = "new") String sortBy,
        @RequestParam(required = true, defaultValue = "1") String pageToken,
        @RequestParam(required = true) int maxResults) {

//        List<ProductResponseDTO> product = productService.getAllProducts(q, allergy, freeFroms, priceMin, priceMax, sortBy, pageToken, maxResults); // step2는 categoryId가 들어가던데...
//        return ResponseEntity.ok(product);

        Sort sort;
        switch (sortBy.toLowerCase()) {
            case "new":
                sort = Sort.by(Sort.Direction.DESC, "createdAt");
                break;
            case "price":
                sort = Sort.by(Sort.Direction.ASC, "price");
                break;
            case "sales":
                sort = Sort.by(Sort.Direction.DESC, "salesVolume");
                break;
            default:
                sort = Sort.by(Sort.Direction.DESC, "rating");
                break;
        }

        Pageable pageable = PageRequest.of(Integer.parseInt(pageToken), maxResults, sort);
        Page<ProductResponseDTO> products = productService.getAllProducts(q, allergy, freeFroms, priceMin, priceMax, pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductsById(@PathVariable Long id) {
       ProductResponseDTO product = productService.getProductResponseDTO(id);
       return ResponseEntity.ok(product);
    }


    // todo: 토큰 사용 로직
    @PostMapping
    public ResponseEntity<?> postProducts(
        @RequestParam ProductDTO productDTO,
        @RequestParam List<String> allegies,
        @RequestParam List<String> freeFroms
//        ,@LoginUser Long userId
    ){
        ProductResponseDTO product = productService.createProduct(productDTO, allegies, freeFroms);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // todo: 토큰 사용 로직
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProducts(@PathVariable Long id,
        @RequestParam ProductDTO productDTO,
        @RequestParam List<String> allegies,
        @RequestParam List<String> freeFroms
        //        ,@LoginUser Long userId
    ) {

        ProductResponseDTO product = productService.updateProduct(id, productDTO, allegies, freeFroms);
        return ResponseEntity.ok(product);
    }


    // todo : 토큰 사용 로직
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducts(@PathVariable Long id
        //        ,@LoginUser Long userId
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
