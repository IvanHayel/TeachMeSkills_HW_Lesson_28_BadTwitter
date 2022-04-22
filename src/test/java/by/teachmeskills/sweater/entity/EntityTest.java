package by.teachmeskills.sweater.entity;

import by.teachmeskills.sweater.entity.content.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityTest {
    @Test
    void testGetId() {
        assertEquals(0, (new Comment()).getId());
    }

    @Test
    void testSetId() {
        Comment comment = new Comment();
        comment.setId(1);
        assertEquals(1, comment.getId());
    }

    @Test
    void testToString() {
        assertEquals("Comment(super=Content(super=id=0, author=null, content=null, timestamp=null))",
                (new Comment()).toString());
    }
}