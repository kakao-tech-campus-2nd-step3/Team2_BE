package jeje.work.aeatbe.dto.article;

import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

/**
 * 특정 칼럼 반환할 때 사용하는 형식입니다.
 */
@Builder
public record ArticleResponseDTO(
        Long id,
        String title,
        String imgurl,
        Timestamp createdAt,
        String auth,
        List<String> keyword,
        String content,
        String subtitle
) {
}