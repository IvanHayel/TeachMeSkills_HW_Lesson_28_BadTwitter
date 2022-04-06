package by.teachmeskills.sweater.entity.content;

import by.teachmeskills.sweater.entity.Entity;
import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Content extends Entity {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm:ss");

    protected ReadOnlyUser author;
    protected String content;
    protected LocalDateTime timestamp;

    public Content(int id, @NonNull ReadOnlyUser author,
                   @NonNull String content, @NonNull LocalDateTime timestamp) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getTimestampFormatted() {
        return timestamp.format(TIMESTAMP_FORMAT);
    }
}