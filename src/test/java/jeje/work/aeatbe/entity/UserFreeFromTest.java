package jeje.work.aeatbe.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserFreeFromTest {

    @Test
    @DisplayName("UserFreeFrom 객체 생성 검증")
    void testUserFreeFromCreation() {
        User user = new User();
        FreeFromCategory freeFromCategory = new FreeFromCategory();

        UserFreeFrom userFreeFrom = new UserFreeFrom(1L, user, freeFromCategory);

        assertEquals(1, userFreeFrom.getId());
        assertEquals(user, userFreeFrom.getUser());
        assertEquals(freeFromCategory, userFreeFrom.getFreeFromCategory());
    }

    @Test
    @DisplayName("UserFreeFrom 기본 생성자 검증")
    void testUserFreeFromNoArgsConstructor() {
        UserFreeFrom userFreeFrom = new UserFreeFrom();

        assertNotNull(userFreeFrom);
        assertNull(userFreeFrom.getUser());
        assertNull(userFreeFrom.getFreeFromCategory());
    }

    @Test
    @DisplayName("UserFreeFrom 객체 equals 검증")
    void testUserFreeFromEquals() {
        User user1 = new User();
        User user2 = new User();
        FreeFromCategory freeFromCategory1 = new FreeFromCategory();
        FreeFromCategory freeFromCategory2 = new FreeFromCategory();

        UserFreeFrom userFreeFrom1 = new UserFreeFrom(1L, user1, freeFromCategory1);
        UserFreeFrom userFreeFrom2 = new UserFreeFrom(1L, user1, freeFromCategory1);
        UserFreeFrom userFreeFrom3 = new UserFreeFrom(2L, user2, freeFromCategory2);

        assertEquals(userFreeFrom1, userFreeFrom2);
        assertNotEquals(userFreeFrom1, userFreeFrom3);
    }
}
