package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.AllergyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergyCategoryRepository extends JpaRepository<AllergyCategory, Long> {
   Optional<AllergyCategory> findByAllergyType(String allergyType);
}