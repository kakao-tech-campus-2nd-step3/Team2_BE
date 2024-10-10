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
    @Column(nullable = false)
    private String content;



    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailUrl;

    /**
     * 질문: 이렇게 article에서도 좋아요 수를 관리를 굳이 따로 해주어야하나요?
     */
    private int likes;

    /**
     * 좋아요 수를 증가시킨다
     */
    public void upLike(){
        this.likes ++;
    }

    /**
     * 좋아요 수를 감소시킨다
     */
    public void downLike(){
        this.likes --;
    }

}