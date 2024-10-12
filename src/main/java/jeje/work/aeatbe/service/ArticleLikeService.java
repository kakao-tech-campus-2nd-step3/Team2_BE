package jeje.work.aeatbe.service;

import java.util.Optional;
import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.entity.ArticleLike;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.NotFoundColumnException;
import jeje.work.aeatbe.exception.NotFoundUserException;
import jeje.work.aeatbe.repository.ArticleLikeRepository;
import jeje.work.aeatbe.repository.ArticleRepository;
import jeje.work.aeatbe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    public ArticleLikeService(ArticleLikeRepository articleLikeRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.articleLikeRepository = articleLikeRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * 좋아요를 누릅니다
     * @param userId 사용자 ID
     * @param articleId articleId
     * @throws
     * @throws
     */
    public void likeArticle(Long userId, Long articleId) {
        User user = userRepository.findById(userId)
            .orElseThrow(()->new NotFoundUserException("확인할 수 없는 사용자입니다."));
        Article article = articleRepository.findById(articleId)
            .orElseThrow(()->new NotFoundColumnException("확인할 수 없는 컬럼입니다."));

        ArticleLike like = new ArticleLike(user, article);
        articleLikeRepository.save(like);
    }

    public void deleteArticleLike(Long likeId) {
        Optional<ArticleLike> articleLike = articleLikeRepository.findById(likeId);
        articleLikeRepository.deleteById(likeId);
    }

    public int getLikeCount(Long articleId) {
        return articleLikeRepository.countByArticleId(articleId);
    }

    public Optional<ArticleLike> findArticleLikeByUserAndArticle(Long userId, Long articleId) {
        Optional<ArticleLike> like = articleLikeRepository.findByUserIdAndArticleId(userId,articleId);
        return like;
    }

    public int getArticleLikeCount(Long articleId) {
        return articleLikeRepository.countByArticleId(articleId);
    }



}
