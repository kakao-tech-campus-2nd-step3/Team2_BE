package jeje.work.aeatbe.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserAllergyTest {

    @Test
    @DisplayName("UserAllergy 객체 생성 검증")
    void testUserAllergyCreation() {
        User user = new User();
        AllergyCategory allergyCategory = new AllergyCategory();

        UserAllergy userAllergy = new UserAllergy(1, user, allergyCategory);

        assertEquals(1, userAllergy.getId());
        assertEquals(user, userAllergy.getUser());
        assertEquals(allergyCategory, userAllergy.getAllergy());
    }

    @Test
    @DisplayName("UserAllergy 기본 생성자 검증")
    void testUserAllergyNoArgsConstructor() {
        UserAllergy userAllergy = new UserAllergy();

        assertNotNull(userAllergy);
        assertNull(userAllergy.getUser());
        assertNull(userAllergy.getAllergy());
    }

    @Test
    @DisplayName("UserAllergy 객체 equals 검증")
    void testUserAllergyEquals() {
        User user1 = new User();
        User user2 = new User();
        AllergyCategory allergy1 = new AllergyCategory();
        AllergyCategory allergy2 = new AllergyCategory();

        UserAllergy userAllergy1 = new UserAllergy(1, user1, allergy1);
        UserAllergy userAllergy2 = new UserAllergy(1, user1, allergy1);
        UserAllergy userAllergy3 = new UserAllergy(2, user2, allergy2);

        assertEquals(userAllergy1, userAllergy2);
        assertNotEquals(userAllergy1, userAllergy3);
    }
}
