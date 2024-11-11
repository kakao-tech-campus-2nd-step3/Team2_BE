package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "products_allergies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "allergy_id"}))
public class ProductAllergy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergy_id", nullable = false)
    private AllergyCategory allergy;
}
