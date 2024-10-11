package jeje.work.aeatbe;

import jeje.work.aeatbe.dto.*;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.repository.ArticleRepository;
import jeje.work.aeatbe.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;


public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    // 칼럼 리스트 반환 테스트
    @Test
    public void testGetArticles() {
        Article article1 = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news", "Title 1\nSubtitle 1\nImage URL 1", 10, "http://example.com/image1.jpg");
        Article article2 = new Article(2L, "Title 2", new Timestamp(System.currentTimeMillis()), "Author 2", "news", "Title 2\nSubtitle 2\nImage URL 2", 20, "http://example.com/image2.jpg");

        when(articleRepository.findAll()).thenReturn(List.of(article1, article2));

        ArticleListResponseDTO result = articleService.getArticles(null, null, null, null, null, 10);

        assertEquals(2, result.getColumns().size());
        assertEquals("Title 1", result.getColumns().get(0).getTitle());
        assertEquals("Subtitle 1", result.getColumns().get(0).getSubtitle());
        assertEquals("http://example.com/image1.jpg", result.getColumns().get(0).getImageurl());
    }

    // 특정 칼럼 반환 테스트
    @Test
    public void testGetArticleById() {
        Article article = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news",
            "Title 1\nSubtitle 1\nhttp://example.com/image1.jpg\nThis is the first paragraph.\nThis is the second paragraph.", 10, "http://example.com/image1.jpg");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        ArticleDetailDTO result = articleService.getArticleById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Title 1", result.getTitle());
        assertEquals("http://example.com/image1.jpg", result.getImgurl());

        assertEquals("h2", result.getContent().get(0).getTag());
        assertEquals("Title 1", result.getContent().get(0).getContent());

        assertEquals("h3", result.getContent().get(1).getTag());
        assertEquals("Subtitle 1", result.getContent().get(1).getContent());

        assertEquals("img", result.getContent().get(2).getTag());
        assertEquals("http://example.com/image1.jpg", result.getContent().get(2).getContent());

        assertEquals("p", result.getContent().get(3).getTag());
        assertEquals("This is the first paragraph.\nThis is the second paragraph.", result.getContent().get(3).getContent());
    }

    // 칼럼 업데이트 테스트
    @Test
    public void testUpdateArticle() {
        Article existingArticle = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news",
            "Title 1\nSubtitle 1\nhttp://example.com/image1.jpg", 10, "http://example.com/image1.jpg");

        ArticleDTO updateDTO = new ArticleDTO(1L, "Updated Title", new Timestamp(System.currentTimeMillis()), "Updated Author", "updated,sports",
            "Updated content", 20, "http://example.com/updatedImage.jpg");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

        ArticleDTO result = articleService.updateArticle(1L, updateDTO);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals("updated,sports", result.getTags());
        assertEquals("Updated content", result.getContent());
        assertEquals(20, result.getLikes());
        assertEquals("http://example.com/updatedImage.jpg", result.getThumbnailUrl());

        verify(articleRepository).save(any(Article.class));
    }

    // 칼럼 삭제 테스트
    @Test
    public void testDeleteArticle() {
        Article article = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news", "Title 1\nSubtitle 1\nImage URL 1", 10, "http://example.com/image1.jpg");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        articleService.deleteArticle(1L);

        verify(articleRepository, times(1)).delete(article);
    }
}