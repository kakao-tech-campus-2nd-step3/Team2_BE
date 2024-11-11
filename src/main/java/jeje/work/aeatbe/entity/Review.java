package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
    name = "reviews",
    indexes = {
        @Index(name = "idx_user_id",columnList ="user_id"),
        @Index(name = "idx_product_id", columnList = "product_id")
    })
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(columnDefinition = "BIGINT DEFAULT 0", nullable = false)
    private Long rate = 0L;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @PrePersist
    private void prePersist() {
        if (this.rate == null) {
            this.rate = 0L;
        }
    }
}
