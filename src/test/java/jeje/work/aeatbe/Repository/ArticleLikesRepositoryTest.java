package jeje.work.aeatbe.Repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.LongStream;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.entity.ArticleLike;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.repository.ArticleLikeRepository;
import jeje.work.aeatbe.repository.ArticleRepository;
import jeje.work.aeatbe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ArticleLikesRepositoryTest {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    private List<ArticleLike> articleLikes = new ArrayList<>();
    private List<Article> articles = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 100; i++) {
            User user = User.builder()
                .kakaoId("kakao_" + i)
                .userName("testUser" + i)
                .userImgUrl("url" + i)
                .kakaoAccessToken("accessToken" + i)
                .kakaoRefreshToken("refreshToken" + i)
                .jwtRefreshToken("jwtToken" + i)
                .build();
            users.add(userRepository.save(user));
        }


        for (int i = 1; i <= 100; i++) {
            Article article = Article.builder()
                .title("Sample Title " + i)
                .author("testUser" + i)
                .content("sample content " + i)
                .tags("sample tags " + i)
                .likes(0)
                .build();
            articles.add(articleRepository.save(article));
        }

        Collections.shuffle(articles);
        Collections.shuffle(articleLikes);


        for (Article article : articles) {
            for (int j = 0; j < 100; j++) {
                User randomUser = users.get(j);
                articleLikes.add(new ArticleLike(randomUser, article));
            }
        }
        Collections.shuffle(articleLikes);
        articleLikeRepository.saveAll(articleLikes);
    }

    @Test
    void testCountByArticleIdPerformance() {
        Article testArticle = articles.get(0);

        long startTime= System.currentTimeMillis();
        int count = articleLikeRepository.countByArticleId(testArticle.getId());
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) + " ms");


        assertThat(count).isEqualTo(100);

    }

    @Test
    void testFindByUserIdAndArticleIdPerformance() {
        User testUser = users.get(0);
        Article testArticle = articles.get(0);

        long startTime = System.currentTimeMillis();
        Optional<ArticleLike> result = articleLikeRepository.findByUserIdAndArticleId(testUser.getId(), testArticle.getId());
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time for findByUserIdAndArticleId: " + (endTime - startTime) + " ms");

        assertThat(result).isPresent();
        assertThat(result.get().getUser().getId()).isEqualTo(testUser.getId());
        assertThat(result.get().getArticle().getId()).isEqualTo(testArticle.getId());
    }
}
