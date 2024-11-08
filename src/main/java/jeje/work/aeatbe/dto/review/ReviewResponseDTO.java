package jeje.work.aeatbe.dto.review;

public record ReviewResponseDTO(
        Long id,
        Long Rate,
        String content,
        Long productId,
        Long productImgUrl
) {
}
