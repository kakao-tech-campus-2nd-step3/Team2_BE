package jeje.work.aeatbe.dto.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Timestamp;
import org.hibernate.validator.constraints.URL;

@Builder
public record ArticleDTO(
        Long id,
        @NotNull
        @Size(max = 100)
        String title,
        Timestamp date,
        @NotNull
        @Size(max = 50)
        String author,
        @Size(max = 100)
        String tags,
        @NotNull
        String content,
        int likes,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String thumbnailUrl
) {
}