package by.teachmeskills.sweater.entity.user;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadOnlyUserTest {
    @Test
    void testConstructor1() {
        assertNull((new ReadOnlyUser()).getFullName());
    }

    @Test
    void testConstructor2() {
        User user = new User();
        ReadOnlyUser actualReadOnlyUser = new ReadOnlyUser(user);
        assertNull(actualReadOnlyUser.getEmail());
        assertEquals(user, actualReadOnlyUser.getUser());
        assertEquals(0, actualReadOnlyUser.getId());
        assertEquals("null null", actualReadOnlyUser.getFullName());
    }

    @Test
    void testConstructor3() {
        assertThrows(NullPointerException.class, () -> new ReadOnlyUser(null));
    }

    @Test
    void testGetUser1() {
        User user = new User();
        assertNotNull((new ReadOnlyUser(user)).getUser());
        assertEquals((new ReadOnlyUser(user)).getUser(), user);
    }

    @Test
    void testGetUser2() {
        ReadOnlyUser readOnlyUser = new ReadOnlyUser();
        readOnlyUser.setUser(new User());
        User actualUser = readOnlyUser.getUser();
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getSurname());
        assertNull(actualUser.getRoles());
        assertNull(actualUser.getPassword());
        assertNull(actualUser.getName());
        assertNull(actualUser.getLogin());
        assertEquals(0, actualUser.getId());
    }

    @Test
    void testSetUser1() {
        ReadOnlyUser readOnlyUser = new ReadOnlyUser();
        User user = new User();
        readOnlyUser.setUser(user);
        assertNull(readOnlyUser.getEmail());
        assertEquals(user, readOnlyUser.getUser());
        assertEquals(0, readOnlyUser.getId());
        assertEquals("null null", readOnlyUser.getFullName());
    }

    @Test
    void testSetUser2() {
        assertThrows(NullPointerException.class, () -> (new ReadOnlyUser()).setUser(null));
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testGetId1() {
        assertThrows(NullPointerException.class, () -> (new ReadOnlyUser()).getId());
    }

    @Test
    void testGetId2() {
        ReadOnlyUser readOnlyUser = new ReadOnlyUser();
        readOnlyUser.setUser(new User());
        assertEquals(0, readOnlyUser.getId());
    }

    @Test
    void testGetEmail1() {
        assertThrows(NullPointerException.class, () -> (new ReadOnlyUser()).getEmail());
    }

    @Test
    void testGetEmail2() {
        ReadOnlyUser readOnlyUser = new ReadOnlyUser();
        readOnlyUser.setUser(new User());
        assertNull(readOnlyUser.getEmail());
    }

    @Test
    void testGetFullName() {
        assertNull((new ReadOnlyUser()).getFullName());
        assertEquals("null null", (new ReadOnlyUser(new User())).getFullName());
    }
}