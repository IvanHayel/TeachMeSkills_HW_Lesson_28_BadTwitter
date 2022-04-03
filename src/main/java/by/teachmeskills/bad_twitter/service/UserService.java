package by.teachmeskills.bad_twitter.service;

import by.teachmeskills.bad_twitter.entity.user.Role;
import by.teachmeskills.bad_twitter.entity.user.User;
import lombok.Cleanup;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserService extends EntityService {
    private static final String FIND_ALL_USERS = "find-all-users";
    private static final String FIND_ALL_ROLES = "find-all-roles";
    private static final String FIND_USER_ROLES = "find-user-roles";
    private static final String FIND_USER_BY_ID = "find-user-by-id";
    private static final String FIND_USER_BY_LOGIN = "find-user-by-login";
    private static final String FIND_MAX_USER_ID = "find-max-user-id";
    private static final String SAVE_USER = "save-user";
    private static final String SAVE_ROLE = "save-role";
    private static final String UPDATE_USER = "update-user";
    private static final String DELETE_USER = "delete-user";
    private static final String CLEAR_USER_ROLES = "clear-user-roles";

    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    private UserService() {
    }

    public List<Role> findUserRoles(int userId) {
        List<Role> roles = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_USER_ROLES);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, userId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                int accessLevel = resultSet.getInt(ACCESS_LEVEL);
                roles.add(new Role(id, name, accessLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_USERS);
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String login = resultSet.getString(LOGIN);
                String password = resultSet.getString(PASSWORD);
                String name = resultSet.getString(NAME);
                String surname = resultSet.getString(SURNAME);
                List<Role> roles = findUserRoles(id);
                users.add(new User(id, login, password, name, surname, roles));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_ROLES);
        int nextUserId = Integer.MIN_VALUE;
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                int accessLevel = resultSet.getInt(ACCESS_LEVEL);
                roles.add(new Role(id, name, accessLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public User findUser(int id) {
        User user = null;
        String query = SCRIPT_MANAGER.getQuery(FIND_USER_BY_ID);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString(LOGIN);
                String password = resultSet.getString(PASSWORD);
                String name = resultSet.getString(NAME);
                String surname = resultSet.getString(SURNAME);
                List<Role> roles = findUserRoles(id);
                user = new User(id, login, password, name, surname, roles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findUser(String login) {
        User user = null;
        String query = SCRIPT_MANAGER.getQuery(FIND_USER_BY_LOGIN);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setString(FIRST_PARAMETER, login);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String password = resultSet.getString(PASSWORD);
                String name = resultSet.getString(NAME);
                String surname = resultSet.getString(SURNAME);
                List<Role> roles = findUserRoles(id);
                user = new User(id, login, password, name, surname, roles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveRole(int userId, Role role) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_ROLE);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, userId);
            preparedStatement.setInt(SECOND_PARAMETER, role.getId());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    public boolean saveRoles(int userId, List<Role> roles) {
        for (Role role : roles)
            if (!saveRole(userId, role))
                return false;
        return true;
    }

    public boolean saveUser(@NonNull User user) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_USER);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, user.getId());
            preparedStatement.setString(SECOND_PARAMETER, user.getLogin());
            preparedStatement.setString(THIRD_PARAMETER, user.getPassword());
            preparedStatement.setString(FOURTH_PARAMETER, user.getName());
            preparedStatement.setString(FIFTH_PARAMETER, user.getSurname());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    public User updateUser(@NonNull User user) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(UPDATE_USER);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setString(FIRST_PARAMETER, user.getLogin());
            preparedStatement.setString(SECOND_PARAMETER, user.getPassword());
            preparedStatement.setString(THIRD_PARAMETER, user.getName());
            preparedStatement.setString(FOURTH_PARAMETER, user.getSurname());
            preparedStatement.setInt(FIFTH_PARAMETER, user.getId());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState == SQL_STATEMENT_UPDATE_NOTHING ? null : user;
    }

    public User deleteUser(int id) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(DELETE_USER);
        User user = findUser(id);
        if (user == null) return null;
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, id);
            updateState = preparedStatement.executeUpdate();
            clearUserRoles(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState == SQL_STATEMENT_UPDATE_NOTHING ? null : user;
    }

    private void clearUserRoles(int userId) {
        String query = SCRIPT_MANAGER.getQuery(CLEAR_USER_ROLES);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNextUserId() {
        String query = SCRIPT_MANAGER.getQuery(FIND_MAX_USER_ID);
        int nextUserId = Integer.MIN_VALUE;
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next())
                nextUserId = resultSet.getInt(MAX_USER_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextUserId;
    }

    public List<Role> getCommonRoles() {
        List<Role> commonRoles = new ArrayList<>();
        List<Role> allRoles = findAllRoles();
        allRoles.stream()
                .filter(role -> role.getAccessLevel() <= 0)
                .forEach(commonRoles::add);
        return commonRoles;
    }

    public int getMaxUserAccessLevel(@NonNull User user) {
        List<Role> roles = user.getRoles();
        return roles.stream()
                .mapToInt(Role::getAccessLevel)
                .max()
                .orElse(0);
    }
}