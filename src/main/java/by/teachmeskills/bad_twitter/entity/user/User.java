package by.teachmeskills.bad_twitter.entity.user;

import by.teachmeskills.bad_twitter.entity.Entity;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class User extends Entity {
    private String login;
    private String password;
    private String name;
    private String surname;
    private List<Role> roles;

    public User(int id, @NonNull String login, @NonNull String password,
                @NonNull String name, @NonNull String surname, @NonNull List<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }

    public User(@NonNull User user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.name = user.name;
        this.surname = user.surname;
        this.roles = user.roles;
    }
}