package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import jeje.work.aeatbe.dto.ArticleDTO;
import jeje.work.aeatbe.dto.ArticleDetailDTO;
import jeje.work.aeatbe.dto.ArticleListResponseDTO;
import jeje.work.aeatbe.dto.ContentDTO;
import jeje.work.aeatbe.dto.PageInfoDTO;
import jeje.work.aeatbe.dto.SimpleArticleDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Article 생성 로직
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = new Article(
            articleDTO.getId(),
            articleDTO.getTitle(),
            articleDTO.getDate(),
            articleDTO.getAuthor(),
            articleDTO.getTags(),
            articleDTO.getContent(),
            articleDTO.getLikes(),
            articleDTO.getThumbnailUrl()
        );
        articleRepository.save(article);
        return articleDTO;
    }

    // 칼럼 리스트 반환 로직
    public ArticleListResponseDTO getArticles(String category, String title, String subtitle, String sortby, String pageToken, int maxResults) {
        List<SimpleArticleDTO> filteredArticles = articleRepository.findAll().stream()
            .filter(article -> category == null || article.getTags().contains(category)) // 카테고리 필터링
            .filter(article -> title == null || article.getTitle().contains(title))       // 제목 필터링
            .filter(article -> subtitle == null || extractSubtitle(article.getContent()).contains(subtitle)) // 소제목 필터링
            .sorted(getComparator(sortby)) // 정렬 기준에 따른 정렬
            .skip(getPageOffset(pageToken, maxResults)) // 페이지네이션 처리
            .limit(maxResults)
            .map(this::convertToSimpleDTO) // SimpleArticleDTO로 변환
            .collect(Collectors.toList());

        int totalResults = (int) articleRepository.count();
        PageInfoDTO pageInfo = new PageInfoDTO(totalResults, filteredArticles.size());

        return new ArticleListResponseDTO(filteredArticles, getNextPageToken(pageToken), pageInfo);
    }

    // 특정 칼럼 반환 로직
    public ArticleDetailDTO getArticleById(int id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found"));

        // Article의 content를 \n로 분리하고 ContentDTO 리스트로 변환
        String[] contentParts = article.getContent().split("\n");
        List<ContentDTO> contentList = new ArrayList<>();

        for (String part : contentParts) {
            if (part.contains("h2")) {
                contentList.add(new ContentDTO("h2", part));
            } else if (part.contains("h3")) {
                contentList.add(new ContentDTO("h3", part));
            } else if (part.contains("img")) {
                contentList.add(new ContentDTO("img", part));
            } else {
                contentList.add(new ContentDTO("p", part)); // 기본적으로 본문 내용은 p로 처리
            }
        }

        // ArticleDetailDTO로 변환
        return new ArticleDetailDTO(
            article.getId(),
            article.getTitle(),
            article.getThumbnailUrl(), // imgurl로 사용
            article.getDate(),
            article.getAuthor(),
            List.of(article.getTags().split(",")), // tags를 키워드 배열로 변환
            contentList
        );
    }

    // 정렬 기준에 따른 Comparator 반환
    private Comparator<Article> getComparator(String sortby) {
        if ("price".equals(sortby)) {
            return Comparator.comparing(Article::getLikes); // price 대신 likes 예시로 사용
        } else if ("recommend".equals(sortby)) {
            return Comparator.comparing(Article::getLikes).reversed(); // recommend는 likes의 역순 예시
        } else if ("sales".equals(sortby)) {
            return Comparator.comparing(Article::getLikes); // sales도 likes로 정렬 예시
        } else {
            return Comparator.comparing(Article::getDate).reversed(); // 기본: 최신순(new)
        }
    }

    // 페이지 오프셋 계산 (pageToken 활용)
    private int getPageOffset(String pageToken, int maxResults) {
        return pageToken == null ? 0 : Integer.parseInt(pageToken) * maxResults;
    }

    // 다음 페이지 토큰 생성 (pageToken 활용)
    private String getNextPageToken(String pageToken) {
        return pageToken == null ? "1" : String.valueOf(Integer.parseInt(pageToken) + 1);
    }

    // SimpleArticleDTO로 변환 로직
    private SimpleArticleDTO convertToSimpleDTO(Article article) {
        return new SimpleArticleDTO(
            article.getId(),
            article.getTitle(),
            extractSubtitle(article.getContent()), // 소제목은 content의 두 번째 줄에서 추출
            article.getThumbnailUrl() // 이미지 URL은 article의 thumbnailUrl에서 가져옴
        );
    }

    // 소제목 추출 (content의 두 번째 줄을 추출)
    private String extractSubtitle(String content) {
        String[] lines = content.split("\n");
        return lines.length > 1 ? lines[1].trim() : ""; // 두 번째 줄을 소제목으로 반환
    }
}