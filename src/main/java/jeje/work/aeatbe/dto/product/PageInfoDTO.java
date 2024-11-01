package jeje.work.aeatbe.dto.product;

import lombok.Builder;

@Builder
public record PageInfoDTO(
    int totalResults,
    int resultsPerPage
) {
}
