package by.teachmeskills.sweater.entity.content;

import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    @Test
    void testConstructor1() {
        Comment actualComment = new Comment();
        assertNull(actualComment.getAuthor());
        assertNull(actualComment.getTimestamp());
        assertEquals(0, actualComment.getId());
        assertNull(actualComment.getContent());
    }

    @Test
    void testConstructor2() {
        ReadOnlyUser owner = new ReadOnlyUser();
        Comment actualComment = new Comment(1, owner, "Not all who wander are lost", LocalDateTime.of(1, 1, 1, 1, 1));

        ReadOnlyUser expectedAuthor = actualComment.author;
        assertSame(expectedAuthor, actualComment.getAuthor());
        assertEquals("0001-January-01 01:01:00", actualComment.getTimestampFormatted());
        assertEquals("Not all who wander are lost", actualComment.getContent());
        assertEquals(1, actualComment.getId());
    }

    @Test
    void testConstructor3() {
        assertThrows(NullPointerException.class, () ->
                new Comment(1, null, null, null));

    }

    @Test
    void testConstructor4() {
        ReadOnlyUser owner = new ReadOnlyUser();
        assertThrows(NullPointerException.class, () ->
                new Comment(1, owner, null,
                        LocalDateTime.of(1, 1, 1, 1, 1)));

    }

    @Test
    void testConstructor5() {
        assertThrows(NullPointerException.class, () ->
                new Comment(1, new ReadOnlyUser(), "Not all who wander are lost", null));

    }

    @Test
    void testCanEqual1() {
        assertFalse((new Comment()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Comment comment = new Comment();
        assertTrue(comment.canEqual(new Comment()));
    }

    @Test
    void testEquals1() {
        assertNotEquals(new Comment(), null);
        assertNotEquals(new Comment(), "Different type to Comment");
    }

    @Test
    void testEquals2() {
        Comment comment = new Comment();
        assertEquals(comment, comment);
        int expectedHashCodeResult = comment.hashCode();
        assertEquals(expectedHashCodeResult, comment.hashCode());
    }

    @Test
    void testEquals3() {
        Comment comment = new Comment();
        assertNotEquals(comment, new Comment());
    }
}