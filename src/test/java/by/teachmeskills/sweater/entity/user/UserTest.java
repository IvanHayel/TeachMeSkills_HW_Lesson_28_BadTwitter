package by.teachmeskills.sweater.entity.user;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testConstructor1() {
        User actualUser = new User();
        actualUser.setEmail("jane.doe@example.org");
        actualUser.setLogin("Login");
        actualUser.setName("Name");
        actualUser.setPassword("iloveyou");
        ArrayList<Role> roleList = new ArrayList<>();
        actualUser.setRoles(roleList);
        actualUser.setSurname("Doe");
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertEquals("Login", actualUser.getLogin());
        assertEquals("Name", actualUser.getName());
        assertEquals("iloveyou", actualUser.getPassword());
        assertSame(roleList, actualUser.getRoles());
        assertEquals("Doe", actualUser.getSurname());
    }

    @Test
    void testConstructor2() {
        User actualUser = new User(1, "Login", "iloveyou", "jane.doe@example.org",
                "Name", "Doe", new ArrayList<>());

        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertEquals("Doe", actualUser.getSurname());
        assertTrue(actualUser.getRoles().isEmpty());
        assertEquals("iloveyou", actualUser.getPassword());
        assertEquals("Name", actualUser.getName());
        assertEquals("Login", actualUser.getLogin());
        assertEquals(1, actualUser.getId());
    }

    @Test
    void testConstructor3() {
        assertThrows(NullPointerException.class,
                () -> new User(1, null, null, null, null, null, null));
    }

    @Test
    void testConstructor4() {
        assertThrows(NullPointerException.class,
                () -> new User(1, "Login", null, "jane.doe@example.org",
                        "Name", "Doe", new ArrayList<>()));
    }

    @Test
    void testConstructor5() {
        assertThrows(NullPointerException.class,
                () -> new User(1, "Login", "iloveyou", null,
                        "Name", "Doe", new ArrayList<>()));
    }

    @Test
    void testConstructor6() {
        assertThrows(NullPointerException.class,
                () -> new User(1, "Login", "iloveyou", "jane.doe@example.org",
                        null, "Doe", new ArrayList<>()));
    }

    @Test
    void testConstructor7() {
        assertThrows(NullPointerException.class,
                () -> new User(1, "Login", "iloveyou", "jane.doe@example.org",
                        "Name", null, new ArrayList<>()));
    }

    @Test
    void testConstructor8() {
        User actualUser = new User(new User());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getSurname());
        assertNull(actualUser.getRoles());
        assertNull(actualUser.getPassword());
        assertNull(actualUser.getName());
        assertNull(actualUser.getLogin());
        assertEquals(0, actualUser.getId());
    }

    @Test
    void testConstructor9() {
        assertThrows(NullPointerException.class,() -> new User(null));
    }

    @Test
    void testCanEqual1() {
        assertFalse((new User()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        User user = new User();
        assertTrue(user.canEqual(new User()));
    }

    @Test
    void testEquals1() {
        assertNotEquals(new User(), null);
        assertNotEquals(new User(), "Different type to User");
    }

    @Test
    void testEquals2() {
        User user = new User();
        assertEquals(user, user);
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user.hashCode());
    }

    @Test
    void testEquals3() {
        User user = new User();
        User user1 = new User();
        assertEquals(user, user1);
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user1.hashCode());
    }

    @Test
    void testEquals4() {
        User user = new User(1, "Login", "iloveyou", "jane.doe@example.org",
                "Name", "Doe", new ArrayList<>());
        assertNotEquals(user, new User());
    }

    @Test
    void testEquals5() {
        User user = new User();
        assertNotEquals(user, new User(1, "Login", "iloveyou", "jane.doe@example.org",
                "Name", "Doe", new ArrayList<>()));
    }

    @Test
    void testEquals6() {
        User user = new User();
        user.setPassword("iloveyou");
        assertNotEquals(user, new User());
    }

    @Test
    void testEquals7() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        assertNotEquals(user, new User());
    }

    @Test
    void testEquals8() {
        User user = new User();
        user.setName("Name");
        assertNotEquals(user, new User());
    }

    @Test
    void testEquals9() {
        User user = new User();
        user.setSurname("Doe");
        assertNotEquals(user, new User());
    }

    @Test
    void testEquals10() {
        User user = new User();
        user.setRoles(new ArrayList<>());
        assertNotEquals(user, new User());
    }

    @Test
    void testEquals11() {
        User user = new User(1, "Login", "iloveyou", "jane.doe@example.org",
                "Name", "Doe", new ArrayList<>());
        User user1 = new User(1, "Login", "iloveyou", "jane.doe@example.org",
                "Name", "Doe", new ArrayList<>());

        assertEquals(user, user1);
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user1.hashCode());
    }

    @Test
    void testEquals12() {
        User user = new User();

        User user1 = new User();
        user1.setPassword("iloveyou");
        assertNotEquals(user, user1);
    }

    @Test
    void testEquals13() {
        User user = new User();

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        assertNotEquals(user, user1);
    }

    @Test
    void testEquals14() {
        User user = new User();

        User user1 = new User();
        user1.setName("Name");
        assertNotEquals(user, user1);
    }

    @Test
    void testEquals15() {
        User user = new User();

        User user1 = new User();
        user1.setSurname("Doe");
        assertNotEquals(user, user1);
    }

    @Test
    void testEquals16() {
        User user = new User();

        User user1 = new User();
        user1.setRoles(new ArrayList<>());
        assertNotEquals(user, user1);
    }
}