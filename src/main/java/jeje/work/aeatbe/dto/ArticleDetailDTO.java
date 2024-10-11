package jeje.work.aeatbe.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailDTO {
    private Long id;
    private String title;
    private String imgurl;
    private Timestamp createdAt;
    private String auth;
    private List<String> keyword;
    private List<ContentDTO> content;
}