package by.teachmeskills.bad_twitter.service;

import by.teachmeskills.bad_twitter.entity.content.Comment;
import by.teachmeskills.bad_twitter.entity.content.Post;
import by.teachmeskills.bad_twitter.entity.user.ReadOnlyUser;
import lombok.Cleanup;
import lombok.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class PostService extends EntityService {
    private static final UserService USER_SERVICE = UserService.getInstance();
    private static final String DELIMITER = ", ";

    private static final String FIND_ALL_POSTS = "find-all-posts";
    private static final String FIND_ALL_POST_COMMENTS = "find-all-post-comments";
    private static final String FIND_POST_BY_ID = "find-post-by-id";
    private static final String SAVE_POST = "save-post";
    private static final String SAVE_COMMENT = "save-comment";
    private static final String UPDATE_POST = "update-post";
    private static final String DELETE_POST = "delete-post";
    private static final String CLEAR_POST_COMMENTS = "clear-post-comments";
    private static final String FIND_MAX_POST_ID = "find-max-post-id";

    private static PostService instance;

    public static PostService getInstance() {
        if (instance == null) instance = new PostService();
        return instance;
    }

    private PostService() {
    }

    public List<Comment> findAllPostComments(int postId) {
        List<Comment> postComments = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_POST_COMMENTS);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, postId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                int ownerId = resultSet.getInt(OWNER_ID);
                String content = resultSet.getString(CONTENT);
                ReadOnlyUser owner = new ReadOnlyUser(USER_SERVICE.findUser(ownerId));
                postComments.add(new Comment(id, owner, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postComments;
    }

    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = SCRIPT_MANAGER.getQuery(FIND_ALL_POSTS);
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                int owner_id = resultSet.getInt(OWNER_ID);
                String content = resultSet.getString(CONTENT);
                ReadOnlyUser owner = new ReadOnlyUser(USER_SERVICE.findUser(owner_id));
                List<Comment> comments = findAllPostComments(id);
                String unparsedLikes = resultSet.getString(LIKES);
                List<String> likes = unparsedLikes == null || unparsedLikes.trim().isEmpty()
                        ? new ArrayList<>()
                        : Arrays.stream(unparsedLikes.split(DELIMITER)).collect(Collectors.toList());
                posts.add(new Post(id, owner, content, comments, likes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> findAllUserPosts(int userId) {
        List<Post> userPosts = new ArrayList<>();
        List<Post> allPosts = findAllPosts();
        allPosts.stream()
                .filter(post -> post.getOwner().getId() == userId)
                .forEach(userPosts::add);
        return userPosts;
    }

    public Post findPost(int id) {
        String query = SCRIPT_MANAGER.getQuery(FIND_POST_BY_ID);
        Post post = null;
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int ownerId = resultSet.getInt(OWNER_ID);
                String content = resultSet.getString(CONTENT);
                ReadOnlyUser owner = new ReadOnlyUser(USER_SERVICE.findUser(ownerId));
                List<Comment> comments = findAllPostComments(id);
                String unparsedLikes = resultSet.getString(LIKES);
                List<String> likes = unparsedLikes == null || unparsedLikes.trim().isEmpty()
                        ? new ArrayList<>()
                        : Arrays.stream(unparsedLikes.split(DELIMITER)).collect(Collectors.toList());
                post = new Post(id, owner, content, comments, likes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public boolean savePost(@NonNull Post post) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_POST);
        List<String> unparsedLikes = post.getLikes();
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            StringJoiner likes = new StringJoiner(DELIMITER);
            if (unparsedLikes != null && !unparsedLikes.isEmpty())
                unparsedLikes.forEach(like -> likes.add(like));
            preparedStatement.setInt(FIRST_PARAMETER, post.getId());
            preparedStatement.setInt(SECOND_PARAMETER, post.getOwner().getId());
            preparedStatement.setString(THIRD_PARAMETER, post.getContent());
            preparedStatement.setString(FOURTH_PARAMETER, likes.toString());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    public boolean saveComment(@NonNull Post post, @NonNull Comment comment) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(SAVE_COMMENT);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, comment.getId());
            preparedStatement.setInt(SECOND_PARAMETER, post.getId());
            preparedStatement.setInt(THIRD_PARAMETER, comment.getOwner().getId());
            preparedStatement.setString(FOURTH_PARAMETER, comment.getContent());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING;
    }

    public Post updatePost(@NonNull Post post) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(UPDATE_POST);
        List<String> unparsedLikes = post.getLikes();
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            StringJoiner likes = new StringJoiner(DELIMITER);
            if (unparsedLikes != null && !unparsedLikes.isEmpty())
                unparsedLikes.forEach(like -> likes.add(like));
            preparedStatement.setInt(FIRST_PARAMETER, post.getOwner().getId());
            preparedStatement.setString(SECOND_PARAMETER, post.getContent());
            preparedStatement.setString(THIRD_PARAMETER, likes.toString());
            preparedStatement.setInt(FOURTH_PARAMETER, post.getId());
            updateState = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING ? post : null;
    }

    public Post deletePost(int id) {
        int updateState = SQL_STATEMENT_UPDATE_NOTHING;
        String query = SCRIPT_MANAGER.getQuery(DELETE_POST);
        Post post = findPost(id);
        if (post == null) return null;
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, id);
            updateState = preparedStatement.executeUpdate();
            clearPostComments(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateState != SQL_STATEMENT_UPDATE_NOTHING ? post : null;
    }

    private void clearPostComments(int id) {
        String query = SCRIPT_MANAGER.getQuery(CLEAR_POST_COMMENTS);
        try {
            @Cleanup PreparedStatement preparedStatement = DRIVER_MANAGER.prepareStatement(query);
            preparedStatement.setInt(FIRST_PARAMETER, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNextPostId() {
        String query = SCRIPT_MANAGER.getQuery(FIND_MAX_POST_ID);
        int nextPostId = Integer.MIN_VALUE;
        try (
                Statement statement = DRIVER_MANAGER.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next())
                nextPostId = resultSet.getInt(MAX_POST_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextPostId;
    }

    public int getNextCommentId(@NonNull Post post) {
        return post.getComments().size() + 1;
    }

    public void setLike(@NonNull ReadOnlyUser user, @NonNull Post post) {
        List<String> likes = post.getLikes();
        String userLike = user.getFullName();
        boolean likedAlready = likes.stream().anyMatch(like -> like.equals(userLike));
        if (likedAlready) likes.remove(userLike);
        else likes.add(userLike);
        updatePost(post);
    }
}