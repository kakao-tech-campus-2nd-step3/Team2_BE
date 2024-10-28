package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.FreeFromCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeFromCategoryRepository extends JpaRepository<FreeFromCategory, Long> {

    Optional<FreeFromCategory> findByFreeFromType(String s);
}
