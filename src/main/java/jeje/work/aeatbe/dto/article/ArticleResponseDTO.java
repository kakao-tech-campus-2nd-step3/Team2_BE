package jeje.work.aeatbe.dto.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.validator.constraints.URL;

/**
 * 특정 칼럼 반환할 때 사용하는 형식입니다.
 */
@Builder
public record ArticleResponseDTO(
        Long id,
        @NotNull
        @Size(max = 100)
        String title,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String imgurl,
        Timestamp createdAt,
        String auth,
        List<String> keyword,
        List<ContentDTO> content,
        String subtitle
) {
}