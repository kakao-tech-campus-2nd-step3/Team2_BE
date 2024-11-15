package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.articleLike.ArticleLikeCountResponseDTO;
import jeje.work.aeatbe.dto.articleLike.IsLikedResponseDTO;
import jeje.work.aeatbe.dto.articleLike.ArticleLikeRequestDTO;
import jeje.work.aeatbe.dto.articleLike.ArticleLikeResponseDTO;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.service.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/columns/likes", "/api/article/likes"})
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    /**
     * 좋아요를 가져온다
     *
     * @param articleId
     * @param loginUserInfo
     * @return ArticleLikeResponseDTO
     */
    @GetMapping
    public ResponseEntity<IsLikedResponseDTO> getArticleLikes(@RequestParam Long articleId, @LoginUser LoginUserInfo loginUserInfo) {
        Long articleLikeId = articleLikeService.findArticleLikeByUserAndArticle(loginUserInfo.userId(), articleId);
        boolean isliked = articleLikeId != -1;
        return ResponseEntity.ok().body(new IsLikedResponseDTO(isliked));
    }


    /**
     * 좋아요를 누른다.
     *
     * @param articleLikeRequestDTO
     * @return ArticleLikePostResponseDTO
     */
    @PostMapping
    public ResponseEntity<ArticleLikeResponseDTO> addArticleLike(@RequestBody ArticleLikeRequestDTO articleLikeRequestDTO, @LoginUser LoginUserInfo loginUserInfo) {
        Long articleLikeId = articleLikeService.likeArticle(loginUserInfo.userId(), articleLikeRequestDTO.articleId());
        int like = articleLikeService.getArticleLikeCount(articleLikeRequestDTO.articleId());
        return ResponseEntity.ok().body(new ArticleLikeResponseDTO(articleLikeId, like));
    }

    /**
     * 좋아요 삭제
     *
     * @param articleId
     * @param loginUserInfo
     * @return HTTP.status.OK
     */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticleLike(@PathVariable Long articleId, @LoginUser LoginUserInfo loginUserInfo) {
        Long articleLikeId = articleLikeService.findArticleLikeByUserAndArticle(articleId, loginUserInfo.userId());
        articleLikeService.deleteArticleLike(articleLikeId);
        return ResponseEntity.ok().build();
    }


    /**
     * 종아요 수를 반환
     *
     * @param articleId
     * @return ArticleLikeCountResponseDTO
     */
    @GetMapping("/count")
    public ResponseEntity<ArticleLikeCountResponseDTO> getArticleLikeCount(@RequestParam Long articleId) {
        int likeCount = articleLikeService.getArticleLikeCount(articleId);
        return ResponseEntity.ok().body( new ArticleLikeCountResponseDTO(likeCount));
    }


}
