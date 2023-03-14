package se.accelerateit.signup6.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void testGravatarHash() {
        UserController uc = new UserController(null);
        assertEquals("ecec44dabecfcfe3c1c6525521121f0c", uc.gravatarHash("jan.grape@crisp.se"));
    }

}