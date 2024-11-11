package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "products_free_from",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "free_from_id"}))
public class ProductFreeFrom extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_from_id", nullable = false)
    private FreeFromCategory freeFromCategory;
}
