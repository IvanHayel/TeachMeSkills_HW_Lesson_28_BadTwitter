package by.teachmeskills.sweater.service;

import by.teachmeskills.sweater.entity.content.Comment;
import by.teachmeskills.sweater.entity.content.Post;
import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import by.teachmeskills.sweater.storage.mysql.MySqlDriverManager;
import by.teachmeskills.sweater.storage.mysql.MySqlScriptManager;
import lombok.Cleanup;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static by.teachmeskills.sweater.constant.mysql.MySqlSchemaConstants.*;
import static by.teachmeskills.sweater.constant.mysql.MySqlScriptNames.*;
import static by.teachmeskills.sweater.constant.mysql.MySqlStatementConstants.*;

public class MySqlPostService implements PostService {
    private static final UserService USER_SERVICE = MySqlUserService.getInstance();
    private static final MySqlDriverManager DRIVER_MANAGER = MySqlDriverManager.getInstance();
    private static final MySqlScriptManager SCRIPT_MANAGER = MySqlScriptManager.getInstance();

    private static MySqlPostService instance;

    public static MySqlPostService getInstance() {
        if (instance == null) instance = new MySqlPostService();
        return instance;
    }

    private MySqlPostService() {
    }

    @Override
    public List<Comment> findAllPostComments(int postId) {
        List<Comment> postComments = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_POST_COMMENTS_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, postId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                int authorId = resultSet.getInt(COLUMN_AUTHOR_ID);
                String content = resultSet.getString(COLUMN_CONTENT);
                String unparsedTimestamp = resultSet.getString(COLUMN_TIMESTAMP);
                LocalDateTime timestamp = LocalDateTime.parse(unparsedTimestamp, MY_SQL_TIMESTAMP_FORMATTER);
                ReadOnlyUser author = new ReadOnlyUser(USER_SERVICE.findUser(authorId));
                postComments.add(new Comment(id, author, content, timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postComments;
    }

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_POSTS_SCRIPT_NAME);
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                int authorId = resultSet.getInt(COLUMN_AUTHOR_ID);
                String content = resultSet.getString(COLUMN_CONTENT);
                ReadOnlyUser author = new ReadOnlyUser(USER_SERVICE.findUser(authorId));
                List<Comment> comments = findAllPostComments(id);
                String unparsedTimestamp = resultSet.getString(COLUMN_TIMESTAMP);
                String unparsedLikes = resultSet.getString(COLUMN_LIKES);
                LocalDateTime timestamp = LocalDateTime.parse(unparsedTimestamp, MY_SQL_TIMESTAMP_FORMATTER);
                List<String> likes = unparsedLikes == null || unparsedLikes.trim().isEmpty()
                        ? new ArrayList<>()
                        : Arrays.stream(unparsedLikes.split(BASIC_SEPARATOR)).collect(Collectors.toList());
                posts.add(new Post(id, author, content, timestamp, comments, likes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> findAllUserPosts(int userId) {
        List<Post> userPosts = new ArrayList<>();
        List<Post> allPosts = findAllPosts();
        allPosts.stream()
                .filter(post -> post.getAuthor().getId() == userId)
                .forEach(userPosts::add);
        return userPosts;
    }

    @Override
    public Post findPost(int id) {
        String query = SCRIPT_MANAGER.getQuery(FIND_POST_BY_ID_SCRIPT_NAME);
        Post post = null;
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int authorId = resultSet.getInt(COLUMN_AUTHOR_ID);
                String content = resultSet.getString(COLUMN_CONTENT);
                ReadOnlyUser owner = new ReadOnlyUser(USER_SERVICE.findUser(authorId));
                List<Comment> comments = findAllPostComments(id);
                String unparsedTimestamp = resultSet.getString(COLUMN_TIMESTAMP);
                String unparsedLikes = resultSet.getString(COLUMN_LIKES);
                LocalDateTime timestamp = LocalDateTime.parse(unparsedTimestamp, MY_SQL_TIMESTAMP_FORMATTER);
                List<String> likes = unparsedLikes == null || unparsedLikes.trim().isEmpty()
                        ? new ArrayList<>()
                        : Arrays.stream(unparsedLikes.split(BASIC_SEPARATOR)).collect(Collectors.toList());
                post = new Post(id, owner, content, timestamp, comments, likes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public boolean savePost(@NonNull Post post) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_POST_SCRIPT_NAME);
        List<String> unparsedLikes = post.getLikes();
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            StringJoiner likes = new StringJoiner(BASIC_SEPARATOR);
            if (unparsedLikes != null && !unparsedLikes.isEmpty())
                unparsedLikes.forEach(likes::add);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, post.getId());
            preparedStatement.setInt(PREPARED_STATEMENT_SECOND_PARAMETER, post.getAuthor().getId());
            preparedStatement.setString(PREPARED_STATEMENT_THIRD_PARAMETER, post.getContent());
            preparedStatement.setString(PREPARED_STATEMENT_FOURTH_PARAMETER, likes.toString());
            preparedStatement.setString(PREPARED_STATEMENT_FIFTH_PARAMETER,
                    post.getTimestamp().format(MY_SQL_TIMESTAMP_FORMATTER));
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    @Override
    public boolean saveComment(@NonNull Post post, @NonNull Comment comment) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_COMMENT_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, comment.getId());
            preparedStatement.setInt(PREPARED_STATEMENT_SECOND_PARAMETER, post.getId());
            preparedStatement.setInt(PREPARED_STATEMENT_THIRD_PARAMETER, comment.getAuthor().getId());
            preparedStatement.setString(PREPARED_STATEMENT_FOURTH_PARAMETER, comment.getContent());
            preparedStatement.setString(PREPARED_STATEMENT_FIFTH_PARAMETER,
                    post.getTimestamp().format(MY_SQL_TIMESTAMP_FORMATTER));
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    @Override
    public Post updatePost(@NonNull Post post) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(UPDATE_POST_SCRIPT_NAME);
        List<String> unparsedLikes = post.getLikes();
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            StringJoiner likes = new StringJoiner(BASIC_SEPARATOR);
            if (unparsedLikes != null && !unparsedLikes.isEmpty())
                unparsedLikes.forEach(likes::add);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, post.getAuthor().getId());
            preparedStatement.setString(PREPARED_STATEMENT_SECOND_PARAMETER, post.getContent());
            preparedStatement.setString(PREPARED_STATEMENT_THIRD_PARAMETER, likes.toString());
            preparedStatement.setString(PREPARED_STATEMENT_FOURTH_PARAMETER,
                    post.getTimestamp().format(MY_SQL_TIMESTAMP_FORMATTER));
            preparedStatement.setInt(PREPARED_STATEMENT_FIFTH_PARAMETER, post.getId());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING ? post : null;
    }

