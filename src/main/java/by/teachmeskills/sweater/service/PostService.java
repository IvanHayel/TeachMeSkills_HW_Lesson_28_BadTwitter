package by.teachmeskills.sweater.service;

import by.teachmeskills.sweater.entity.content.Comment;
import by.teachmeskills.sweater.entity.content.Post;
import by.teachmeskills.sweater.entity.user.ReadOnlyUser;

import java.util.List;

// TODO: lot's of methods can be moved to one main Interface
public interface PostService {
    List<Comment> findAllPostComments(int postId);

    List<Post> findAllPosts();

    List<Post> findAllUserPosts(int userId);

    Post findPost(int id);

    boolean savePost(Post post);

    boolean saveComment(Post post, Comment comment);

    Post updatePost(Post post);

    Post deletePost(int id);

    void deleteAllUserPosts(int id);

    int getNextPostId();

    int getNextCommentId(Post post);

    void likePost(ReadOnlyUser user, Post post);
}