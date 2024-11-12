package jeje.work.aeatbe.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.entity.ArticleLike;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.repository.ArticleLikeRepository;
import jeje.work.aeatbe.repository.ArticleRepository;
import jeje.work.aeatbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;

    @PostMapping("/index/init")
    public void index() {

        List<Article> articles = new ArrayList<>();
        List<User> users = new ArrayList<>();
        Set<String> uniqueLikes = new HashSet<>(); // userId와 articleId 조합의 중복 방지
        Random random = new Random();
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

        // 아티클 50개 생성
        for (int i = 1; i <= 500; i++) {
            Article article = Article.builder()
                .title("Sample Title " + i)
                .author("testUser" + i)
                .content("sample content " + i)
                .tags("sample tags " + i)
                .likes(0)
                .build();
            articles.add(articleRepository.save(article));
        }

        Collections.shuffle(users);
        Collections.shuffle(articles);

//        for (Article article : articles) {
//            for(User user : users) {
//                articleLikeRepository.save(new ArticleLike(user,article));
//            }
//        }
        int generatedLikes = 0;
        while (generatedLikes < 30000) {
            User randomUser = users.get(random.nextInt(users.size()));
            Article randomArticle = articles.get(random.nextInt(articles.size()));

            String uniqueKey = randomUser.getId() + "_" + randomArticle.getId();

            // 중복된 조합이 아니면 저장
            if (!uniqueLikes.contains(uniqueKey)) {
                uniqueLikes.add(uniqueKey);
                articleLikeRepository.save(new ArticleLike(randomUser, randomArticle));
                generatedLikes++;
            }
        }


    }

    @PostMapping("/index/test1")
    public String test1() {
        long startTime= System.currentTimeMillis();
        Random random = new Random();
        for(int i =0; i<100; i++) {
            int count = articleLikeRepository.countByArticleId(random.nextLong(50));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        return "Execution time: " + (endTime - startTime) + " ms" +random.nextLong(50);
    }

    @PostMapping("/index/test2")
    public String test2() {
        long startTime= System.currentTimeMillis();
        Random random = new Random();
        for(int i =0; i<100; i++) {
            Optional<ArticleLike> result = articleLikeRepository.findByUserIdAndArticleId(
                random.nextLong(50), random.nextLong(50));

        }
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        return "Execution time: " + (endTime - startTime) + " ms" +random.nextLong(50);
    }
}
