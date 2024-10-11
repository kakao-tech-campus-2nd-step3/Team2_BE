package jeje.work.aeatbe;

import jeje.work.aeatbe.column_dto.ArticleListResponseDTO;
import jeje.work.aeatbe.column_dto.ArticleResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    public void testGetArticles() {
        Article article1 = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news", "Title 1\nSubtitle 1\nImage URL 1",  "http://example.com/image1.jpg", 10);
        Article article2 = new Article(2L, "Title 2", new Timestamp(System.currentTimeMillis()), "Author 2", "news", "Title 2\nSubtitle 2\nImage URL 2", "http://example.com/image2.jpg", 10);
        Article article3 = new Article(3L, "Title 3", new Timestamp(System.currentTimeMillis()), "Author 3", "sports", "Title 3\nSubtitle 3\nImage URL 3", "http://example.com/image3.jpg", 10);

        Page<Article> page1 = new PageImpl<>(List.of(article1, article2));
        Page<Article> page2 = new PageImpl<>(List.of(article3));

        when(articleRepository.findByFilters(anyString(), anyString(), anyString(), any(Pageable.class)))
            .thenReturn(page1)
            .thenReturn(page2);

        ArticleListResponseDTO result1 = articleService.getArticles("sports", null, null, "popular", "0", 2);
        assertEquals(2, result1.getColumns().size());
        assertEquals("Title 1", result1.getColumns().get(0).getTitle());
        assertEquals("Subtitle 1", result1.getColumns().get(0).getSubtitle());
        assertEquals("Title 2", result1.getColumns().get(1).getTitle());
        assertEquals("Subtitle 2", result1.getColumns().get(1).getSubtitle());

        // 두 번째 페이지 호출 (페이지 1)
        ArticleListResponseDTO result2 = articleService.getArticles("sports", null, null, "popular", "1", 2);
        assertEquals(1, result2.getColumns().size());
        assertEquals("Title 3", result2.getColumns().get(0).getTitle());
        assertEquals("Subtitle 3", result2.getColumns().get(0).getSubtitle());
    }

    // 특정 칼럼 반환 테스트
    @Test
    public void testGetArticleById() {
        Article article = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news",
            "Title 1\nSubtitle 1\nhttp://example.com/image1.jpg\nasbsss. diqdn.\n dadaoidn", "http://example.com/image1.jpg", 10);

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        ArticleResponseDTO result = articleService.getArticleById(1L);

        assertEquals("Title 1", result.getTitle());
        assertEquals("http://example.com/image1.jpg", result.getImgurl());

        assertEquals("h2", result.getContent().get(0).getTag());
        assertEquals("Title 1", result.getContent().get(0).getContent());

        assertEquals("h3", result.getContent().get(1).getTag());
        assertEquals("Subtitle 1", result.getContent().get(1).getContent());

        assertEquals("img", result.getContent().get(2).getTag());
        assertEquals("http://example.com/image1.jpg", result.getContent().get(2).getContent());

        assertEquals("p", result.getContent().get(3).getTag());
        assertEquals("asbsss. diqdn.\n dadaoidn", result.getContent().get(3).getContent());
    }

    // 칼럼 업데이트 테스트
    @Test
    public void testUpdateArticle() {
        Article existingArticle = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news",
            "Title 1\nSubtitle 1\nhttp://example.com/image1.jpg", "http://example.com/image1.jpg",10);

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
        Article article = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news", "Title 1\nSubtitle 1\nImage URL 1", "http://example.com/image1.jpg",10);

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        articleService.deleteArticle(1L);

        verify(articleRepository, times(1)).delete(article);
    }
}