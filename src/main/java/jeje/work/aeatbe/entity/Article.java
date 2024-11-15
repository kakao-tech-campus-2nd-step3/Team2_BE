package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import jeje.work.aeatbe.dto.article.ArticleDTO;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "articles",
        indexes = {
                @Index(name = "idx_title", columnList = "title"),
        })
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 10)
    private String subTitle;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(length = 100)
    private String tags;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(name = "thumbnail_url", columnDefinition = "TEXT")
    private String thumbnailUrl;

    public void updateEntity(ArticleDTO articleDTO){
        this.title = articleDTO.title() == null ? this.title : articleDTO.title();
        this.author = articleDTO.author() == null ? this.author : articleDTO.author();
        this.tags = articleDTO.tags() == null ? this.tags : articleDTO.tags();
        this.content = articleDTO.content() == null ? this.content : articleDTO.content();
        this.thumbnailUrl = articleDTO.thumbnailUrl() == null ? this.thumbnailUrl : articleDTO.thumbnailUrl();
        this.subTitle = articleDTO.subTitle() == null ? this.subTitle : articleDTO.subTitle();
    }

}