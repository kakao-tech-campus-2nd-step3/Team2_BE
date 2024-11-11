package jeje.work.aeatbe.service;

import java.util.List;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.article.ArticleDTO;
import jeje.work.aeatbe.dto.article.ArticleListResponseDTO;
import jeje.work.aeatbe.dto.article.ArticleResponseDTO;
import jeje.work.aeatbe.dto.article.PageInfoDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.exception.ColumnNotFoundException;
import jeje.work.aeatbe.mapper.article.ArticleMapper;
import jeje.work.aeatbe.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;


    /**
     * 새로운 칼럼을 데이터베이스에 저장
     *
     * @param articleDTO 생성할 칼럼의 정보가 포함된 DTO
     * @return 생성된 칼럼의 DTO
     */
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = Article.builder()
            .title(articleDTO.title())
            .date(articleDTO.date())
            .author(articleDTO.author())
            .tags(articleDTO.tags())
            .content(articleDTO.content())
            .thumbnailUrl(articleDTO.thumbnailUrl())
            .likes(articleDTO.likes())
            .build();

        article = articleRepository.save(article);
        return articleMapper.toDTO(article);
    }

    /**
     * 필터링 및 페이지네이션이 적용된 칼럼 목록 반환
     *
     * @param category 칼럼의 카테고리
     * @param title 칼럼의 제목
     * @param subtitle 칼럼의 소제목
     * @param pageable 페이지네이션 정보
     * @return 필터링된 칼럼 목록과 페이지 정보가 포함된 DTO
     */
    @Transactional(readOnly = true)
    public ArticleListResponseDTO getArticles(String category, String title, String subtitle, Pageable pageable) {
        Page<Article> articlePage = applyFilters(category, title, subtitle, pageable);

        PageInfoDTO pageInfo = PageInfoDTO.builder()
            .totalResults((int) articlePage.getTotalElements())
            .resultsPerPage(pageable.getPageSize())
            .build();

        List<ArticleResponseDTO> columns = articlePage.getContent().stream()
            .map(articleMapper::toResponseDTO)
            .collect(Collectors.toList());

        return new ArticleListResponseDTO(columns, pageInfo);
    }

    /**
     * 특정 칼럼 반환
     *
     * @param id 반환할 칼럼의 ID
     * @return 요청된 칼럼의 세부 정보가 포함된 DTO
     */
    @Transactional(readOnly = true)
    public ArticleResponseDTO getArticleById(Long id) {
        Article article = findArticle(id);
        return articleMapper.toResponseDTO(article);
    }

    /**
     * 칼럼 업데이트
     *
     * @param id 업데이트할 칼럼의 ID
     * @param articleDTO 업데이트할 내용이 담긴 DTO
     * @return 업데이트된 칼럼의 DTO
     */
    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
        Article existingArticle = findArticle(id);

        existingArticle = Article.builder()
            .id(existingArticle.getId())
            .title(articleDTO.title() != null ? articleDTO.title() : existingArticle.getTitle())
            .date(articleDTO.date() != null ? articleDTO.date() : existingArticle.getDate())
            .author(articleDTO.author() != null ? articleDTO.author() : existingArticle.getAuthor())
            .tags(articleDTO.tags() != null ? articleDTO.tags() : existingArticle.getTags())
            .content(articleDTO.content() != null ? articleDTO.content() : existingArticle.getContent())
            .thumbnailUrl(articleDTO.thumbnailUrl() != null ? articleDTO.thumbnailUrl() : existingArticle.getThumbnailUrl())
            .likes(existingArticle.getLikes())
            .build();

        articleRepository.save(existingArticle);
        return articleMapper.toDTO(existingArticle);
    }

    /**
     * 칼럼 삭제
     *
     * @param id 삭제할 칼럼의 ID
     */
    @Transactional
    public void deleteArticle(Long id) {
        Article article = findArticle(id);
        articleRepository.delete(article);
    }

    private Page<Article> applyFilters(String category, String title, String subtitle, Pageable pageable) {
        if (category != null && !category.isEmpty() && title != null && !title.isEmpty() && subtitle != null && !subtitle.isEmpty()) {
            return articleRepository.findByTagsContainingAndTitleContainingAndContentContaining(
                category, title, subtitle, pageable
            );
        } else if ((category == null || category.isEmpty()) && (title == null || title.isEmpty()) && (subtitle == null || subtitle.isEmpty())) {
            return articleRepository.findAll(pageable);
        } else if (category != null && !category.isEmpty()) {
            return articleRepository.findByTagsContaining(category, pageable);
        } else if (title != null && !title.isEmpty()) {
            return articleRepository.findByTitleContaining(title, pageable);
        } else {
            return articleRepository.findByContentContaining(subtitle, pageable);
        }
    }

    @Transactional(readOnly = true)
    protected Article findArticle(Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));
    }
}

