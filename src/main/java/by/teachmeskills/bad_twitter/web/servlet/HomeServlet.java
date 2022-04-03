package by.teachmeskills.bad_twitter.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "")
public class HomeServlet extends HttpServlet {
    private static final String HOME_PAGE_PATH = "/pages/home.jsp";

    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher homeDispatcher = req.getRequestDispatcher(HOME_PAGE_PATH);
        homeDispatcher.forward(req, resp);
    }
}