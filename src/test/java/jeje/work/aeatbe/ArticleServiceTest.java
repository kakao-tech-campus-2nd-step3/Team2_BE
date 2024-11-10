//package jeje.work.aeatbe;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//import jeje.work.aeatbe.dto.article.ArticleListResponseDTO;
//import jeje.work.aeatbe.dto.article.ArticleResponseDTO;
//import jeje.work.aeatbe.entity.Article;
//import jeje.work.aeatbe.repository.ArticleRepository;
//import jeje.work.aeatbe.service.ArticleService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//@SpringBootTest
//public class ArticleServiceTest {
//
//    @MockBean
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private ArticleService articleService;
//
//    @Test
//    @DisplayName("필터 및 페이지네이션을 사용하여 기사 목록을 가져오는 테스트(칼럼 리스트 반환 기능)")
//    public void testGetArticles() {
//        Article article1 = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news", "Title 1\nSubtitle 1\nImage URL 1", "http://example.com/image1.jpg", 10);
//        Article article2 = new Article(2L, "Title 2", new Timestamp(System.currentTimeMillis()), "Author 2", "news", "Title 2\nSubtitle 2\nImage URL 2", "http://example.com/image2.jpg", 20);
//        Article article3 = new Article(3L, "Title 3", new Timestamp(System.currentTimeMillis()), "Author 3", "sports", "Title 3\nSubtitle 3\nImage URL 3", "http://example.com/image3.jpg", 30);
//
//        Page<Article> page1 = new PageImpl<>(List.of(article1, article2));
//        Page<Article> page2 = new PageImpl<>(List.of(article3));
//
//        when(articleRepository.findAll(any(Pageable.class)))
//            .thenReturn(page1)
//            .thenReturn(page2);
//
//        when(articleRepository.findByTagsContaining(anyString(), any(Pageable.class)))
//            .thenReturn(page1)
//            .thenReturn(page2);
//
//        when(articleRepository.findByTitleContaining(anyString(), any(Pageable.class)))
//            .thenReturn(page1)
//            .thenReturn(page2);
//
//        when(articleRepository.findByContentContaining(anyString(), any(Pageable.class)))
//            .thenReturn(page1)
//            .thenReturn(page2);
//
//        Pageable pageable = PageRequest.of(0, 2, Sort.by("date").descending());
//        Pageable pageable1 = PageRequest.of(1, 2, Sort.by("date").descending());
//
//        // 테스트 케이스 1: 모든 조건이 비어 있을 때 (전체 기사 가져오기)
//        ArticleListResponseDTO result1 = articleService.getArticles("", "", "", pageable);
//        assertEquals(2, result1.columns().size());
//        assertEquals("Title 1", result1.columns().get(0).title());
//        assertEquals("Subtitle 1", result1.columns().get(0).subtitle());
//        assertEquals("Title 2", result1.columns().get(1).title());
//        assertEquals("Subtitle 2", result1.columns().get(1).subtitle());
//        assertEquals(2, result1.pageInfo().resultsPerPage());
//        assertEquals(2, result1.pageInfo().totalResults());
//
//
//        // 테스트 케이스 2: 다음 페이지로 이동 (전체 기사 가져오기)
//        ArticleListResponseDTO result2 = articleService.getArticles("", "", "", pageable1);
//        assertEquals(1, result2.columns().size());
//        assertEquals("Title 3", result2.columns().get(0).title());
//        assertEquals("Subtitle 3", result2.columns().get(0).subtitle());
//        assertEquals(2, result2.pageInfo().resultsPerPage());
//        assertEquals(1, result2.pageInfo().totalResults());
//
//        // 테스트 케이스 3: 특정 카테고리가 존재하는 경우
//        ArticleListResponseDTO result3 = articleService.getArticles("sports", "", "", pageable);
//        assertEquals(2, result3.columns().size());
//
//        // 테스트 케이스 4: 특정 제목이 존재하는 경우
//        ArticleListResponseDTO result4 = articleService.getArticles("", "Title", "", pageable);
//        assertEquals(2, result4.columns().size());
//
//        // 테스트 케이스 5: 특정 소제목이 존재하는 경우
//        ArticleListResponseDTO result5 = articleService.getArticles("", "", "Subtitle", pageable);
//        assertEquals(2, result5.columns().size());
//    }
//
//    @Test
//    @DisplayName("ID로 특정 기사를 가져오는 테스트(특정 칼럼 반환 기능)")
//    public void testGetArticleById() {
//        Article article = new Article(1L, "Title 1", new Timestamp(System.currentTimeMillis()), "Author 1", "sports,news", "Title 1\nSubtitle 1\nImage URL 1", "http://example.com/image1.jpg", 10);
//
//        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
//
//        ArticleResponseDTO result = articleService.getArticleById(1L);
//
//        assertEquals(1L, result.id());
//        assertEquals("Title 1", result.title());
//        assertEquals("http://example.com/image1.jpg", result.imgurl());
//    }
//}