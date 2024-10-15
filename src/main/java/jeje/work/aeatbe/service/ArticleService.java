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

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ColumnNotFoundException("Article with id " + id + " not found"));

        articleRepository.delete(article);
    }

}

