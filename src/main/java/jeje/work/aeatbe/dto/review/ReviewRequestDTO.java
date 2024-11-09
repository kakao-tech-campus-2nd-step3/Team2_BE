package jeje.work.aeatbe.dto.review;

public record ReviewRequestDTO(
        Long rate,
        String content,
        Long productId
) {
}
