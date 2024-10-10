package jeje.work.aeatbe.dto;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private Long id;
    private String title;
    private Timestamp date;
    private String author;
    private String tags;
    private String content;
    private int likes;
    private String thumbnailUrl;

}