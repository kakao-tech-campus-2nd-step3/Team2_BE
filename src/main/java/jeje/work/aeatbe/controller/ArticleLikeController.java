package jeje.work.aeatbe.controller;

import java.util.Optional;
import jeje.work.aeatbe.dto.ArticleLikeRequestDTO;
import jeje.work.aeatbe.dto.ArticleLikeResponseDTO;
import jeje.work.aeatbe.entity.ArticleLike;
import jeje.work.aeatbe.service.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/columnLikes")
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    /**
     * 좋아요를 가져온다
     * @param articleId
     * @param userId
     * @return 좋아요 id
     */
    @GetMapping
    public ResponseEntity<ArticleLikeResponseDTO> getArticleLikes(@RequestParam Long articleId, @RequestParam Long userId) {
        ArticleLike like = articleLikeService.findArticleLikeByUserAndArticle(userId, articleId);
        return ResponseEntity.ok().body(new ArticleLikeResponseDTO(like.getId()));
    }


    /**
     * 좋아요를 누른다.
     * @param articleLikeRequestDTO
     * @return 확인시 HTTP.status.OK
     */
    @PostMapping
    public ResponseEntity<?> addArticleLike(@RequestBody ArticleLikeRequestDTO articleLikeRequestDTO) {
        articleLikeService.likeArticle(articleLikeRequestDTO.getArticleId(), articleLikeRequestDTO.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * 좋아요 삭제
     * @param articleId
     * @param userId
     * @return 성공시 HTTP.status.OK
     */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticleLike(@PathVariable Long articleId, @RequestParam Long userId) {
        ArticleLike like = articleLikeService.findArticleLikeByUserAndArticle(userId, articleId);
        articleLikeService.deleteArticleLike(like.getId());
        return ResponseEntity.ok().build();
    }



}
