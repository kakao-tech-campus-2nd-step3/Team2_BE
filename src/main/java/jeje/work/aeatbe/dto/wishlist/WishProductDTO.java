package jeje.work.aeatbe.dto.wishlist;

import lombok.Builder;

@Builder
public record WishProductDTO(
        Long id,
        String name,
        Long price,
        String imgurl,
        String tag
) {
}