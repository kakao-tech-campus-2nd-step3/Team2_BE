package jeje.work.aeatbe.column_dto;

import java.util.List;
import lombok.*;
import java.sql.Timestamp;

/**
 * 특정 칼럼 반환할 때 사용하는 형식입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDTO {
    private Long id;
    private String title;
    private String imgurl;
    private Timestamp createdAt;
    private String auth;
    private List<String> keyword;
    private List<ContentDTO> content;
    private String subtitle;
}