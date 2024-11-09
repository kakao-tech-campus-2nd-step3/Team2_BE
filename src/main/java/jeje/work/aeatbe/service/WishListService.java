package jeje.work.aeatbe.service;

import java.util.List;
import java.util.stream.Collectors;
import jeje.work.aeatbe.dto.wishlist.WishDTO;
import jeje.work.aeatbe.dto.wishlist.WishProductDTO;
import jeje.work.aeatbe.entity.Product;
import jeje.work.aeatbe.entity.User;
import jeje.work.aeatbe.entity.Wishlist;
import jeje.work.aeatbe.exception.UserNotFoundException;
import jeje.work.aeatbe.exception.WishlistNotFoundException;
import jeje.work.aeatbe.mapper.product.ProductMapper;
import jeje.work.aeatbe.repository.UserRepository;
import jeje.work.aeatbe.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;

    /**
     * 위시리스트 항목을 생성하는 메서드.
     *
     * @param loginUserId 사용자의 인증 토큰
     * @param productId 위시리스트에 추가할 상품의 ID
     * @return 생성된 WishDTO 객체
     * @throws WishlistNotFoundException 사용자를 찾지 못하거나, 상품을 찾지 못할 경우 예외 발생
     */
    public WishDTO createWish(Long loginUserId, Long productId) {
        User user = findUser(loginUserId);

        Product product = productMapper.toEntity(productService.getProductDTO(productId), true);

        Wishlist wishlist = new Wishlist(null, user, product);
        wishlistRepository.save(wishlist);

        return WishDTO.builder()
            .id(wishlist.getId())
            .product(WishProductDTO.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .imgurl(product.getProductImageUrl())
                .tag(product.getTypeName())
                .build())
            .build();
    }

    /**
     * 사용자의 위시리스트를 조회하는 메서드.
     *
     * @param loginUserId 사용자의 인증 토큰
     * @return 사용자의 모든 위시리스트 항목을 포함한 List<WishDTO>
     * @throws WishlistNotFoundException 사용자 ID를 추출할 수 없거나, 위시리스트 항목을 찾지 못할 경우 예외 발생
     */
    public List<WishDTO> getWishlist(Long loginUserId) {
        User user = findUser(loginUserId);
        List<Wishlist> wishlistItems = wishlistRepository.findByUserId(user.getId());

        return wishlistItems.stream()
            .map(wishlist -> WishDTO.builder()
                .id(wishlist.getId())
                .product(WishProductDTO.builder()
                    .id(wishlist.getProduct().getId())
                    .name(wishlist.getProduct().getProductName())
                    .price(wishlist.getProduct().getPrice())
                    .imgurl(wishlist.getProduct().getProductImageUrl())
                    .tag(wishlist.getProduct().getTypeName())
                    .build())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * 위시리스트 항목을 업데이트하는 메서드.
     *
     * @param loginUserId 사용자의 인증 토큰
     * @param wishId 업데이트할 위시리스트 항목의 ID
     * @param newProductId 새로 추가할 상품의 ID
     * @throws WishlistNotFoundException 위시리스트 항목 또는 새 상품을 찾지 못할 경우 예외 발생
     */
    @Transactional
    public void updateWish(Long loginUserId, Long wishId, Long newProductId) {
        User user = findUser(loginUserId);
        Wishlist existingWishlist = findWishlist(wishId, user.getId());

        Product newProduct = productMapper.toEntity(productService.getProductDTO(newProductId), true);

        existingWishlist.setProduct(newProduct);
    }

    /**
     * 위시리스트 항목을 삭제하는 메서드.
     *
     * @param loginUserId 사용자의 인증 토큰
     * @param wishId 삭제할 위시리스트 항목의 ID
     * @throws WishlistNotFoundException 위시리스트 항목을 찾지 못하거나 권한이 없을 경우 예외 발생
     */
    public void deleteWish(Long loginUserId, Long wishId) {
        User user = findUser(loginUserId);
        Wishlist wishlist = findWishlist(wishId, user.getId());

        wishlistRepository.delete(wishlist);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    private Wishlist findWishlist(Long wishId, Long userId) {
        return wishlistRepository.findByIdAndUserId(wishId, userId)
            .orElseThrow(() -> new WishlistNotFoundException("Wish not found or not authorized"));
    }
}