package jeje.work.aeatbe.repository;


import java.util.List;
import jeje.work.aeatbe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByProductId(Long productId);
}
