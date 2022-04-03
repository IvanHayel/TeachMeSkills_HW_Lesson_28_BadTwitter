package by.teachmeskills.bad_twitter.web.servlet;

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

@WebServlet(name = "TweetServlet", value = "/tweet")
public class TweetServlet extends HttpServlet {
    private static final PostService postService = PostService.getInstance();

    private static final String TWEET_PAGE_PATH = "/pages/tweet.jsp";
    private static final String MY_TWEETS_PATH = "/my-tweets";
    private static final String USER_ATTRIBUTE = "user";
    private static final String POST_CONTENT_PARAMETER = "post-content";

    private transient HttpSession session;
    private transient User currentUser;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        req.getRequestDispatcher(TWEET_PAGE_PATH).forward(req, resp);
    }

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        currentUser = (User) session.getAttribute(USER_ATTRIBUTE);
        @NonNull String content = req.getParameter(POST_CONTENT_PARAMETER);
        int postId = postService.getNextPostId();
        ReadOnlyUser postOwner = new ReadOnlyUser(currentUser);
        Post tweet = new Post(postId, postOwner, content);
        postService.savePost(tweet);
        resp.sendRedirect(MY_TWEETS_PATH);
    }
}