package jeje.work.aeatbe.dto.article;

import lombok.*;

/**
 * ArticleListResponseDTO에 사용되는 멤버입니다.
 */
public record ContentDTO(
    String tag,
    String content
) {
    @Builder
    public ContentDTO {}
}