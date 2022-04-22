package by.teachmeskills.sweater.entity.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoleTest {
    @Test
    void testConstructor() {
        Role actualRole = new Role();
        actualRole.setAccessLevel(1);
        actualRole.setName("Name");
        assertEquals(1, actualRole.getAccessLevel());
        assertEquals("Name", actualRole.getName());
    }

    @Test
    void testEquals1() {
        assertNotEquals(new Role(1, "Name", 1), null);
        assertNotEquals(new Role(1, "Name", 1), "Different type to Role");
    }

    @Test
    void testEquals2() {
        Role role = new Role(1, "Name", 1);
        assertEquals(role, role);
        int expectedHashCodeResult = role.hashCode();
        assertEquals(expectedHashCodeResult, role.hashCode());
    }

    @Test
    void testEquals3() {
        Role role = new Role(1, "Name", 1);
        Role role1 = new Role(1, "Name", 1);

        assertEquals(role, role1);
        int expectedHashCodeResult = role.hashCode();
        assertEquals(expectedHashCodeResult, role1.hashCode());
    }
}

