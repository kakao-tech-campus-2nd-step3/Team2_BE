package jeje.work.aeatbe.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListResponseDTO {
    private List<SimpleArticleDTO> columns;
    private String nextPageToken;
    private PageInfoDTO pageInfo;
}