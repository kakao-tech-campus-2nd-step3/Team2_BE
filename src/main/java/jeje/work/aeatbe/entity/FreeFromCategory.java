package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
        name = "free_from_categories",
        indexes = {
                @Index(name = "idx_free_from_type", columnList = "free_from_type"),
        })
public class FreeFromCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "free_from_type", length = 25, nullable = false)
    private String freeFromType;
}
