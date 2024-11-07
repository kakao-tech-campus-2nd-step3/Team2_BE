package jeje.work.aeatbe.repository;

import java.util.List;
import jeje.work.aeatbe.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContaining(String q, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.productAllergies a WHERE a.allergy IN :allergies")
    Page<Product> findByAllergy(List<String> allergies, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.productFreeFroms f WHERE f.freeFromCategory IN :freeFroms")
    Page<Product> findByFreeFrom(List<String> freeFroms, Pageable pageable);

    Page<Product> findByPriceBetween(int priceMin, int priceMax, Pageable pageable);
}