    @Override
    public Post deletePost(int id) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(DELETE_POST_SCRIPT_NAME);
        Post post = findPost(id);
        if (post == null) return null;
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, id);
            updateState = preparedStatement.executeUpdate();
            clearPostComments(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING ? post : null;
    }

    @Override
    public void deleteAllUserPosts(int userId) {
        findAllUserPosts(userId)
                .stream()
                .mapToInt(Post::getId)
                .forEach(this::deletePost);
    }

    private void clearPostComments(int id) {
        String query = SCRIPT_MANAGER.getQuery(CLEAR_POST_COMMENTS_SCRIPT_NAME);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(PREPARED_STATEMENT_FIRST_PARAMETER, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNextPostId() {
        String query = SCRIPT_MANAGER.getQuery(FIND_MAX_POST_ID_SCRIPT_NAME);
        int nextPostId = Integer.MIN_VALUE;
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next())
                nextPostId = resultSet.getInt(COLUMN_MAX_POST_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextPostId;
    }

    @Override
    public int getNextCommentId(@NonNull Post post) {
        return post.getComments().size() + 1;
    }

    @Override
    public void likePost(@NonNull ReadOnlyUser user, @NonNull Post post) {
        List<String> likes = post.getLikes();
        String userLike = user.getFullName();
        boolean likedAlready = likes.stream().anyMatch(like -> like.equals(userLike));
        if (likedAlready) likes.remove(userLike);
        else likes.add(userLike);
        updatePost(post);
    }
}