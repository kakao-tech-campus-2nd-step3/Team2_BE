package jeje.work.aeatbe.repository;

import java.util.Optional;
import jeje.work.aeatbe.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    int countByArticleId(Long articleId);
    Optional<ArticleLike> findByUserIdAndArticleId(Long userId, Long articleId);
}
