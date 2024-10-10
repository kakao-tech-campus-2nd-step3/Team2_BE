package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.FreeFromCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeFromCategoryRepository extends JpaRepository<FreeFromCategory, Integer> {

}
