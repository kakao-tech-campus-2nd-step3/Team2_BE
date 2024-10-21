package jeje.work.aeatbe.dto.article;

import lombok.*;

/**
 * ArticleListResponseDTO에 사용되는 멤버입니다.
 */
public record PageInfoDTO(
    int totalElements,
    int pageSize
) {
    @Builder
    public PageInfoDTO {}
}