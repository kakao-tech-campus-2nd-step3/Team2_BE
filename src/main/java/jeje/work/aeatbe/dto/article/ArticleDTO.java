package jeje.work.aeatbe.dto.article;

import lombok.*;
import java.sql.Timestamp;

@Builder
public record ArticleDTO(
    Long id,
    String title,
    Timestamp date,
    String author,
    String tags,
    String content,
    int likes,
    String thumbnailUrl
) {
}