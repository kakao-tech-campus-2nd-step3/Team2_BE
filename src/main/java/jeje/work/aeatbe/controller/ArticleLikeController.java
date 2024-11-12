package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.annotation.LoginUser;
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
    public ResponseEntity<ArticleLikeResponseDTO> getArticleLikes(@RequestParam Long articleId, @LoginUser LoginUserInfo loginUserInfo) {
        Long articleLikeId = articleLikeService.findArticleLikeByUserAndArticle(loginUserInfo.userId(), articleId);
        int like = articleLikeService.getArticleLikeCount(articleId);
        return ResponseEntity.ok().body(new ArticleLikeResponseDTO(articleLikeId, like));
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
     * @param articleLikeId
     * @param loginUserInfo
     * @return HTTP.status.OK
     */
    @DeleteMapping("/{articleLikeId}")
    public ResponseEntity<?> deleteArticleLike(@PathVariable Long articleLikeId, @LoginUser LoginUserInfo loginUserInfo) {
        articleLikeService.deleteArticleLike(loginUserInfo.userId());
        return ResponseEntity.ok().build();
    }


}
