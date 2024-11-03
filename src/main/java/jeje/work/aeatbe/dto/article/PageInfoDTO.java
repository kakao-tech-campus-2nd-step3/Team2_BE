package jeje.work.aeatbe.dto.article;

import lombok.*;

/**
 * ArticleListResponseDTO에 사용되는 멤버입니다.
 */
@Builder
public record PageInfoDTO(
    int totalResults,
    int resultsPerPage
) {
}