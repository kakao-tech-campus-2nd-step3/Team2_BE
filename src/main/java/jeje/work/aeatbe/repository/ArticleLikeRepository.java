package jeje.work.aeatbe.repository;

import java.util.Optional;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    int countByArticleId(Long articleId);
    Optional<ArticleLike> findByUserUserIdAndArticleId(String userId, Long articleId);
}
