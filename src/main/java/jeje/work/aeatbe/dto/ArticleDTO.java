package jeje.work.aeatbe.dto;

import java.sql.Timestamp;

public class ArticleDTO {
    private int id;
    private String title;
    private Timestamp date;
    private String author;
    private String tags;
    private String content;
    private int likes;
    private String thumbnailUrl;

    public ArticleDTO() {
    }

    public ArticleDTO(
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

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}