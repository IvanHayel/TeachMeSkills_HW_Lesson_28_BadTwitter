package by.teachmeskills.bad_twitter.web.servlet.authorization;

import by.teachmeskills.bad_twitter.entity.user.User;
import by.teachmeskills.bad_twitter.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(name = "SignInServlet", value = "/sign-in")
public class SignInServlet extends HttpServlet {
    private static final UserService userService = UserService.getInstance();

    private static final String SIGN_IN_PAGE_PATH = "/pages/sign-in.jsp";
    private static final String HOME_PATH = "/";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ACCESS_LEVEL_ATTRIBUTE = "accessLevel";

    private transient HttpSession session;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        User currentUser = (User) session.getAttribute(USER_ATTRIBUTE);
        if (currentUser != null) resp.sendRedirect(HOME_PATH);
        else req.getRequestDispatcher(SIGN_IN_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        @NonNull String login = req.getParameter(LOGIN_PARAMETER);
        @NonNull String password = req.getParameter(PASSWORD_PARAMETER);
        User user = userService.findUser(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                session.setAttribute(USER_ATTRIBUTE, user);
                session.setAttribute(ACCESS_LEVEL_ATTRIBUTE, userService.getMaxUserAccessLevel(user));
            }
        }
        doGet(req, resp);
    }
}