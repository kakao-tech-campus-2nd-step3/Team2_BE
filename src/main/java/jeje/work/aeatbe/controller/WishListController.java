package jeje.work.aeatbe.controller;

import java.util.List;
import jeje.work.aeatbe.dto.wishlist.WishDTO;
import jeje.work.aeatbe.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    /**
     * 위시리스트에 새로운 상품을 추가하는 API 엔드포인트
     *
     * @param token Authorization 헤더에 포함된 사용자 인증 토큰
     * @param productId 추가할 상품의 ID (쿼리 파라미터로 전달)
     * @return 추가된 위시리스트 항목을 담은 WishDTO와 HTTP 상태 코드 201 CREATED
     */
    @PostMapping
    public ResponseEntity<WishDTO> createWish(@RequestHeader("Authorization") String token, @RequestParam Long productId) {
        WishDTO wishDTO = wishListService.createWish(token, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishDTO);
    }

    /**
     * 사용자의 위시리스트를 조회하는 API 엔드포인트
     *
     * @param token Authorization 헤더에 포함된 사용자 인증 토큰
     * @return 사용자의 위시리스트 항목들을 담은 리스트와 HTTP 상태 코드 200 OK
     */
    @GetMapping
    public ResponseEntity<List<WishDTO>> getWishlist(@RequestHeader("Authorization") String token) {
        List<WishDTO> wishlist = wishListService.getWishlist(token);
        return ResponseEntity.ok(wishlist);
    }

    /**
     * 위시리스트의 특정 항목을 수정하는 API 엔드포인트.
     *
     * @param token Authorization 헤더에 포함된 사용자 인증 토큰
     * @param id 수정할 위시리스트 항목의 ID (PathVariable로 전달)
     * @param newProductId 새로운 상품의 ID (쿼리 파라미터로 전달)
     * @return HTTP 상태 코드 204 No Content.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWish(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestParam Long newProductId) {
        wishListService.updateWish(token, id, newProductId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 위시리스트에서 특정 항목을 삭제하는 API 엔드포인트
     *
     * @param token Authorization 헤더에 포함된 사용자 인증 토큰
     * @param id 삭제할 위시리스트 항목의 ID (PathVariable로 전달)
     * @return HTTP 상태 코드 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        wishListService.deleteWish(token, id);
        return ResponseEntity.noContent().build();
    }
}