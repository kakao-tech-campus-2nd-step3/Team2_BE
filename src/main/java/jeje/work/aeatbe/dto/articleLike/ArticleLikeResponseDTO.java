package jeje.work.aeatbe.dto.articleLike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikeResponseDTO {

    private Long articleLikeId;
    private int count; //좋아요 개수

}
