package jeje.work.aeatbe.mapper.article;

import jeje.work.aeatbe.dto.article.ArticleDTO;
import jeje.work.aeatbe.entity.Article;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ArticleMapper {

    /**
     * Entity -> DTO
     *
     * @param article
     * @return
     */
    public ArticleDTO toDTO(Article article) {
        return ArticleDTO.builder()
                .title(article.getTitle())
                .date(Timestamp.valueOf(article.getUpdatedAt()))
                .author(article.getAuthor())
                .tags(article.getTags())
                .content(article.getContent())
                .thumbnailUrl(article.getThumbnailUrl())
                .subTitle(article.getSubTitle())
                .build();
    }

    /**
     * DTO -> Entity
     *
     * @param articleDTO
     * @param idRequired
     * @return
     */
    public Article toEntity(ArticleDTO articleDTO, boolean idRequired) {
        return Article.builder()
                .id(idRequired ? articleDTO.id() : null)
                .title(articleDTO.title())
                .author(articleDTO.author())
                .tags(articleDTO.tags())
                .content(articleDTO.content())
                .thumbnailUrl(articleDTO.thumbnailUrl())
                .subTitle(articleDTO.subTitle())
                .build();
    }

    /**
     * DTO -> Entity
     *
     * @param article Entity
     * @return
     */
    public Article toEntity(ArticleDTO article){
        return toEntity(article, false);
    }

}
