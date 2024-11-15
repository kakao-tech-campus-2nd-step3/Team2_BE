package jeje.work.aeatbe.dto.article;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record ArticleDTO(
        Long id,
        String title,
        String subTitle,
        Timestamp date,
        String author,
        String tags,
        String content,
        int likes,
        String thumbnailUrl

) {
}