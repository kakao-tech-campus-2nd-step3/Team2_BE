package jeje.work.aeatbe.repository;


import jeje.work.aeatbe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);

    List<Review> findByProductId(Long productId);
}
