package jeje.work.aeatbe.controller;

import jeje.work.aeatbe.annotation.LoginUser;
import jeje.work.aeatbe.dto.user.LoginUserInfo;
import jeje.work.aeatbe.dto.wishlist.WishDTO;
import jeje.work.aeatbe.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    /**
     * 위시리스트에 새로운 상품을 추가하는 API 엔드포인트
     *
     * @param loginUserInfo 유저, 카카오 id
     * @param productId     추가할 상품의 ID (쿼리 파라미터로 전달)
     * @return 추가된 위시리스트 항목을 담은 WishDTO와 HTTP 상태 코드 201 CREATED
     */
    @PostMapping
    public ResponseEntity<WishDTO> createWish(@LoginUser LoginUserInfo loginUserInfo, @RequestParam Long productId) {
        WishDTO wishDTO = wishListService.createWish(loginUserInfo.userId(), productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishDTO);
    }

    /**
     * 사용자의 위시리스트를 조회하는 API 엔드포인트
     *
     * @param loginUserInfo 유저, 카카오 id
     * @return 사용자의 위시리스트 항목들을 담은 리스트와 HTTP 상태 코드 200 OK
     */
    @GetMapping
    public ResponseEntity<List<WishDTO>> getWishlist(@LoginUser LoginUserInfo loginUserInfo) {
        List<WishDTO> wishlist = wishListService.getWishlist(loginUserInfo.userId());
        return ResponseEntity.ok(wishlist);
    }

    /**
     * 위시리스트의 특정 항목을 수정하는 API 엔드포인트.
     *
     * @param loginUserInfo 유저, 카카오 id
     * @param id            수정할 위시리스트 항목의 ID (PathVariable로 전달)
     * @param newProductId  새로운 상품의 ID (쿼리 파라미터로 전달)
     * @return HTTP 상태 코드 204 No Content.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWish(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long id, @RequestParam Long newProductId) {
        wishListService.updateWish(loginUserInfo.userId(), id, newProductId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 위시리스트에서 특정 항목을 삭제하는 API 엔드포인트
     *
     * @param loginUserInfo 유저, 카카오 id
     * @param id            삭제할 위시리스트 항목의 ID (PathVariable로 전달)
     * @return HTTP 상태 코드 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long id) {
        wishListService.deleteWish(loginUserInfo.userId(), id);
        return ResponseEntity.noContent().build();
    }
}