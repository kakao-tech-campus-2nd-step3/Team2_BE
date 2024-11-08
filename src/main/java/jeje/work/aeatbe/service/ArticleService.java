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
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.ColumnNotFoundException;
import jeje.work.aeatbe.exception.UserNotFoundException;
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
        return mapToDTO(article);
    }

    public ArticleListResponseDTO getArticles(String category, String title, String subtitle, Pageable pageable) {
        Page<Article> articlePage = applyFilters(category, title, subtitle, pageable);

        PageInfoDTO pageInfo = PageInfoDTO.builder()
            .totalResults((int) articlePage.getTotalElements())
            .resultsPerPage(pageable.getPageSize())
            .build();

        List<ArticleResponseDTO> columns = articlePage.getContent().stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());

        return new ArticleListResponseDTO(columns, pageInfo);
    }

    public ArticleResponseDTO getArticleById(Long id) {
        Article article = findArticle(id);
        return mapToResponseDTO(article);
    }

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
        return mapToDTO(existingArticle);
    }

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

    private Article findArticle(Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));
    }

    private ArticleDTO mapToDTO(Article article) {
        return ArticleDTO.builder()
            .id(article.getId())
            .title(article.getTitle())
            .date(article.getDate())
            .author(article.getAuthor())
            .tags(article.getTags())
            .content(article.getContent())
            .thumbnailUrl(article.getThumbnailUrl())
            .likes(article.getLikes())
            .build();
    }

    private ArticleResponseDTO mapToResponseDTO(Article article) {
        return ArticleResponseDTO.builder()
            .id(article.getId())
            .title(article.getTitle())
            .imgurl(article.getThumbnailUrl())
            .createdAt(article.getDate())
            .auth(article.getAuthor())
            .keyword(Arrays.asList(article.getTags().split(",")))
            .content(ArticleUtil.extractContentList(article.getContent()))
            .subtitle(ArticleUtil.extractSubtitle(article.getContent()))
            .build();
    }
}

