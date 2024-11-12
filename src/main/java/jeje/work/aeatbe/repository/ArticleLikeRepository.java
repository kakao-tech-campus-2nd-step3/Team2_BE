package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    int countByArticleId(Long articleId);

    Optional<ArticleLike> findByUserIdAndArticleId(Long userId, Long articleId);
}
