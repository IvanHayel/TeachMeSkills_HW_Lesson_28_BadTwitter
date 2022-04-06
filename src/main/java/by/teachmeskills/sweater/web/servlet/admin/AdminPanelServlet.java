package by.teachmeskills.sweater.web.servlet.admin;

import by.teachmeskills.sweater.entity.user.User;
import by.teachmeskills.sweater.service.MySqlUserService;
import by.teachmeskills.sweater.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "AdminPanelServlet", value = PATH_ADMIN_PANEL)
public class AdminPanelServlet extends HttpServlet {
    private static final UserService USER_SERVICE = MySqlUserService.getInstance();

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<User> allUsers = USER_SERVICE.findAllUsers();
        req.setAttribute(REQUEST_ATTRIBUTE_ALL_USERS, allUsers);
        req.getRequestDispatcher(PAGE_PATH_ADMIN_PANEL).forward(req, resp);
    }
}