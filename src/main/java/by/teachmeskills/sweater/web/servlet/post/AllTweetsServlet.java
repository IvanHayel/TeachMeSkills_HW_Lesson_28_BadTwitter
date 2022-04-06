package by.teachmeskills.sweater.web.servlet.post;

import by.teachmeskills.sweater.entity.content.Post;
import by.teachmeskills.sweater.service.MySqlPostService;
import by.teachmeskills.sweater.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "AllTweetsServlet", value = PATH_ALL_TWEETS)
public class AllTweetsServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<Post> allPosts = POST_SERVICE.findAllPosts();
        req.setAttribute(REQUEST_ATTRIBUTE_ALL_POSTS, allPosts);
        req.getRequestDispatcher(PAGE_PATH_ALL_TWEETS).forward(req, resp);
    }
}