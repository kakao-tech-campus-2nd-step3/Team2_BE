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

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    /**
     * 새로운 칼럼을 데이터베이스에 저장
     *
     * @param articleDTO 생성할 칼럼의 정보가 포함된 DTO
     * @return 생성된 칼럼의 DTO
     */
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

    /**
     * 필터링 및 페이지네이션이 적용된 칼럼 목록 반환
     *
     * @param category 칼럼의 카테고리
     * @param title 칼럼의 제목
     * @param subtitle 칼럼의 소제목
     * @param sortby 정렬 기준
     * @param pageToken 페이지 토큰
     * @param maxResults 한 페이지당 가져올 최대 칼럼 개수
     * @return 필터링된 칼럼 목록과 페이지 정보가 포함된 DTO
     */
    public ArticleListResponseDTO getArticles(String category, String title, String subtitle, String sortby, String pageToken, int maxResults) {
        List<SimpleArticleDTO> filteredArticles = articleRepository.findAll().stream()
            .filter(article -> category == null || article.getTags().contains(category))
            .filter(article -> title == null || article.getTitle().contains(title))
            .filter(article -> subtitle == null || extractSubtitle(article.getContent()).contains(subtitle))
            .sorted(getComparator(sortby))
            .skip(getPageOffset(pageToken, maxResults))
            .limit(maxResults)
            .map(this::convertToSimpleDTO)
            .collect(Collectors.toList());

        int totalResults = (int) articleRepository.count();
        PageInfoDTO pageInfo = new PageInfoDTO(totalResults, filteredArticles.size());

        return new ArticleListResponseDTO(filteredArticles, getNextPageToken(pageToken), pageInfo);
    }

    /**
     * 특정 ID에 해당하는 칼럼의 세부 정보 반환
     *
     * @param id 가져올 칼럼의 ID
     * @return 칼럼의 세부 정보가 포함된 DTO
     */
    public ArticleDetailDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found"));

        String[] contentParts = article.getContent().split("\n");

        List<ContentDTO> contentList = new ArrayList<>();
        contentList.add(new ContentDTO("h2", contentParts[0]));

        if (contentParts.length > 1) {
            contentList.add(new ContentDTO("h3", contentParts[1]));
        }

        if (contentParts.length > 2) {
            contentList.add(new ContentDTO("img", contentParts[2]));
        }

        if (contentParts.length > 3) {
            StringBuilder fullContent = new StringBuilder();
            for (int i = 3; i < contentParts.length; i++) {
                fullContent.append(contentParts[i]).append("\n");
            }
            contentList.add(new ContentDTO("p", fullContent.toString().trim()));
        }

        return new ArticleDetailDTO(
            article.getId(),
            article.getTitle(),
            article.getThumbnailUrl(),
            article.getDate(),
            article.getAuthor(),
            List.of(article.getTags().split(",")),
            contentList
        );
    }

    /**
     * 칼럼 업데이트
     *
     * @param id 업데이트할 칼럼의 ID
     * @param articleDTO 업데이트할 내용이 담긴 DTO
     * @return 업데이트된 칼럼의 DTO
     */
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
        Article existingArticle = articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found"));

        Article updatedArticle = new Article(
            existingArticle.getId(),
            articleDTO.getTitle(),
            articleDTO.getDate() != null ? articleDTO.getDate() : existingArticle.getDate(),
            articleDTO.getAuthor(),
            articleDTO.getTags(),
            articleDTO.getContent(),
            articleDTO.getLikes(),
            articleDTO.getThumbnailUrl()
        );

        articleRepository.save(updatedArticle);

        return articleDTO;
    }

    /**
     * 칼럼 삭제
     *
     * @param id 삭제할 칼럼의 ID
     */
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found"));

        articleRepository.delete(article);
    }

    // 정렬 기준에 따른 Comparator 반환 (default=최신)
    private Comparator<Article> getComparator(String sortby) {
        if ("popular".equals(sortby)) {
            // 좋아요 수가 많은 순으로 정렬 (내림차순)
            return Comparator.comparing(Article::getLikes).reversed();
        } else {
            // 기본값: 최신순 (날짜 기준 내림차순)
            return Comparator.comparing(Article::getDate).reversed();
        }
    }

    // 페이지 오프셋 계산
    private int getPageOffset(String pageToken, int maxResults) {
        return pageToken == null ? 0 : Integer.parseInt(pageToken) * maxResults;
    }

    // 다음 페이지 토큰 생성
    private String getNextPageToken(String pageToken) {
        return pageToken == null ? "1" : String.valueOf(Integer.parseInt(pageToken) + 1);
    }

    // SimpleArticleDTO로 변환 로직
    private SimpleArticleDTO convertToSimpleDTO(Article article) {
        return new SimpleArticleDTO(
            article.getId(),
            article.getTitle(),
            extractSubtitle(article.getContent()),
            article.getThumbnailUrl()
        );
    }

    // 소제목 추출 (content의 두 번째 줄을 추출)
    private String extractSubtitle(String content) {
        String[] lines = content.split("\n");
        return lines.length > 1 ? lines[1].trim() : "";
    }



}