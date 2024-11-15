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
//    Page<Product> findByProductNameContaining(String q, Pageable pageable);
//
//    @Query("SELECT p FROM Product p WHERE NOT EXISTS (SELECT a FROM p.productAllergies a WHERE a.allergy IN :allergies)")
//    Page<Product> findByAllergyNotIn(List<String> allergies, Pageable pageable);
//
//    @Query("SELECT p FROM Product p JOIN p.productFreeFroms f WHERE f.freeFromCategory IN :freeFroms")
//    Page<Product> findByFreeFrom(List<String> freeFroms, Pageable pageable);
//
//    Page<Product> findByPriceBetween(int priceMin, int priceMax, Pageable pageable);
//
    @Query("SELECT new map(p.id as id, p.productName as product_name) FROM Product p")
    List<Map<String, Object>> findByProductIdAndName();
//
//
//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax")
//    Page<Product> findByPriceBetween(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);
//
//
//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax AND p.productName LIKE %:q%")
//    Page<Product> findByProductName(@Param("q") String q, @Param("priceMin") int priceMin,
//            @Param("priceMax") int priceMax, Pageable pageable);


//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax " +
//            "AND NOT EXISTS (SELECT a FROM p.productAllergies a WHERE a.allergy IN :allergies)")
//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax " +
//        "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax " +
//            "AND p.id NOT IN (SELECT pa.product.id FROM ProductAllergy pa WHERE pa.allergy.allergyType IN :allergies)")
//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
//        "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
//    Page<Product> findByAllergy(@Param("allergies") List<String> allergies, @Param("priceMin") int priceMin,
//            @Param("priceMax") int priceMax, Pageable pageable);
//
//
//    @Query("SELECT p FROM Product p JOIN ProductFreeFrom pf ON p = pf.product " +
//            "JOIN FreeFromCategory ff ON pf.freeFromCategory = ff WHERE p.price BETWEEN :priceMin AND :priceMax AND ff.freeFromType IN :freeFroms")
//    Page<Product> findByFreeFrom(@Param("freeFroms") List<String> freeFroms, @Param("priceMin") int priceMin,
//            @Param("priceMax") int priceMax, Pageable pageable);

//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax AND p.productName LIKE %:q% " +
//            "AND NOT EXISTS (SELECT a FROM p.productAllergies a WHERE a.allergy IN :allergies)")
//    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax AND p.productName LIKE %:q% " +
//        "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
//    Page<Product> findByProductNameAndAllergy(@Param("q") String q, @Param("allergies") List<String> allergies,
//            @Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);


//    @Query("SELECT p FROM Product p JOIN p.productFreeFroms f WHERE p.price BETWEEN :priceMin AND :priceMax " +
//            "AND p.productName LIKE %:q% AND f.freeFromCategory IN :freeFroms")
//    @Query("SELECT DISTINCT p FROM Product p JOIN ProductFreeFrom pf ON p = pf.product JOIN FreeFromCategory ff ON pf.freeFromCategory = ff " +
//        "WHERE p.price BETWEEN :priceMin AND :priceMax AND ff.freeFromType IN :freeFroms")
//    @Query("SELECT DISTINCT p FROM Product p JOIN ProductFreeFrom pf ON p = pf.product " +
//        "JOIN FreeFromCategory ff ON pf.freeFromCategory = ff WHERE p.price BETWEEN :priceMin AND :priceMax " +
//        "AND p.productName LIKE %:q% AND ff.freeFromType IN :freeFroms")
//    Page<Product> findByProductNameAndFreeFrom(@Param("q") String q, @Param("freeFroms") List<String> freeFroms,
//            @Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);

//    @Query("SELECT p FROM Product p JOIN p.productFreeFroms f WHERE p.price BETWEEN :priceMin AND :priceMax " +
//            "AND NOT EXISTS (SELECT a FROM p.productAllergies a WHERE a.allergy IN :allergies) AND f.freeFromCategory IN :freeFroms")
//    @Query("SELECT DISTINCT p FROM Product p JOIN ProductFreeFrom pf ON p = pf.product JOIN FreeFromCategory ff ON pf.freeFromCategory = ff " +
//        "WHERE p.price BETWEEN :priceMin AND :priceMax AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
//        "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies) AND ff.freeFromType IN :freeFroms")
//    Page<Product> findByAllergyAndFreeFrom(@Param("allergies") List<String> allergies, @Param("freeFroms") List<String> freeFroms,
//            @Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);



//    @Query("SELECT p FROM Product p JOIN p.productFreeFroms f WHERE p.price BETWEEN :priceMin AND :priceMax AND p.productName LIKE %:q% " +
//            "AND NOT EXISTS (SELECT a FROM p.productAllergies a WHERE a.allergy IN :allergies) AND f.freeFromCategory IN :freeFroms")
//    @Query("SELECT p FROM Product p JOIN ProductFreeFrom pf ON p = pf.product JOIN FreeFromCategory ff ON pf.freeFromCategory = ff " +
//        "WHERE p.price BETWEEN :priceMin AND :priceMax AND p.productName LIKE %:q% " +
//        "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa WHERE pa.product = p AND pa.allergy.allergyType IN :allergies) " +
//        "AND ff.freeFromType IN :freeFroms")
//    Page<Product> findByAll(
//            @Param("q") String q, @Param("allergies") List<String> allergies, @Param("freeFroms") List<String> freeFroms,
//            @Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);

    // 기본 가격 범위 검색
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax")
    Page<Product> findByPriceBetween(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);

    // 상품명 검색
    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.productName LIKE %:q%")
    Page<Product> findByProductName(@Param("q") String q, @Param("priceMin") int priceMin,
                                    @Param("priceMax") int priceMax, Pageable pageable);

    // 알레르기 제외 검색
    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
            "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
    Page<Product> findByAllergy(@Param("allergies") List<String> allergies, @Param("priceMin") int priceMin,
                                @Param("priceMax") int priceMax, Pageable pageable);

    // FreeFrom 포함 검색 (OR 조건)
    @Query("SELECT DISTINCT p FROM Product p " +
            "JOIN p.productFreeFroms pf " +
            "JOIN pf.freeFromCategory ff " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND ff.freeFromType IN :freeFroms")
    Page<Product> findByFreeFrom(@Param("freeFroms") List<String> freeFroms,
                                 @Param("priceMin") int priceMin,
                                 @Param("priceMax") int priceMax,
                                 Pageable pageable);

    // 상품명 + 알레르기 제외 검색
    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.price BETWEEN :priceMin AND :priceMax " +
            "AND p.productName LIKE %:q% " +
            "AND NOT EXISTS (SELECT pa FROM ProductAllergy pa " +
            "WHERE pa.product = p AND pa.allergy.allergyType IN :allergies)")
    Page<Product> findByProductNameAndAllergy(@Param("q") String q, @Param("allergies") List<String> allergies,
                                              @Param("priceMin") int priceMin, @Param("priceMax") int priceMax, Pageable pageable);

    // 상품명 + FreeFrom 검색 (OR 조건)
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

    // 알레르기 제외 + FreeFrom 검색 (OR 조건)
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

    // 전체 조건 검색 (FreeFrom은 OR 조건)
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