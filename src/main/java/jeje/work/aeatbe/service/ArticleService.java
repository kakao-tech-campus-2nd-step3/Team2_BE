package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.ArticleDTO;
import jeje.work.aeatbe.column_dto.ArticleListResponseDTO;
import jeje.work.aeatbe.column_dto.ArticleResponseDTO;
import jeje.work.aeatbe.column_dto.ContentDTO;
import jeje.work.aeatbe.column_dto.PageInfoDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.exception.NotFoundColumnException;
import jeje.work.aeatbe.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
            articleDTO.getThumbnailUrl(),
            articleDTO.getLikes()  // 좋아요 수도 그대로 저장
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

        Sort sort = Sort.by(Sort.Direction.DESC, "date");

        // Directly use the parameters without null checks
        Page<Article> articlePage = articleRepository.findByTagsContainingAndTitleContainingAndContentContaining(
            category, title, subtitle,
            PageRequest.of(Integer.parseInt(pageToken), maxResults, sort)
        );

        PageInfoDTO pageInfo = new PageInfoDTO(
            (int) articlePage.getTotalElements(),
            maxResults
        );

        List<ArticleResponseDTO> columns = articlePage.getContent().stream()
            .map(article -> new ArticleResponseDTO(
                article.getId(),
                article.getTitle(),
                article.getThumbnailUrl(),
                article.getDate(),
                article.getAuthor(),
                Arrays.asList(article.getTags().split(",")),
                null,
                extractSubtitle(article.getContent())
            ))
            .collect(Collectors.toList());

        return new ArticleListResponseDTO(columns, pageToken, pageInfo);
    }

    /**
     * 특정 칼럼 반환
     *
     * @param id 반환할 칼럼의 ID
     * @return 요청된 칼럼의 세부 정보가 포함된 DTO
     */
    public ArticleResponseDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new NotFoundColumnException("Article with id " + id + " not found"));

        List<String> keywords = Arrays.asList(article.getTags().split(","));
        List<ContentDTO> contentList = extractContentList(article.getContent());

        return new ArticleResponseDTO(
            article.getId(),
            article.getTitle(),
            article.getThumbnailUrl(),
            article.getDate(),
            article.getAuthor(),
            keywords,
            contentList,
            extractSubtitle(article.getContent())
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
            .orElseThrow(() -> new NotFoundColumnException("Article with id " + id + " not found"));

        Article updatedArticle = new Article(
            existingArticle.getId(),
            articleDTO.getTitle(),
            articleDTO.getDate() != null ? articleDTO.getDate() : existingArticle.getDate(),
            articleDTO.getAuthor(),
            articleDTO.getTags(),
            articleDTO.getContent(),
            articleDTO.getThumbnailUrl(),
            articleDTO.getLikes()
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
            .orElseThrow(() -> new NotFoundColumnException("Article with id " + id + " not found"));

        articleRepository.delete(article);
    }

    private String extractSubtitle(String content) {
        String[] lines = content.split("\n");
        return lines.length > 1 ? lines[1] : "";
    }

    private List<ContentDTO> extractContentList(String content) {
        String[] lines = content.split("\n");
        List<ContentDTO> contentList = new ArrayList<>();

        if (lines.length > 0) contentList.add(new ContentDTO("h2", lines[0])); // 제목
        if (lines.length > 1) contentList.add(new ContentDTO("h3", lines[1])); // 소제목
        if (lines.length > 2) contentList.add(new ContentDTO("img", lines[2])); // 이미지 URL

        if (lines.length > 3) {
            // 본문을 \n 포함한 상태로 그대로 묶어서 처리
            String body = String.join("\n", Arrays.copyOfRange(lines, 3, lines.length));
            contentList.add(new ContentDTO("p", body)); // 본문 전체를 한 번에 추가
        }

        return contentList;
    }

//    private Sort getSortBy(String sortby) {
//        if ("popular".equalsIgnoreCase(sortby)) {
//            return Sort.by(Sort.Direction.DESC, "likes");
//        } else {
//            return Sort.by(Sort.Direction.DESC, "date");
//        }
//    }

}