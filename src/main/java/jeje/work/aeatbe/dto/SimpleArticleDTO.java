package jeje.work.aeatbe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleArticleDTO {
    private int id;
    private String title;
    private String subtitle; // 소제목
    private String imageurl; // 이미지 주소 (thumbnailUrl에서 매핑)
}