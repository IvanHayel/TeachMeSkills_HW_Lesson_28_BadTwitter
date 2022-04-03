package by.teachmeskills.bad_twitter.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebFilter(urlPatterns = "*.jsp")
public class JspFilter extends HttpFilter {
    @Override
    @SneakyThrows(IOException.class)
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        res.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}