package jeje.work.aeatbe.dto.wishlist;

import lombok.Builder;

@Builder
public record WishDTO(
        Long id,
        WishProductDTO product
) {
}