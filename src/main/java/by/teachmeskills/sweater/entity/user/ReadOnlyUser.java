package by.teachmeskills.sweater.entity.user;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
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

    public int getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getFullName() {
        if (user == null) return null;
        return String.format("%s %s", user.getName(), user.getSurname());
    }

    @Override
    public String toString() {
        return getFullName();
    }
}