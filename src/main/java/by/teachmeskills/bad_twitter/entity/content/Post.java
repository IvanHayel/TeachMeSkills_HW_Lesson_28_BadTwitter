package by.teachmeskills.bad_twitter.entity.content;

import by.teachmeskills.bad_twitter.entity.Entity;
import by.teachmeskills.bad_twitter.entity.user.ReadOnlyUser;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Post extends Entity {
    private ReadOnlyUser owner;
    private String content;
    private List<Comment> comments;
    private List<String> likes;

    public Post(@NonNull int id, @NonNull ReadOnlyUser owner, @NonNull String content,
                @NonNull List<Comment> comments, @NonNull List<String> likes) {
        this.id = id;
        this.owner = owner;
        this.content = content;
        this.comments = comments;
        this.likes = likes;
    }

    public Post(@NonNull int id, @NonNull ReadOnlyUser owner, @NonNull String content) {
        this(id, owner, content, new ArrayList<>(), new ArrayList<>());
    }

    public int getLikesCount() {
        return likes.size();
    }
}