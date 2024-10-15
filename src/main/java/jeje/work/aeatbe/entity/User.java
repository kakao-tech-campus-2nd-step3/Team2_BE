package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 100)
    private String userId;

    @Column(length = 100)
    private String allergies;

    @Column(name = "free_from", length = 100)
    private String freeFrom;

    @Column(name = "user_name", nullable = false, length = 15)
    private String userName;

    @Column(name = "user_img_url", length = 255)
    private String userImgUrl;

}