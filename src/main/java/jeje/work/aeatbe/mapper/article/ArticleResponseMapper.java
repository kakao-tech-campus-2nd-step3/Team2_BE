package jeje.work.aeatbe.mapper.article;

import jeje.work.aeatbe.dto.article.ArticleResponseDTO;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.utility.ArticleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ArticleResponseMapper {

    /**
     * Entity -> ResponseDTO
     *
     * @param article Entity
     * @return
     */
    public ArticleResponseDTO toDTO(Article article) {
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .imgurl(article.getThumbnailUrl())
                .createdAt(Timestamp.valueOf(article.getCreatedAt()))
                .auth(article.getAuthor())
                .keyword(Arrays.asList(article.getTags().split(",")))
                .content(article.getContent())
                .subtitle(article.getSubTitle())
                .build();
    }
}
