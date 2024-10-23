package jeje.work.aeatbe.repository;

import java.util.List;
import java.util.Optional;
import jeje.work.aeatbe.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
    Optional<Wishlist> findByIdAndUserId(Long id, Long userId);
}