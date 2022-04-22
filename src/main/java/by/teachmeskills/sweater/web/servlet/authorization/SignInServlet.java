package by.teachmeskills.sweater.web.servlet.authorization;

import by.teachmeskills.sweater.entity.user.User;
import by.teachmeskills.sweater.service.MySqlUserService;
import by.teachmeskills.sweater.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "SignInServlet", value = PATH_SIGN_IN)
public class SignInServlet extends HttpServlet {
    private static final UserService USER_SERVICE = MySqlUserService.getInstance();

    private transient HttpSession session;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (currentUser != null) resp.sendRedirect(PATH_HOME);
        else req.getRequestDispatcher(PAGE_PATH_SIGN_IN).forward(req, resp);
    }

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        @NonNull String login = req.getParameter(REQUEST_PARAMETER_LOGIN);
        @NonNull String password = req.getParameter(REQUEST_PARAMETER_PASSWORD);
        User user = USER_SERVICE.findUser(login);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute(SESSION_ATTRIBUTE_USER, user);
            session.setAttribute(SESSION_ATTRIBUTE_ACCESS_LEVEL, USER_SERVICE.getMaxUserAccessLevel(user));
            resp.sendRedirect(PATH_HOME);
        } else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}