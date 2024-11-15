package jeje.work.aeatbe.controller;

import jakarta.validation.Valid;
import jeje.work.aeatbe.dto.article.ArticleDTO;
import jeje.work.aeatbe.dto.article.ArticleResponseDTO;
import jeje.work.aeatbe.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/columns", "/api/article"})
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 새로운 칼럼 생성
     *
     * @param articleDTO 생성할 칼럼의 세부 정보가 포함된 DTO
     * @return 생성된 칼럼의 DTO와 상태 코드 201 (Created)
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    /**
     * 필터와 페이지네이션 기반으로 칼럼 목록 반환
     *
     * @param category 칼럼의 카테고리
     * @param title    칼럼의 제목
     * @param subtitle 칼럼의 소제목
     * @param pageable 페이지네이션 정보 (기본값: 페이지 크기 10, 날짜 기준 내림차순 정렬)
     * @return 칼럼 목록과 페이지 정보가 포함된 DTO와 상태 코드 200 (OK)
     */
    @GetMapping
    public ResponseEntity<Page<ArticleResponseDTO>> getArticles(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String subtitle,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ArticleResponseDTO> articles = articleService.getArticles(category, title, subtitle, pageable);
        return ResponseEntity.ok(articles);
    }

    /**
     * 특정 칼럼 반환 API
     *
     * @param id 반환할 칼럼의 ID
     * @return 요청된 칼럼의 세부 정보가 포함된 DTO와 상태 코드 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable Long id) {
        ArticleResponseDTO article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    /**
     * 칼럼 업데이트 (PATCH)
     *
     * @param id         업데이트할 칼럼의 ID
     * @param articleDTO 업데이트할 내용이 담긴 DTO
     * @return 업데이트된 칼럼의 DTO와 상태 코드 200 (OK)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody @Valid ArticleDTO articleDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    /**
     * 칼럼 삭제 (DELETE)
     *
     * @param id 삭제할 칼럼의 ID
     * @return 상태 코드 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}