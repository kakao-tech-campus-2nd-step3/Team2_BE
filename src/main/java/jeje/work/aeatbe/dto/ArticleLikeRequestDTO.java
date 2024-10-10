package jeje.work.aeatbe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikeRequestDTO {

    private Long userId;
    private Long articleId;

}
