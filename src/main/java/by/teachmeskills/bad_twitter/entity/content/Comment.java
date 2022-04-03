package by.teachmeskills.bad_twitter.entity.content;

import by.teachmeskills.bad_twitter.entity.Entity;
import by.teachmeskills.bad_twitter.entity.user.ReadOnlyUser;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Comment extends Entity {
    private ReadOnlyUser owner;
    private String content;

    public Comment(int id, @NonNull ReadOnlyUser owner, @NonNull String content) {
        this.id = id;
        this.owner = owner;
        this.content = content;
    }
}