package jeje.work.aeatbe.controller;

import java.util.Optional;
import jeje.work.aeatbe.dto.ArticleLikeRequestDTO;
import jeje.work.aeatbe.dto.ArticleLikeResponseDTO;
import jeje.work.aeatbe.entity.ArticleLike;
import jeje.work.aeatbe.service.ArticleLikeService;
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
@RequestMapping("/api/column_likes")
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    public ArticleLikeController(ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    @GetMapping
    public ResponseEntity<ArticleLikeResponseDTO> getArticleLikes(@RequestParam Long articleId, @RequestParam Long userId) {
        Optional<ArticleLike> like = articleLikeService.findArticleLikeByUserAndArticle(userId, articleId);
        if(like.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new ArticleLikeResponseDTO(like.get().getId()));
    }

    @PostMapping
    public ResponseEntity<?> addArticleLike(@RequestBody ArticleLikeRequestDTO articleLikeRequestDTO) {
        articleLikeService.likeArticle(articleLikeRequestDTO.getArticleId(), articleLikeRequestDTO.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticleLike(@PathVariable Long articleId, @RequestParam Long userId) {
        Optional<ArticleLike> like = articleLikeService.findArticleLikeByUserAndArticle(userId, articleId);
        if(like.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        articleLikeService.deleteArticleLike(like.get().getId());
        return ResponseEntity.ok().build();
    }



}
