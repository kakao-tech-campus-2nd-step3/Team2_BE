package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergy_categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllergyCategory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allergy_type", nullable = false, length = 25)
    private String allergyType;

}