package by.teachmeskills.bad_twitter.entity.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@EqualsAndHashCode
public class ReadOnlyUser {
    private User user;

    public ReadOnlyUser(@NonNull User user) {
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    public void setUser(@NonNull User user) {
        this.user = new User(user);
    }

    public String getFullName() {
        if(user == null) return null;
        return String.format("%s %s", user.getName(), user.getSurname());
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String toString() {
        return getFullName();
    }
}