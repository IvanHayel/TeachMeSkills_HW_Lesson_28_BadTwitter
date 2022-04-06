package by.teachmeskills.sweater.entity.content;

import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Post extends Content {
    private List<Comment> comments;
    private List<String> likes;

    public Post(@NonNull int id, @NonNull ReadOnlyUser owner, @NonNull String content,
                @NonNull LocalDateTime timestamp, @NonNull List<Comment> comments, @NonNull List<String> likes) {
        super(id, owner, content, timestamp);
        this.comments = comments;
        this.likes = likes;
    }

    public Post(@NonNull int id, @NonNull ReadOnlyUser owner,
                @NonNull String content, @NonNull LocalDateTime timestamp) {
        this(id, owner, content, timestamp, new ArrayList<>(), new ArrayList<>());
    }

    public int getLikesCount() {
        return likes.size();
    }
}