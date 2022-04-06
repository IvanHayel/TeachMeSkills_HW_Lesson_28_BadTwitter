package by.teachmeskills.sweater.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.*;

@WebServlet(name = "HomeServlet", value = "")
public class HomeServlet extends HttpServlet {
    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher homeDispatcher = req.getRequestDispatcher(PAGE_PATH_HOME);
        homeDispatcher.forward(req, resp);
    }
}