package jeje.work.aeatbe.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.ArticleDTO;
import jeje.work.aeatbe.dto.column_dto.ArticleListResponseDTO;
import jeje.work.aeatbe.dto.column_dto.ArticleResponseDTO;
import jeje.work.aeatbe.dto.column_dto.ContentDTO;
import jeje.work.aeatbe.dto.column_dto.PageInfoDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.exception.ColumnNotFoundException;
import jeje.work.aeatbe.repository.ArticleRepository;
import jeje.work.aeatbe.utility.ArticleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        Article article = new Article(
            articleDTO.id(),
            articleDTO.title(),
            articleDTO.date(),
            articleDTO.author(),
            articleDTO.tags(),
            articleDTO.content(),
            articleDTO.thumbnailUrl(),
            articleDTO.likes()
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
                ArticleUtil.extractSubtitle(article.getContent())
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
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        List<String> keywords = Arrays.asList(article.getTags().split(","));
        List<ContentDTO> contentList = ArticleUtil.extractContentList(article.getContent());

        return new ArticleResponseDTO(
            article.getId(),
            article.getTitle(),
            article.getThumbnailUrl(),
            article.getDate(),
            article.getAuthor(),
            keywords,
            contentList,
            ArticleUtil.extractSubtitle(article.getContent())
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
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        Article updatedArticle = new Article(
            existingArticle.getId(),
            articleDTO.title(),
            articleDTO.date() != null ? articleDTO.date() : existingArticle.getDate(),
            articleDTO.author(),
            articleDTO.tags(),
            articleDTO.content(),
            articleDTO.thumbnailUrl(),
            articleDTO.likes()
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
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        articleRepository.delete(article);
    }

}

