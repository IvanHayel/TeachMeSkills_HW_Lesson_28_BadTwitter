package by.teachmeskills.sweater.web.filter;

import by.teachmeskills.sweater.entity.content.Post;
import by.teachmeskills.sweater.entity.user.User;
import by.teachmeskills.sweater.service.MySqlPostService;
import by.teachmeskills.sweater.service.PostService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.SneakyThrows;

import static by.teachmeskills.sweater.constant.SweaterWebConstants.REQUEST_PARAMETER_POST_ID;
import static by.teachmeskills.sweater.constant.SweaterWebConstants.SESSION_ATTRIBUTE_USER;

@WebFilter(filterName = "TweetEditFilter", servletNames = {"TweetEditServlet", "TweetDeleteServlet"})
public class TweetUpdateFilter extends HttpFilter {
    private static final PostService POST_SERVICE = MySqlPostService.getInstance();

    @Override
    @SneakyThrows
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        @NonNull String unparsedPostId = req.getParameter(REQUEST_PARAMETER_POST_ID);
        int postId = Integer.parseInt(unparsedPostId);
        Post post = POST_SERVICE.findPost(postId);
        if (currentUser == null || post == null)
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else if (currentUser.getId() != post.getAuthor().getId())
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        else
            chain.doFilter(req, res);
    }
}