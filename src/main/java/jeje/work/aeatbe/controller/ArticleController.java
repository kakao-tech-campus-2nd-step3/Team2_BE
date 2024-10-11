package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.dto.*;
import jeje.work.aeatbe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 새로운 칼럼 생성
     *
     * @param articleDTO 생성할 칼럼의 세부 정보가 포함된 DTO
     * @return 생성된 칼럼의 DTO
     */
    @PostMapping
    public ArticleDTO createArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.createArticle(articleDTO);
    }

    /**
     * 필터와 페이지네이션 기반으로 칼럼 목록 반환
     *
     * @param category 칼럼의 카테고리
     * @param title 칼럼의 제목
     * @param subtitle 칼럼의 소제목
     * @param sortby 정렬 기준
     * @param pageToken 페이지 토큰
     * @param maxResults 한 페이지당 가져올 칼럼의 최대 개수 (기본값: 10)
     * @return 칼럼 목록과 페이지 정보가 포함된 DTO
     */
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

    /**
     * 특정 ID에 해당하는 칼럼 반환.
     *
     * @param id 가져올 칼럼의 ID
     * @return 칼럼의 세부 정보가 포함된 DTO
     */
    @GetMapping("/{id}")
    public ArticleDetailDTO getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    /**
     * 칼럼 업데이트 (PATCH)
     *
     * @param id 업데이트할 칼럼의 ID
     * @param articleDTO 업데이트할 내용이 담긴 DTO
     * @return 업데이트된 칼럼의 DTO
     */
    @PatchMapping("/{id}")
    public ArticleDTO updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        return articleService.updateArticle(id, articleDTO);
    }

    /**
     * 칼럼 삭제 (DELETE)
     *
     * @param id 삭제할 칼럼의 ID
     */
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}