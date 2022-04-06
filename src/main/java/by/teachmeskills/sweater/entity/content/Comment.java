package by.teachmeskills.sweater.entity.content;

import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Comment extends Content {
    public Comment(int id, @NonNull ReadOnlyUser owner,
                   @NonNull String content, @NonNull LocalDateTime publishDateAndTime) {
        super(id, owner, content, publishDateAndTime);
    }
}