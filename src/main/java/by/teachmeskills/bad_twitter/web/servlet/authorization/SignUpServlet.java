package by.teachmeskills.bad_twitter.web.servlet.authorization;

import by.teachmeskills.bad_twitter.entity.user.Role;
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
import java.util.List;

@WebServlet(name = "SignUpServlet", value = "/sign-up")
public class SignUpServlet extends HttpServlet {
    private static final UserService userService = UserService.getInstance();

    private static final String SIGN_UP_PAGE_PATH = "/pages/sign-up.jsp";
    private static final String SIGN_IN_PATH = "/sign-in";
    private static final String HOME_PATH = "/";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String NAME_PARAMETER = "name";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String USER_ATTRIBUTE = "user";

    private transient HttpSession session;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        User currentUser = (User) session.getAttribute(USER_ATTRIBUTE);
        if (currentUser != null) resp.sendRedirect(HOME_PATH);
        else req.getRequestDispatcher(SIGN_UP_PAGE_PATH).forward(req, resp);
    }

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        @NonNull String login = req.getParameter(LOGIN_PARAMETER);
        @NonNull String password = req.getParameter(PASSWORD_PARAMETER);
        @NonNull String name = req.getParameter(NAME_PARAMETER);
        @NonNull String surname = req.getParameter(SURNAME_PARAMETER);
        if (userService.findUser(login) == null) {
            int id = userService.getNextUserId();
            List<Role> commonRoles = userService.getCommonRoles();
            User userToRegister = new User(id, login, password, name, surname, commonRoles);
            if (userService.saveUser(userToRegister) && userService.saveRoles(id, commonRoles)) {
                resp.sendRedirect(SIGN_IN_PATH);
            } else doGet(req, resp);
        } else doGet(req, resp);
    }
}