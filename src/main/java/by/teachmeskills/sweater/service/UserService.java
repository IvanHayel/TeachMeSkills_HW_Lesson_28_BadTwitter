package by.teachmeskills.sweater.service;

import by.teachmeskills.sweater.entity.user.Role;
import by.teachmeskills.sweater.entity.user.User;

import java.util.List;

public interface UserService {
    List<Role> findUserRoles(int userId);

    List<User> findAllUsers();

    List<Role> findAllRoles();

    User findUser(int id);

    User findUser(String login);

    boolean saveRoles(int userId, List<Role> roles);

    boolean saveRole(int userId, Role role);

    boolean saveUser(User user);

    User updateUser(User user);

    User deleteUser(int id);

    int getNextUserId();

    List<Role> getCommonRoles();

    int getMaxUserAccessLevel(User user);
}