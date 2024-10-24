package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.exception.ProductNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductEntityMapper;
import jeje.work.aeatbe.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAllergyService productAllergyService;
    private final ProductFreeFromService productFreeFromService;

    private final ProductEntityMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductAllergyService productAllergyService, ProductFreeFromService productFreeFromService, ProductEntityMapper productMapper) {
        this.productRepository = productRepository;
        this.productAllergyService = productAllergyService;
        this.productFreeFromService = productFreeFromService;
        this.productMapper = productMapper;
    }

    public Product findById(Long aLong) {
        return productRepository.findById(aLong).orElseThrow(
                () -> new ProductNotFoundException("해당 id의 상품이 존재하지 않습니다.")
        );
    }
}
