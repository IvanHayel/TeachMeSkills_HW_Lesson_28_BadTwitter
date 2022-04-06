package by.teachmeskills.sweater.web.servlet.post;

import by.teachmeskills.sweater.entity.content.Comment;
import by.teachmeskills.sweater.entity.content.Post;
import by.teachmeskills.sweater.entity.user.ReadOnlyUser;
import by.teachmeskills.sweater.entity.user.User;
import by.teachmeskills.sweater.service.MySqlPostService;
import by.teachmeskills.sweater.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDateTime;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "TweetServlet", value="/tweet")
public class TweetServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedPostId = req.getParameter(REQUEST_PARAMETER_POST_ID);
        int postId = Integer.parseInt(unparsedPostId);
        @NonNull Post post = POST_SERVICE.findPost(postId);
        req.setAttribute(SESSION_ATTRIBUTE_POST, post);
        req.getRequestDispatcher(PAGE_PATH_TWEET).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        @NonNull String unparsedPostId = req.getParameter(REQUEST_PARAMETER_POST_ID);
        int postId = Integer.parseInt(unparsedPostId);
        @NonNull Post post = POST_SERVICE.findPost(postId);
        String like = req.getParameter(REQUEST_PARAMETER_LIKE);
        String commentContent = req.getParameter(REQUEST_PARAMETER_COMMENT_CONTENT);
        if (like != null) POST_SERVICE.likePost(new ReadOnlyUser(currentUser), post);
        if (commentContent != null) {
            int commentId = POST_SERVICE.getNextCommentId(post);
            ReadOnlyUser owner = new ReadOnlyUser(currentUser);
            Comment comment = new Comment(commentId, owner, commentContent, LocalDateTime.now());
            POST_SERVICE.saveComment(post, comment);
        }
        doGet(req, resp);
    }
}