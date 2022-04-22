package by.teachmeskills.sweater.entity.content;

import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContentTest {
    @Test
    void testConstructor1() {
        Content actualContent = new Content();
        ReadOnlyUser readOnlyUser = new ReadOnlyUser();
        actualContent.setAuthor(readOnlyUser);
        actualContent.setContent("Not all who wander are lost");
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualContent.setTimestamp(ofResult);
        assertSame(readOnlyUser, actualContent.getAuthor());
        assertEquals("Not all who wander are lost", actualContent.getContent());
        assertSame(ofResult, actualContent.getTimestamp());
    }

    @Test
    void testConstructor2() {
        ReadOnlyUser author = new ReadOnlyUser();
        Content actualContent = new Content(1, author, "Not all who wander are lost",
                LocalDateTime.of(1, 1, 1, 1, 1));

        ReadOnlyUser expectedAuthor = actualContent.author;
        assertSame(expectedAuthor, actualContent.getAuthor());
        assertEquals("0001-January-01 01:01:00", actualContent.getTimestampFormatted());
        assertEquals("Not all who wander are lost", actualContent.getContent());
        assertEquals(1, actualContent.getId());
    }

    @Test
    void testConstructor3() {
        assertThrows(NullPointerException.class, () ->
                new Content(1, null, null, null));
    }

    @Test
    void testConstructor4() {
        assertThrows(NullPointerException.class, () ->
                new Content(1, null, "Not all who wander are lost",
                        LocalDateTime.of(1, 1, 1, 1, 1)));

    }

    @Test
    void testConstructor5() {
        assertThrows(NullPointerException.class, () ->
                new Content(1, new ReadOnlyUser(), "Not all who wander are lost", null));

    }

    @Test
    void testConstructor6() {
        ReadOnlyUser author = new ReadOnlyUser();
        Content actualContent = new Content(0, author, "Not all who wander are lost",
                LocalDateTime.of(1, 1, 1, 0, 1));

        ReadOnlyUser expectedAuthor = actualContent.author;
        assertSame(expectedAuthor, actualContent.getAuthor());
        assertEquals("0001-January-01 00:01:00", actualContent.getTimestampFormatted());
        assertEquals("Not all who wander are lost", actualContent.getContent());
        assertEquals(0, actualContent.getId());
    }

    @Test
    void testGetTimestampFormatted1() {
        assertThrows(NullPointerException.class, () ->
                (new Content()).getTimestampFormatted());
    }

    @Test
    void testGetTimestampFormatted2() {
        ReadOnlyUser owner = new ReadOnlyUser();
        assertEquals("0001-January-01 01:01:00",
                (new Comment(1, owner, "Not all who wander are lost",
                        LocalDateTime.of(1, 1, 1, 1, 1))).getTimestampFormatted());
    }

    @Test
    void testCanEqual() {
        assertFalse((new Content()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Content content = new Content();
        assertTrue(content.canEqual(new Comment()));
    }

    @Test
    void testEquals() {
        assertNotEquals(new Content(), null);
        assertNotEquals(new Content(), "Different type to Content");
    }

    @Test
    void testEquals2() {
        Content content = new Content();
        assertEquals(content, content);
        int expectedHashCodeResult = content.hashCode();
        assertEquals(expectedHashCodeResult, content.hashCode());
    }

    @Test
    void testEquals3() {
        Content content = new Content();
        assertNotEquals(content, new Content());
    }

    @Test
    void testEquals4() {
        Content content = new Content();
        assertNotEquals(content, new Comment());
    }

    @Test
    void testEquals5() {
        Content content = new Content();
        Comment comment = mock(Comment.class);
        when(comment.canEqual((Object) any())).thenThrow(new NullPointerException("foo"));
        assertThrows(NullPointerException.class, () -> content.equals(comment));
    }
}