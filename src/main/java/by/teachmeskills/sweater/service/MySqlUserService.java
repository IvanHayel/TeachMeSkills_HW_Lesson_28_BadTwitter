package by.teachmeskills.sweater.service;

import by.teachmeskills.sweater.entity.user.Role;
import by.teachmeskills.sweater.entity.user.User;
import by.teachmeskills.sweater.storage.mysql.MySqlDriverManager;
import by.teachmeskills.sweater.storage.mysql.MySqlScriptManager;
import lombok.Cleanup;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static by.teachmeskills.sweater.constant.mysql.MySqlSchemaConstants.*;
import static by.teachmeskills.sweater.constant.mysql.MySqlScriptNames.*;
import static by.teachmeskills.sweater.constant.mysql.MySqlStatementConstants.*;

public class MySqlUserService implements UserService {
    private static final MySqlDriverManager DRIVER_MANAGER = MySqlDriverManager.getInstance();
    private static final MySqlScriptManager SCRIPT_MANAGER = MySqlScriptManager.getInstance();

    private static MySqlUserService instance;

    public static MySqlUserService getInstance() {
        if (instance == null) instance = new MySqlUserService();
        return instance;
    }

    private MySqlUserService() {
    }

    @Override
    public List<Role> findUserRoles(int userId) {
        List<Role> roles = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_USER_ROLES_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, userId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int accessLevel = resultSet.getInt(COLUMN_ACCESS_LEVEL);
                roles.add(new Role(id, name, accessLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_USERS_SCRIPT_NAME);
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String login = resultSet.getString(COLUMN_LOGIN);
                String password = resultSet.getString(COLUMN_PASSWORD);
                String email = resultSet.getString(COLUMN_EMAIL);
                String name = resultSet.getString(COLUMN_NAME);
                String surname = resultSet.getString(COLUMN_SURNAME);
                List<Role> roles = findUserRoles(id);
                users.add(new User(id, login, password, email, name, surname, roles));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_ROLES_SCRIPT_NAME);
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int accessLevel = resultSet.getInt(COLUMN_ACCESS_LEVEL);
                roles.add(new Role(id, name, accessLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public User findUser(int id) {
        User user = null;
        String query = SCRIPT_MANAGER.getQuery(FIND_USER_BY_ID_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString(COLUMN_LOGIN);
                String password = resultSet.getString(COLUMN_PASSWORD);
                String email = resultSet.getString(COLUMN_EMAIL);
                String name = resultSet.getString(COLUMN_NAME);
                String surname = resultSet.getString(COLUMN_SURNAME);
                List<Role> roles = findUserRoles(id);
                user = new User(id, login, password, email, name, surname, roles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUser(@NonNull String login) {
        User user = null;
        String query = SCRIPT_MANAGER.getQuery(FIND_USER_BY_LOGIN_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setString(PREPARED_STATEMENT_FIRST_PARAMETER, login);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String password = resultSet.getString(COLUMN_PASSWORD);
                String name = resultSet.getString(COLUMN_NAME);
                String email = resultSet.getString(COLUMN_EMAIL);
                String surname = resultSet.getString(COLUMN_SURNAME);
                List<Role> roles = findUserRoles(id);
                user = new User(id, login, password, email, name, surname, roles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean saveRole(int userId, @NonNull Role role) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_ROLE_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, userId);
            preparedStatement.setInt(PREPARED_STATEMENT_SECOND_PARAMETER, role.getId());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    @Override
    public boolean saveRoles(int userId, @NonNull List<Role> roles) {
        for (Role role : roles)
            if (!saveRole(userId, role))
                return false;
        return true;
    }

    @Override
    public boolean saveUser(@NonNull User user) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_USER_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, user.getId());
            preparedStatement.setString(PREPARED_STATEMENT_SECOND_PARAMETER, user.getLogin());
            preparedStatement.setString(PREPARED_STATEMENT_THIRD_PARAMETER, user.getPassword());
            preparedStatement.setString(PREPARED_STATEMENT_FOURTH_PARAMETER, user.getEmail());
            preparedStatement.setString(PREPARED_STATEMENT_FIFTH_PARAMETER, user.getName());
            preparedStatement.setString(PREPARED_STATEMENT_SIXTH_PARAMETER, user.getSurname());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    @Override
    public User updateUser(@NonNull User user) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(UPDATE_USER_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setString(PREPARED_STATEMENT_FIRST_PARAMETER, user.getLogin());
            preparedStatement.setString(PREPARED_STATEMENT_SECOND_PARAMETER, user.getPassword());
            preparedStatement.setString(PREPARED_STATEMENT_THIRD_PARAMETER, user.getEmail());
            preparedStatement.setString(PREPARED_STATEMENT_FOURTH_PARAMETER, user.getName());
            preparedStatement.setString(PREPARED_STATEMENT_FIFTH_PARAMETER, user.getSurname());
            preparedStatement.setInt(PREPARED_STATEMENT_SIXTH_PARAMETER, user.getId());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState == SQL_STATEMENT_UPDATE_NOTHING ? null : user;
    }

    @Override
    public User deleteUser(int id) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(DELETE_USER_SCRIPT_NAME);
        User user = findUser(id);
        if (user == null) return null;
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, id);
            updateState = preparedStatement.executeUpdate();
            clearUserRoles(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState == SQL_STATEMENT_UPDATE_NOTHING ? null : user;
    }

    private void clearUserRoles(int userId) {
        String query = SCRIPT_MANAGER.getQuery(CLEAR_USER_ROLES_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNextUserId() {
        String query = SCRIPT_MANAGER.getQuery(FIND_MAX_USER_ID_SCRIPT_NAME);
        int nextUserId = Integer.MIN_VALUE;
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next())
                nextUserId = resultSet.getInt(COLUMN_MAX_USER_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextUserId;
    }

    @Override
    public List<Role> getCommonRoles() {
        List<Role> commonRoles = new ArrayList<>();
        List<Role> allRoles = findAllRoles();
        allRoles.stream()
                .filter(role -> role.getAccessLevel() <= 0)
                .forEach(commonRoles::add);
        return commonRoles;
    }

    @Override
    public int getMaxUserAccessLevel(@NonNull User user) {
        List<Role> roles = user.getRoles();
        return roles.stream()
                .mapToInt(Role::getAccessLevel)
                .max()
                .orElse(0);
    }
}