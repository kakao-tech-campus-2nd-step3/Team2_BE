package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.*;
import jeje.work.aeatbe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Article 생성
    @PostMapping
    public ArticleDTO createArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.createArticle(articleDTO);
    }

    // 칼럼 리스트 반환
    @GetMapping
    public ArticleListResponseDTO getArticles(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String subtitle,
        @RequestParam(required = false) String sortby,
        @RequestParam(required = false) String pageToken,
        @RequestParam(required = false, defaultValue = "10") int maxResults) {

        return articleService.getArticles(category, title, subtitle, sortby, pageToken, maxResults);
    }

    // 특정 칼럼 반환
    @GetMapping("/{id}")
    public ArticleDetailDTO getArticleById(@PathVariable int id) {
        return articleService.getArticleById(id);
    }
}