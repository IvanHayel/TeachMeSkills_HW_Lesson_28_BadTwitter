package by.teachmeskills.sweater.web.filter;

import by.teachmeskills.sweater.entity.user.User;
import by.teachmeskills.sweater.service.MySqlUserService;
import by.teachmeskills.sweater.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.SESSION_ATTRIBUTE_USER;
import static by.teachmeskills.sweater.constant.SweaterWebConstants.USER_ACCESS_LEVEL_ADMIN;

@WebFilter(filterName = "AdminFilter", servletNames = {"AdminPanelServlet", "UserDeleteServlet"})
public class AdminFilter extends HttpFilter {
    private static final UserService USER_SERVICE = MySqlUserService.getInstance();

    @Override
    @SneakyThrows
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (currentUser == null)
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else if (USER_SERVICE.getMaxUserAccessLevel(currentUser) < USER_ACCESS_LEVEL_ADMIN)
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        else
            chain.doFilter(req, res);
    }
}