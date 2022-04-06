package by.teachmeskills.sweater.web.servlet.admin;

import by.teachmeskills.sweater.service.MySqlPostService;
import by.teachmeskills.sweater.service.MySqlUserService;
import by.teachmeskills.sweater.service.PostService;
import by.teachmeskills.sweater.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "UserDeleteServlet", value = PATH_DELETE_USER)
public class UserDeleteServlet extends HttpServlet {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();
    private static final UserService USER_SERVICE = MySqlUserService.getInstance();

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedUserId = req.getParameter(REQUEST_PARAMETER_USER_ID);
        int id = Integer.parseInt(unparsedUserId);
        if (USER_SERVICE.deleteUser(id) != null) {
            POST_SERVICE.deleteAllUserPosts(id);
            resp.sendRedirect(PATH_ADMIN_PANEL);
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}