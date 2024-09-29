package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    private int likes;

    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailUrl;

    public Article() {
    }

    public Article(
            int id,
            String title,
            Timestamp date,
            String author,
            String tags,
            String content,
            int likes,
            String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.author = author;
        this.tags = tags;
        this.content = content;
        this.likes = likes;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}