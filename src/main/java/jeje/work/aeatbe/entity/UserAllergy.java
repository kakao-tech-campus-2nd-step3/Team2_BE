package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users_allergies")
@EqualsAndHashCode(callSuper = false)
@Getter
public class UserAllergy extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergy_id", nullable = false)
    private AllergyCategory allergy;

    public void addUserAllergy(User user, AllergyCategory allergy) {
        this.user = user;
        this.allergy = allergy;
        user.getAllergies().add(this);
    }
}