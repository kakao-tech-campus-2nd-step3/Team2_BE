package jeje.work.aeatbe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {
    private String tag;      // 고정 태그 값 (h2, h3, p, img)
    private String content;  // 실제 내용
}