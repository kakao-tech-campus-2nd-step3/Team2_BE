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
    private String kakaoAccessToken;

    @Column
    private String kakaoRefreshToken;

    @Column
    private String jwtRefreshToken;

    @Builder
    public User(String kakaoId, String userName, String userImgUrl,
        String kakaoAccessToken, String kakaoRefreshToken, String jwtRefreshToken) {
        this.kakaoId = kakaoId;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }

    public void kakaoTokenUpdate(String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
    }

    public void updateJwtRefreshToken(String jwtRefreshToken) {
        this.jwtRefreshToken = jwtRefreshToken;
    }

    public void updateInfo(String userName, String userImgUrl){
        this.userName = userName;
        this.userImgUrl = userImgUrl;
    }

}