package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@Table(name = "users_free_from")
public class UserFreeFrom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_from_id", nullable = false)
    private FreeFromCategory freeFromCategory;

    @Builder
    public UserFreeFrom(User user, FreeFromCategory freeFromCategory) {
        this.user = user;
        this.freeFromCategory = freeFromCategory;
    }

    public void addUser(User user) {
        this.user = user;
    }

}
