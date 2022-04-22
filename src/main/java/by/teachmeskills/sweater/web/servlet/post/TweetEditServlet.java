package by.teachmeskills.sweater.web.servlet.post;

import by.teachmeskills.sweater.entity.content.Post;
import by.teachmeskills.sweater.service.MySqlPostService;
import by.teachmeskills.sweater.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDateTime;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "TweetEditServlet", value = PATH_EDIT_TWEET)
public class TweetEditServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String content = req.getParameter(REQUEST_PARAMETER_POST_CONTENT);
        @NonNull String unparsedPostId = req.getParameter(REQUEST_PARAMETER_POST_ID);
        int id = Integer.parseInt(unparsedPostId);
        @NonNull Post post = POST_SERVICE.findPost(id);
        post.setContent(content);
        post.setTimestamp(LocalDateTime.now());
        POST_SERVICE.updatePost(post);
        resp.sendRedirect(PATH_MY_TWEETS);
    }

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedPostId = req.getParameter(REQUEST_PARAMETER_POST_ID);
        int postId = Integer.parseInt(unparsedPostId);
        @NonNull Post post = POST_SERVICE.findPost(postId);
        req.setAttribute(SESSION_ATTRIBUTE_POST, post);
        req.getRequestDispatcher(PAGE_PATH_TWEET_EDIT).forward(req, resp);
    }
}