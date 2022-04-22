package by.teachmeskills.sweater.web.servlet.post;

import by.teachmeskills.sweater.service.MySqlPostService;
import by.teachmeskills.sweater.service.PostService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "TweetDeleteServlet", value = PATH_DELETE_TWEET)
public class TweetDeleteServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedPostId = req.getParameter(REQUEST_PARAMETER_POST_ID);
        int id = Integer.parseInt(unparsedPostId);
        if (POST_SERVICE.deletePost(id) != null)
            resp.sendRedirect(PATH_MY_TWEETS);
        else
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}