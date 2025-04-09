package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.core.RoleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void defaultConstructorGettersAndSetters() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRoleType());
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("12345");
        user.setRoleType(RoleType.ADMIN);
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("12345", user.getPassword());
        assertEquals(RoleType.ADMIN, user.getRoleType());
    }

    @Test
    void overloadedConstructor() {
        User user = new User(1L, "test", "12345", RoleType.ADMIN);
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("12345", user.getPassword());
        assertEquals(RoleType.ADMIN, user.getRoleType());
    }

    @Test
    void userToStringTest() {
        User user = new User(1L, "test", "12345", RoleType.ADMIN);
        assertEquals("User{id=1, username='test', password='12345', roleType=ADMIN}", user.toString());
    }


}