package jeje.work.aeatbe.dto.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record WishProductDTO(
        Long id,
        @NotNull(message = "이름이 존재해야 합니다.")
        String name,
        Long price,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String imgurl,
        String tag
) {
}