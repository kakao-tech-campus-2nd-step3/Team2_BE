package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.ProductFreeFrom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFreeFromRepository extends JpaRepository<ProductFreeFrom, Long> {

}
