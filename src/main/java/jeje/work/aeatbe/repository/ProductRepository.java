package jeje.work.aeatbe.repository;


import java.util.Map;
import jeje.work.aeatbe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new map(p.id as id, p.productName as product_name) FROM Product p")
    List<Map<String, Object>> findByProductIdAndName();

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax")
    Page<Product> findByPriceBetween(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.productName LIKE %:q%")
    Page<Product> findByProductName(@Param("q") String q, @Param("priceMin") int priceMin,
                                    @Param("priceMax") int priceMax, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
            "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
    Page<Product> findByAllergy(@Param("allergies") List<String> allergies, @Param("priceMin") int priceMin,
                                @Param("priceMax") int priceMax, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN p.productFreeFroms pf " +
            "JOIN pf.freeFromCategory ff " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND ff.freeFromType IN :freeFroms")
    Page<Product> findByFreeFrom(@Param("freeFroms") List<String> freeFroms,
                                 @Param("priceMin") int priceMin,
                                 @Param("priceMax") int priceMax,
                                 Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.productName LIKE %:q% " +
            "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
            "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
    Page<Product> findByProductNameAndAllergy(@Param("q") String q, @Param("allergies") List<String> allergies,
                                              @Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN p.productFreeFroms pf " +
            "JOIN pf.freeFromCategory ff " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.productName LIKE %:q% " +
            "AND ff.freeFromType IN :freeFroms")
    Page<Product> findByProductNameAndFreeFrom(@Param("q") String q,
                                               @Param("freeFroms") List<String> freeFroms,
                                               @Param("priceMin") int priceMin,
                                               @Param("priceMax") int priceMax,
                                               Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN p.productFreeFroms pf " +
            "JOIN pf.freeFromCategory ff " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
            "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies) " +
            "AND ff.freeFromType IN :freeFroms")
    Page<Product> findByAllergyAndFreeFrom(
            @Param("allergies") List<String> allergies,
            @Param("freeFroms") List<String> freeFroms,
            @Param("priceMin") int priceMin,
            @Param("priceMax") int priceMax,
            Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN p.productFreeFroms pf " +
            "JOIN pf.freeFromCategory ff " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.productName LIKE %:q% " +
            "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
            "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies) " +
            "AND ff.freeFromType IN :freeFroms")
    Page<Product> findByAll(
            @Param("q") String q,
            @Param("allergies") List<String> allergies,
            @Param("freeFroms") List<String> freeFroms,
            @Param("priceMin") int priceMin,
            @Param("priceMax") int priceMax,
            Pageable pageable);


}