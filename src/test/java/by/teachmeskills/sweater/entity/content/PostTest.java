package by.teachmeskills.sweater.entity.content;

import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    @Test
    void testConstructor1() {
        ReadOnlyUser owner = new ReadOnlyUser();
        Post actualPost = new Post(1, owner, "Not all who wander are lost",
                LocalDateTime.of(1, 1, 1, 1, 1));

        ReadOnlyUser expectedAuthor = actualPost.author;
        assertSame(expectedAuthor, actualPost.getAuthor());
        assertEquals("0001-January-01 01:01:00", actualPost.getTimestampFormatted());
        List<Comment> comments = actualPost.getComments();
        assertTrue(comments.isEmpty());
        assertEquals(comments, actualPost.getLikes());
        assertEquals(1, actualPost.getId());
        assertEquals("Not all who wander are lost", actualPost.getContent());
    }

    @Test
    void testConstructor2() {
        assertThrows(NullPointerException.class, () -> new Post(1, null, null, null));
    }

    @Test
    void testConstructor3() {
        ReadOnlyUser owner = new ReadOnlyUser();
        assertThrows(NullPointerException.class, () -> new Post(1, owner, null,
                LocalDateTime.of(1, 1, 1, 1, 1)));
    }

    @Test
    void testConstructor4() {
        assertThrows(NullPointerException.class, () -> new Post(1, new ReadOnlyUser(),
                "Not all who wander are lost", null));
    }

    @Test
    void testConstructor5() {
        ReadOnlyUser owner = new ReadOnlyUser();
        LocalDateTime timestamp = LocalDateTime.of(1, 1, 1, 1, 1);
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        Post actualPost = new Post(1, owner, "Not all who wander are lost", timestamp, comments, stringList);

        ReadOnlyUser expectedAuthor = actualPost.author;
        assertSame(expectedAuthor, actualPost.getAuthor());
        assertEquals("0001-January-01 01:01:00", actualPost.getTimestampFormatted());
        List<Comment> comments1 = actualPost.getComments();
        assertEquals(stringList, comments1);
        assertEquals(comments1, actualPost.getLikes());
        assertEquals(1, actualPost.getId());
        assertEquals("Not all who wander are lost", actualPost.getContent());
    }

    @Test
    void testConstructor6() {
        assertThrows(NullPointerException.class, () ->
                new Post(1, null, null, null, null, null));
    }

    @Test
    void testConstructor7() {
        ReadOnlyUser owner = new ReadOnlyUser();
        LocalDateTime timestamp = LocalDateTime.of(1, 1, 1, 1, 1);
        ArrayList<Comment> comments = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                new Post(1, owner, null, timestamp, comments, new ArrayList<>()));

    }

    @Test
    void testConstructor8() {
        ReadOnlyUser owner = new ReadOnlyUser();
        ArrayList<Comment> comments = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                new Post(1, owner, "Not all who wander are lost", null, comments, new ArrayList<>()));

    }

    @Test
    void testConstructor9() {
        Post actualPost = new Post();
        ArrayList<Comment> commentList = new ArrayList<>();
        actualPost.setComments(commentList);
        ArrayList<String> stringList = new ArrayList<>();
        actualPost.setLikes(stringList);
        List<Comment> comments = actualPost.getComments();
        assertSame(commentList, comments);
        List<String> likes = actualPost.getLikes();
        assertEquals(likes, comments);
        assertSame(stringList, likes);
        assertEquals(commentList, likes);
    }

    @Test
    void testConstructor10() {
        ReadOnlyUser owner = new ReadOnlyUser();
        Post actualPost = new Post(1, owner, "Not all who wander are lost", LocalDateTime.of(1, 1, 1, 1, 1));

        ReadOnlyUser expectedAuthor = actualPost.author;
        assertSame(expectedAuthor, actualPost.getAuthor());
        assertEquals("0001-January-01 01:01:00", actualPost.getTimestampFormatted());
        List<Comment> comments = actualPost.getComments();
        assertTrue(comments.isEmpty());
        assertEquals(comments, actualPost.getLikes());
        assertEquals(1, actualPost.getId());
        assertEquals("Not all who wander are lost", actualPost.getContent());
    }

    @Test
    void testConstructor11() {
        assertThrows(NullPointerException.class, () -> new Post(1, null, null, null));
    }

    @Test
    void testConstructor12() {
        ReadOnlyUser owner = new ReadOnlyUser();
        assertThrows(NullPointerException.class, () -> new Post(1, owner, null,
                LocalDateTime.of(1, 1, 1, 1, 1)));
    }

    @Test
    void testConstructor13() {
        assertThrows(NullPointerException.class, () ->
                new Post(1, new ReadOnlyUser(), "Not all who wander are lost", null));
    }

    @Test
    void testConstructor14() {
        ReadOnlyUser owner = new ReadOnlyUser();
        LocalDateTime timestamp = LocalDateTime.of(1, 1, 1, 1, 1);
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        Post actualPost = new Post(1, owner, "Not all who wander are lost", timestamp, comments, stringList);

        ReadOnlyUser expectedAuthor = actualPost.author;
        assertSame(expectedAuthor, actualPost.getAuthor());
        assertEquals("0001-January-01 01:01:00", actualPost.getTimestampFormatted());
        List<Comment> comments1 = actualPost.getComments();
        assertEquals(stringList, comments1);
        assertEquals(comments1, actualPost.getLikes());
        assertEquals(1, actualPost.getId());
        assertEquals("Not all who wander are lost", actualPost.getContent());
    }

    @Test
    void testConstructor15() {
        assertThrows(NullPointerException.class, () ->
                new Post(1, null, null, null, null, null));

    }

    @Test
    void testConstructor16() {
        ReadOnlyUser owner = new ReadOnlyUser();
        LocalDateTime timestamp = LocalDateTime.of(1, 1, 1, 1, 1);
        ArrayList<Comment> comments = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                new Post(1, owner, null, timestamp, comments, new ArrayList<>()));

    }

    @Test
    void testConstructor17() {
        ReadOnlyUser owner = new ReadOnlyUser();
        ArrayList<Comment> comments = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                new Post(1, owner, "Not all who wander are lost",
                        null, comments, new ArrayList<>()));

    }

    @Test
    void testCanEqual1() {
        assertFalse((new Post()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Post post = new Post();
        assertTrue(post.canEqual(new Post()));
    }

    @Test
    void testEquals() {
        assertNotEquals(new Post(), null);
        assertNotEquals(new Post(), "Different type to Post");
    }

    @Test
    void testEquals2() {
        Post post = new Post();
        assertEquals(post, post);
        int expectedHashCodeResult = post.hashCode();
        assertEquals(expectedHashCodeResult, post.hashCode());
    }

    @Test
    void testEquals3() {
        Post post = new Post();
        assertNotEquals(post, new Post());
    }

    @Test
    void testGetLikesCount1() {
        assertThrows(NullPointerException.class, () -> new Post().getLikesCount());
    }

    @Test
    void testGetLikesCount2() {
        Post post = new Post();
        post.setLikes(new ArrayList<>());
        assertEquals(0, post.getLikesCount());
    }
}