package jeje.work.aeatbe.dto.articleLike;


import lombok.Builder;


@Builder
public record ArticleLikeRequestDTO(Long userId, Long articleId) {

}
