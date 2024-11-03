package jeje.work.aeatbe.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.article.ArticleDTO;
import jeje.work.aeatbe.dto.article.ArticleListResponseDTO;
import jeje.work.aeatbe.dto.article.ArticleResponseDTO;
import jeje.work.aeatbe.dto.article.ContentDTO;
import jeje.work.aeatbe.dto.article.PageInfoDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.exception.ColumnNotFoundException;
import jeje.work.aeatbe.repository.ArticleRepository;
import jeje.work.aeatbe.utility.ArticleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 새로운 칼럼을 데이터베이스에 저장
     *
     * @param articleDTO 생성할 칼럼의 정보가 포함된 DTO
     * @return 생성된 칼럼의 DTO
     */
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = Article.builder()
            .id(articleDTO.id())
            .title(articleDTO.title())
            .date(articleDTO.date())
            .author(articleDTO.author())
            .tags(articleDTO.tags())
            .content(articleDTO.content())
            .thumbnailUrl(articleDTO.thumbnailUrl())
            .likes(articleDTO.likes())
            .build();
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

        int page = pageToken == null ? 0 : Integer.parseInt(pageToken);
        Pageable pageable = PageRequest.of(page, maxResults, sort);
        Page<Article> articlePage;

        if (category != null && !category.isEmpty() && title != null && !title.isEmpty() && subtitle != null && !subtitle.isEmpty()) {
            articlePage = articleRepository.findByTagsContainingAndTitleContainingAndContentContaining(
                category, title, subtitle, pageable
            );
        }
        else if ((category == null || category.isEmpty()) && (title == null || title.isEmpty()) && (subtitle == null || subtitle.isEmpty())) {
            articlePage = articleRepository.findAll(pageable);
        }
        else if (category != null && !category.isEmpty()) {
            articlePage = articleRepository.findByTagsContaining(category, pageable);
        } else if (title != null && !title.isEmpty()) {
            articlePage = articleRepository.findByTitleContaining(title, pageable);
        } else if (subtitle != null && !subtitle.isEmpty()) {
            articlePage = articleRepository.findByContentContaining(subtitle, pageable);
        } else {
            articlePage = articleRepository.findAll(pageable);
        }

        String nextPageToken = articlePage.hasNext() ? String.valueOf(page + 1) : null;

        PageInfoDTO pageInfo = new PageInfoDTO(
            (int) articlePage.getTotalElements(),
            maxResults
        );

        List<ArticleResponseDTO> columns = articlePage.getContent().stream()
            .map(article -> ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .imgurl(article.getThumbnailUrl())
                .createdAt(article.getDate())
                .auth(article.getAuthor())
                .keyword(Arrays.asList(article.getTags().split(",")))
                .content(null)
                .subtitle(ArticleUtil.extractSubtitle(article.getContent()))
                .build())
            .collect(Collectors.toList());

        return new ArticleListResponseDTO(columns, nextPageToken, pageInfo);
    }

    /**
     * 특정 칼럼 반환
     *
     * @param id 반환할 칼럼의 ID
     * @return 요청된 칼럼의 세부 정보가 포함된 DTO
     */
    public ArticleResponseDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        List<String> keywords = Arrays.asList(article.getTags().split(","));
        List<ContentDTO> contentList = ArticleUtil.extractContentList(article.getContent());

        return ArticleResponseDTO.builder()
            .id(article.getId())
            .title(article.getTitle())
            .imgurl(article.getThumbnailUrl())
            .createdAt(article.getDate())
            .auth(article.getAuthor())
            .keyword(keywords)
            .content(contentList)
            .subtitle(ArticleUtil.extractSubtitle(article.getContent()))
            .build();
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
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        Article updatedArticle = Article.builder()
            .id(existingArticle.getId())
            .title(articleDTO.title())
            .date(articleDTO.date() != null ? articleDTO.date() : existingArticle.getDate())
            .author(articleDTO.author())
            .tags(articleDTO.tags())
            .content(articleDTO.content())
            .thumbnailUrl(articleDTO.thumbnailUrl())
            .likes(articleDTO.likes())
            .build();

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
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        articleRepository.delete(article);
    }

}

