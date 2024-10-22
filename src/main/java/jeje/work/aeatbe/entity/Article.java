package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

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


    private int likes;


    public void upLike(){
        this.likes ++;
    }


    public void downLike(){
        this.likes --;
    }

}