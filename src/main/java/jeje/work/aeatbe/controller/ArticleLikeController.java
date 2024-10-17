package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.articleLike.ArticleLikeRequestDTO;
import jeje.work.aeatbe.dto.articleLike.ArticleLikeResponseDTO;
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
@RequestMapping({"/api/coulmns/likes", "/api/article/likes"})
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    /**
     * 좋아요를 가져온다
     * @param articleId
     * @param userId
     * @return ArticleLikeResponseDTO
     */
    @GetMapping
    public ResponseEntity<ArticleLikeResponseDTO> getArticleLikes(@RequestParam Long articleId, @RequestParam Long userId) {
        ArticleLike articlelike = articleLikeService.findArticleLikeByUserAndArticle(userId, articleId);
        int like = articleLikeService.getArticleLikeCount(articleId);
        return ResponseEntity.ok().body(new ArticleLikeResponseDTO(articlelike.getId(),like));
    }


    /**
     * 좋아요를 누른다.
     * @param articleLikeRequestDTO
     * @return ArticleLikePostResponseDTO
     */
    @PostMapping
    public ResponseEntity<ArticleLikeResponseDTO> addArticleLike(@RequestBody ArticleLikeRequestDTO articleLikeRequestDTO) {
        ArticleLike articleLike = articleLikeService.likeArticle(articleLikeRequestDTO.getArticleId(), articleLikeRequestDTO.getUserId());
        int like = articleLikeService.getArticleLikeCount(articleLikeRequestDTO.getArticleId());
        return ResponseEntity.ok().body(new ArticleLikeResponseDTO(articleLike.getId(),like));
    }

    /**
     * 좋아요 삭제
     * @param articleLikeId
     * @param userId
     * @return HTTP.status.OK
     */
    @DeleteMapping("/{articleLikeId}")
    public ResponseEntity<?> deleteArticleLike(@PathVariable Long articleLikeId, @RequestParam Long userId) {
        articleLikeService.deleteArticleLike(articleLikeId);
        return ResponseEntity.ok().build();
    }



}
