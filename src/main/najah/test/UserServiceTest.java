package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Tests")
public class UserServiceTest {

    UserService userService = new UserService();

    @Test
    @DisplayName("Valid email should return true")
    void testValidEmail() {
        boolean result = userService.isValidEmail("test@gmail.com");

        assertTrue(result);
        assertNotEquals(false, result);
    }

    @Test
    @DisplayName("Email without at symbol should return false")
    void testEmailWithoutAt() {
        boolean result = userService.isValidEmail("testgmail.com");

        assertFalse(result);
    }

    @Test
    @DisplayName("Email without dot should return false")
    void testEmailWithoutDot() {
        boolean result = userService.isValidEmail("test@gmailcom");

        assertFalse(result);
    }

    @Test
    @DisplayName("Null email should return false")
    void testNullEmail() {
        boolean result = userService.isValidEmail(null);

        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({
            "admin,1234,true",
            "admin,wrong,false",
            "user,1234,false",
            "user,wrong,false"
    })
    @DisplayName("Authentication test with multiple cases")
    void testAuthenticate(String username, String password, boolean expected) {
        boolean result = userService.authenticate(username, password);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Authentication success case")
    void testAuthenticationSuccess() {
        boolean result = userService.authenticate("admin", "1234");

        assertTrue(result);
    }

    @Test
    @DisplayName("Authentication failure case")
    void testAuthenticationFailure() {
        boolean result = userService.authenticate("admin", "wrong");

        assertFalse(result);
    }
}