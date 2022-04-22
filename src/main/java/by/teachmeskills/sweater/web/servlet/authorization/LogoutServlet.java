package by.teachmeskills.sweater.web.servlet.authorization;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.PATH_HOME;
import static by.teachmeskills.sweater.constant.SweaterWebConstants.PATH_LOGOUT;

@WebServlet(name = "LogoutServlet", value = PATH_LOGOUT)
public class LogoutServlet extends HttpServlet {
    @Override
    @SneakyThrows(IOException.class)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(PATH_HOME);
    }
}