package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.ProductAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAllergyRepository extends JpaRepository<ProductAllergy, Long> {

    List<ProductAllergy> findByProductId(Long productId);
}
