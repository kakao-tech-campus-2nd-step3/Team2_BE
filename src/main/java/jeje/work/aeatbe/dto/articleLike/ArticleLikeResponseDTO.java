package jeje.work.aeatbe.dto.articleLike;


import lombok.Builder;


@Builder
public record ArticleLikeResponseDTO(Long articleLikeId, int count) {

}
