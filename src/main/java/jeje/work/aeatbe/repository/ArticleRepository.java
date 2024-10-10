package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}