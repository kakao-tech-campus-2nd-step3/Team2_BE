package jeje.work.aeatbe.dto.article;

import java.util.List;
import lombok.*;
import java.sql.Timestamp;

/**
 * 특정 칼럼 반환할 때 사용하는 형식입니다.
 */
public record ArticleResponseDTO(
    Long id,
    String title,
    String imgurl,
    Timestamp createdAt,
    String auth,
    List<String> keyword,
    List<ContentDTO> content,
    String subtitle
) {
    @Builder
    public ArticleResponseDTO {}
}