package jeje.work.aeatbe.dto.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Timestamp;
import org.hibernate.validator.constraints.URL;

@Builder
public record ArticleDTO(
        Long id,
        @NotNull(message = "제목이 존재해야 합니다.")
        @Size(max = 100, message = "제목이 100자를 초과할 수 없습니다.")
        String title,
        String subTitle,
        Timestamp date,
        @NotNull(message = "저자가 존재해야 합니다.")
        @Size(max = 50, message = "저자의 이름이 50자를 초과할 수 없습니다.")
        String author,
        @Size(max = 100, message = "태그 내용이 100자를 초과할 수 없습니다.")
        String tags,
        @NotNull(message = "내용이 존재해야 합니다.")
        String content,
        int likes,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String thumbnailUrl

) {
}