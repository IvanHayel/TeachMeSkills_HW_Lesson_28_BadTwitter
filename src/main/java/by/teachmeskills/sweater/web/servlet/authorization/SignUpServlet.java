package by.teachmeskills.sweater.web.servlet.authorization;

import by.teachmeskills.sweater.email.EmailSender;
import by.teachmeskills.sweater.email.Mailers;
import by.teachmeskills.sweater.entity.user.Role;
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
import java.util.List;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "SignUpServlet", value = PATH_SIGN_UP)
public class SignUpServlet extends HttpServlet {
    private static final UserService USER_SERVICE = MySqlUserService.getInstance();

    private transient HttpSession session;

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (currentUser != null) resp.sendRedirect(PATH_HOME);
        else req.getRequestDispatcher(PAGE_PATH_SIGN_UP).forward(req, resp);
    }

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        @NonNull String login = req.getParameter(REQUEST_PARAMETER_LOGIN);
        @NonNull String password = req.getParameter(REQUEST_PARAMETER_PASSWORD);
        @NonNull String email = req.getParameter(REQUEST_PARAMETER_EMAIL);
        @NonNull String name = req.getParameter(REQUEST_PARAMETER_NAME);
        @NonNull String surname = req.getParameter(REQUEST_PARAMETER_SURNAME);
        if (USER_SERVICE.findUser(login) == null) {
            int id = USER_SERVICE.getNextUserId();
            List<Role> commonRoles = USER_SERVICE.getCommonRoles();
            User userToRegister = new User(id, login, password, email, name, surname, commonRoles);
            if (USER_SERVICE.saveUser(userToRegister) && USER_SERVICE.saveRoles(id, commonRoles)) {
                // TODO: how to implement this functionality?
                EmailSender emailSender = new EmailSender(Mailers.SWEATER_MAILER);
                emailSender.sendTextEmail(OWNER_EMAIL, String.format("%d New user %s registered", id, login),
                        userToRegister.toString());
                resp.sendRedirect(PATH_SIGN_IN);
            } else doGet(req, resp);
        } else doGet(req, resp);
    }
}