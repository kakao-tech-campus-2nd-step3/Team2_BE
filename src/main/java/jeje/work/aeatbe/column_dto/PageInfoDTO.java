package jeje.work.aeatbe.column_dto;

import lombok.*;

/**
 * ArticleListResponseDTO에 사용되는 멤버입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDTO {
    private int totalResults;
    private int resultsPerPage;
}