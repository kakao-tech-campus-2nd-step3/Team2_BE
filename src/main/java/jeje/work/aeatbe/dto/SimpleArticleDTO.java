package jeje.work.aeatbe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleArticleDTO {
    private Long id;
    private String title;
    private String subtitle;
    private String imageurl;
}