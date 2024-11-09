package jeje.work.aeatbe.mapper.article;

import java.util.Arrays;
import jeje.work.aeatbe.dto.article.ArticleDTO;
import jeje.work.aeatbe.dto.article.ArticleResponseDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.utility.ArticleUtil;
import org.springframework.stereotype.Component;

/*
칼럼 매퍼
 */
@Component
public class ArticleMapper {

    /**
     * Entity -> DTO
     * @param article
     * @return
     */
    public ArticleDTO toDTO(Article article) {
        return ArticleDTO.builder()
            .title(article.getTitle())
            .date(article.getDate())
            .author(article.getAuthor())
            .tags(article.getTags())
            .content(article.getContent())
            .thumbnailUrl(article.getThumbnailUrl())
            .likes(article.getLikes())
            .build();
    }

    /**
     * Entity -> ResponseDTO
     * @param article
     * @return
     */
    public ArticleResponseDTO toResponseDTO(Article article) {
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
