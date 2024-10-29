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

    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId;

    @Column(length = 100)
    private String allergies;

    @Column(name = "free_from", length = 100)
    private String freeFrom;

    @Column(name = "user_name", length = 15)
    private String userName;

    @Column(name = "user_img_url", length = 255)
    private String userImgUrl;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Builder
    public User(String kakaoId, String allergies, String freeFrom,
        String userName, String userImgUrl, String accessToken, String refreshToken) {
        this.kakaoId = kakaoId;
        this.allergies = allergies;
        this.freeFrom = freeFrom;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void kakaoTokenUpdate(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}