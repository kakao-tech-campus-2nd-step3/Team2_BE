package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTagsContainingAndTitleContainingAndContentContaining(
        String tags, String title, String content, Pageable pageable
    );
}