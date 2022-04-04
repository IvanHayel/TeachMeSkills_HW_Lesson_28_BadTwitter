package by.teachmeskills.bad_twitter.web.servlet.post;

import by.teachmeskills.bad_twitter.entity.content.Comment;
import by.teachmeskills.bad_twitter.entity.content.Post;
import by.teachmeskills.bad_twitter.entity.user.ReadOnlyUser;
import by.teachmeskills.bad_twitter.entity.user.User;
import by.teachmeskills.bad_twitter.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(name = "TweetServlet", urlPatterns = "/tweet/*")
public class TweetServlet extends HttpServlet {
    private static final PostService postService = PostService.getInstance();

    private static final String TWEET_PAGE_PATH = "/pages/tweet.jsp";
    private static final String POST_ID_PARAMETER = "post-id";
    private static final String LIKE_PARAMETER = "like";
    private static final String COMMENT_CONTENT_PARAMETER = "comment-content";
    private static final String POST_ATTRIBUTE = "post";
    private static final String USER_ATTRIBUTE = "user";

    private transient HttpSession session;
    private transient User currentUser;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedPostId = req.getParameter(POST_ID_PARAMETER);
        int postId = Integer.parseInt(unparsedPostId);
        @NonNull Post post = postService.findPost(postId);
        req.setAttribute(POST_ATTRIBUTE, post);
        req.getRequestDispatcher(TWEET_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        currentUser = (User) session.getAttribute(USER_ATTRIBUTE);
        @NonNull String unparsedPostId = req.getParameter(POST_ID_PARAMETER);
        int postId = Integer.parseInt(unparsedPostId);
        @NonNull Post post = postService.findPost(postId);
        String like = req.getParameter(LIKE_PARAMETER);
        String commentContent = req.getParameter(COMMENT_CONTENT_PARAMETER);
        if (like != null) postService.setLike(new ReadOnlyUser(currentUser), post);
        if (commentContent != null) {
            int commentId = postService.getNextCommentId(post);
            ReadOnlyUser owner = new ReadOnlyUser(currentUser);
            Comment comment = new Comment(commentId, owner, commentContent);
            postService.saveComment(post, comment);
        }
        doGet(req, resp);
    }
}