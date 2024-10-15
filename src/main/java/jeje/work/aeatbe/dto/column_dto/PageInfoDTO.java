package jeje.work.aeatbe.dto.column_dto;

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