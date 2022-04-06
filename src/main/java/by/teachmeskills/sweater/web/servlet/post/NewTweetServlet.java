package by.teachmeskills.sweater.web.servlet.post;

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
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDateTime;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "NewTweetServlet", value = PATH_NEW_TWEET)
public class NewTweetServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    private transient HttpSession session;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        req.getRequestDispatcher(PAGE_PATH_NEW_TWEET).forward(req, resp);
    }

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        String content = req.getParameter(REQUEST_PARAMETER_POST_CONTENT);
        int postId = POST_SERVICE.getNextPostId();
        ReadOnlyUser postAuthor = new ReadOnlyUser(currentUser);
        Post tweet = new Post(postId, postAuthor, content, LocalDateTime.now());
        POST_SERVICE.savePost(tweet);
        resp.sendRedirect(PATH_MY_TWEETS);
    }
}