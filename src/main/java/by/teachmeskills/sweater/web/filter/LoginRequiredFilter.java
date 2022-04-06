package by.teachmeskills.sweater.web.filter;

import by.teachmeskills.sweater.entity.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.SESSION_ATTRIBUTE_USER;

@WebFilter(filterName = "LoginRequiredFilter",
        servletNames = {"LogoutServlet", "MyTweetsServlet", "NewTweetServlet", "TweetServlet", "AllTweetsServlet",
        "TweetEditServlet", "TweetDeleteServlet", "AdminPanelServlet"})
public class LoginRequiredFilter extends HttpFilter {
    @Override
    @SneakyThrows({IOException.class, ServletException.class})
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (currentUser == null)
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        else
            chain.doFilter(req, res);
    }
}