package jeje.work.aeatbe.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
            .kakaoId("12345")
            .userName("TestUser")
            .userImgUrl("http://example.com/user.jpg")
            .accessToken("initialAccessToken")
            .refreshToken("initialRefreshToken")
            .build();

    }

    @Test
    @DisplayName("User 객체 생성 테스트")
    void testUserCreation() {
        User newUser = User.builder()
            .kakaoId("12345")
            .userName("TestUser")
            .userImgUrl("http://example.com/user.jpg")
            .accessToken("initialAccessToken")
            .refreshToken("initialRefreshToken")
            .build();


        assertThat(newUser.getKakaoId()).isEqualTo("12345");
        assertThat(newUser.getAllergies()).isEqualTo("Peanut, Dairy");
        assertThat(newUser.getUserName()).isEqualTo("TestUser");
        assertThat(newUser.getUserImgUrl()).isEqualTo("http://example.com/user.jpg");
        assertThat(newUser.getAccessToken()).isEqualTo("initialAccessToken");
        assertThat(newUser.getRefreshToken()).isEqualTo("initialRefreshToken");
    }

    @Test
    @DisplayName("User 기본 생성자 검증")
    void testUserNoArgsConstructor() {
        User user = new User();

        assertNotNull(user);
        assertNull(user.getKakaoId());
        assertNull(user.getAllergies());
        assertNull(user.getUserName());
        assertNull(user.getUserImgUrl());
        assertNull(user.getAccessToken());
        assertNull(user.getRefreshToken());
    }

    @Test
    @DisplayName("카카오 토큰 업데이트 메서드 검증")
    void testKakaoTokenUpdate() {
        user.kakaoTokenUpdate("newAccessToken", "newRefreshToken");

        assertThat(user.getAccessToken()).isEqualTo("newAccessToken");
        assertThat(user.getRefreshToken()).isEqualTo("newRefreshToken");
    }
}