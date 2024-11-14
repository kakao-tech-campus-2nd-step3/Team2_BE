package jeje.work.aeatbe.dto.product;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record ProductResponseDTO(
        Long id,
        String name,
        @Size(min = 0, message = "가격이 0원 이상이어야 합니다.")
        Long price,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String imgUrl,
        @Size(min = 0, max = 5, message = "평점이 0점이상, 5점이하여야 합니다.")
        Double rating,
        @URL(message = "유효하지 않는 URL 형식입니다.")
        String ProductUrl,
        String description,
        String[] freeFrom,
        String[] allergy,
        String tag
) {
}
