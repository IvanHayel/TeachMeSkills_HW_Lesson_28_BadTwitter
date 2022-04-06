package by.teachmeskills.sweater.web.servlet.post;

import by.teachmeskills.sweater.entity.content.Post;
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
import java.util.List;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "MyTweetsServlet", value = PATH_MY_TWEETS)
public class MyTweetsServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        List<Post> allUserPosts = POST_SERVICE.findAllUserPosts(currentUser.getId());
        req.setAttribute(REQUEST_ATTRIBUTE_USER_POSTS, allUserPosts);
        req.getRequestDispatcher(PAGE_PATH_MY_TWEETS).forward(req, resp);
    }
}