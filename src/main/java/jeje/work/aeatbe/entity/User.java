package jeje.work.aeatbe.entity;

import jakarta.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy="user")
    private List<UserAllergy> allergies;

    @OneToMany(mappedBy="user")
    private List<UserFreeFrom> freeFroms;

    @Column(name = "user_name", length = 15)
    private String userName;

    @Column(name = "user_img_url", length = 255)
    private String userImgUrl;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Builder
    public User(String kakaoId, String userName,
        String userImgUrl, String accessToken, String refreshToken) {
        this.kakaoId = kakaoId;
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