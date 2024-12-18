package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTagsContainingAndTitleContainingAndContentContaining(
            String tags, String title, String content, Pageable pageable
    );

    Page<Article> findByTagsContaining(String tags, Pageable pageable);

    Page<Article> findByTitleContaining(String title, Pageable pageable);

    Page<Article> findByContentContaining(String content, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    @Query("SELECT a FROM Article a " +
            "LEFT JOIN ArticleLike al ON a.id = al.article.id " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(al.id) DESC")
    Page<Article> findAllOrderByLikes(Pageable pageable);

}