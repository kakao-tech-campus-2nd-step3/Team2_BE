package jeje.work.aeatbe.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleTest {

    private Article article;

    @BeforeEach
    void setup() {
        article = Article.builder()
            .id(1L)
            .title("Sample Article")
            .date(Timestamp.valueOf("2023-10-31 10:00:00"))
            .author("Author Name")
            .tags("test, article")
            .content("This is a sample article content.")
            .thumbnailUrl("http://example.com/image.jpg")
            .build();
    }

    @Test
    @DisplayName("Article 객체 생성 테스트")
    void testArticleCreation() {
        Article newArticle = Article.builder()
            .id(1L)
            .title("Sample Article")
//            .date(Timestamp.valueOf("2023-10-31 10:00:00"))
            .author("Author Name")
            .tags("test, article")
            .content("This is a sample article content.")
            .thumbnailUrl("http://example.com/image.jpg")
            .build();

        assertThat(newArticle.getId()).isEqualTo(1L);
        assertThat(newArticle.getTitle()).isEqualTo("Sample Article");
        assertThat(newArticle.getAuthor()).isEqualTo("Author Name");
    }

    @Test
    @DisplayName("Article 기본 생성자 검증")
    void testArticleNoArgsConstructor() {
        Article article = new Article();

        assertNotNull(article);
        assertNull(article.getTitle());
        assertNull(article.getAuthor());
        assertNull(article.getContent());
    }

    /*
     * @see ArticleLike
     */
//    @Test
//    @DisplayName("좋아요 증가 메서드 테스트")
//    void testUpLike() {
//        article.upLike();
//        assertThat(article.getLikes()).isEqualTo(1);
//    }

    /*
     * @see ArticleLike
     */
//    @Test
//    @DisplayName("좋아요 감소 메서드 테스트")
//    void testDownLike() {
//        article.upLike();
//        article.downLike();
//        assertThat(article.getLikes()).isEqualTo(0);
//    }

    @Test
    @DisplayName("Null 필드 허용 검증 테스트")
    void testNullableFields() {
        Article articleWithNullFields = Article.builder()
            .id(2L)
            .title("Another Article")
//            .date(Timestamp.valueOf("2023-11-01 12:00:00"))
            .author("Another Author")
            .content("This article has some nullable fields.")
            .build();

        assertThat(articleWithNullFields.getTags()).isNull();
        assertThat(articleWithNullFields.getThumbnailUrl()).isNull();
    }
}