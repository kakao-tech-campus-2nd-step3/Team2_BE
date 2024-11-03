package jeje.work.aeatbe.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleLikeTest {

    private User user;
    private Article article;
    private ArticleLike articleLike;

    @BeforeEach
    void setup() {
        user = User.builder()
            .kakaoId("12345")
            .userName("TestUser")
            .build();
        user.setId(1L);

        article = Article.builder()
            .id(1L)
            .title("Sample Article")
            .author("Author Name")
            .content("This is a sample article content.")
            .build();

        articleLike = new ArticleLike(user, article);
    }

    @Test
    @DisplayName("ArticleLike 객체 생성 테스트")
    void testArticleLikeCreation() {
        ArticleLike newArticleLike = new ArticleLike(user, article);

        assertThat(newArticleLike.getUser()).isEqualTo(user);
        assertThat(newArticleLike.getArticle()).isEqualTo(article);
    }

    @Test
    @DisplayName("ArticleLike 기본 생성자 검증")
    void testArticleLikeNoArgsConstructor() {
        ArticleLike articleLike = new ArticleLike();

        assertNotNull(articleLike);
        assertNull(articleLike.getUser());
        assertNull(articleLike.getArticle());
    }

    @Test
    @DisplayName("User와 Article 관계 매핑 테스트")
    void testArticleLikeUserArticleMapping() {
        assertThat(articleLike.getUser()).isNotNull();
        assertThat(articleLike.getUser().getKakaoId()).isEqualTo("12345");
        assertThat(articleLike.getUser().getUserName()).isEqualTo("TestUser");

        assertThat(articleLike.getArticle()).isNotNull();
        assertThat(articleLike.getArticle().getTitle()).isEqualTo("Sample Article");
        assertThat(articleLike.getArticle().getAuthor()).isEqualTo("Author Name");
    }

}