package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.Article;
import jeje.work.aeatbe.entity.ArticleLike;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.exception.ArticleLikeNotFoundException;
import jeje.work.aeatbe.exception.ColumnNotFoundException;
import jeje.work.aeatbe.exception.UserNotFoundException;
import jeje.work.aeatbe.repository.ArticleLikeRepository;
import jeje.work.aeatbe.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;

    private final UserService userService;

    private final ArticleRepository articleRepository;



    /**
     * 좋아요를 누릅니다
     * @param userId 사용자 ID
     * @param articleId articleId
     * @throws UserNotFoundException:유저가 없을때
     * @throws ColumnNotFoundException(확인 할 수 없는 칼럼)
     * @return articleLikeId
     */
    @Transactional
    public Long likeArticle(Long userId, Long articleId) {
        User user = userService.findById(userId);
        Article article = articleRepository.findById(articleId)
            .orElseThrow(()->new ColumnNotFoundException("확인할 수 없는 컬럼입니다."));

        ArticleLike like = new ArticleLike(user, article);
        articleLikeRepository.save(like);
        return like.getId();
    }

    /**
     * 좋아요 삭제
     * @param likeId
     */
    @Transactional
    public void deleteArticleLike(Long likeId) {
        ArticleLike articleLike = articleLikeRepository.findById(likeId)
            .orElseThrow(()->new ArticleLikeNotFoundException("확인할 수 없는 좋아요 입니다."));
        articleLikeRepository.deleteById(likeId);
    }

    /**
     * 좋아요 찾기
     * @param userId
     * @param articleId
     * @return articleLikeId
     */
    @Transactional(readOnly = true)
    public Long findArticleLikeByUserAndArticle(Long userId, Long articleId) {
        ArticleLike articleLike = articleLikeRepository.findByUserIdAndArticleId(userId,articleId)
            .orElseThrow(()->new ArticleLikeNotFoundException("확인할 수 없는 좋아요입니다."));
        return articleLike.getId();
    }

    /**
     * 좋아요 개수
     * @param articleId
     * @return 해당 컬럼의 좋아요수
     */
    @Transactional(readOnly = true)
    public int getArticleLikeCount(Long articleId) {
        return articleLikeRepository.countByArticleId(articleId);
    }



}
