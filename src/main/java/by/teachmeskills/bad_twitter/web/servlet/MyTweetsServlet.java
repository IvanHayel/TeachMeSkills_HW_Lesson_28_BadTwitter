package by.teachmeskills.bad_twitter.web.servlet;

import by.teachmeskills.bad_twitter.entity.content.Post;
import by.teachmeskills.bad_twitter.entity.user.User;
import by.teachmeskills.bad_twitter.service.PostService;
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

@WebServlet(name = "MyTweetsServlet", urlPatterns = "/my-tweets/*")
public class MyTweetsServlet extends HttpServlet {
    private static final PostService postService = PostService.getInstance();

    private static final String MY_TWEETS_PAGE_PATH = "/pages/my-tweets.jsp";
    private static final String POST_EDIT_PAGE_PATH = "/pages/post-edit.jsp";
    private static final String MY_TWEETS_PATH = "/my-tweets";
    private static final String POST_DELETE_PATH = "/my-tweets/delete";
    private static final String POST_EDIT_PATH = "/my-tweets/edit";
    private static final String USER_ATTRIBUTE = "user";
    private static final String POST_ATTRIBUTE = "post";
    private static final String USER_POSTS_ATTRIBUTE = "userPosts";
    private static final String POST_ID_PARAMETER = "post-id";
    private static final String POST_CONTENT_PARAMETER = "post-content";

    private transient HttpSession session;
    private transient User currentUser;

    @Override
    @SneakyThrows(IOException.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        currentUser = (User) session.getAttribute(USER_ATTRIBUTE);
        @NonNull String content = req.getParameter(POST_CONTENT_PARAMETER);
        @NonNull String unparsedId = req.getParameter(POST_ID_PARAMETER);
        int id = Integer.parseInt(unparsedId);
        Post post = postService.findPost(id);
        post.setContent(content);
        postService.updatePost(post);
        resp.sendRedirect(MY_TWEETS_PATH);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        currentUser = (User) session.getAttribute(USER_ATTRIBUTE);
        String action = req.getRequestURI();
        switch (action) {
            case POST_EDIT_PATH:
                editUserPost(req, resp);
                break;
            case POST_DELETE_PATH:
                deleteUserPost(req, resp);
                break;
            default:
                getListOfUserTweets(req, resp);
                break;
        }
    }

    @SneakyThrows({IOException.class, ServletException.class})
    private void editUserPost(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedPostId = req.getParameter(POST_ID_PARAMETER);
        int postId = Integer.parseInt(unparsedPostId);
        Post post = postService.findPost(postId);
        req.setAttribute(POST_ATTRIBUTE, post);
        req.getRequestDispatcher(POST_EDIT_PAGE_PATH).forward(req, resp);
    }

    @SneakyThrows(IOException.class)
    private void deleteUserPost(HttpServletRequest req, HttpServletResponse resp) {
        @NonNull String unparsedPostId = req.getParameter(POST_ID_PARAMETER);
        int postId = Integer.parseInt(unparsedPostId);
        postService.deletePost(postId);
        resp.sendRedirect(MY_TWEETS_PATH);
    }

    @SneakyThrows({IOException.class, ServletException.class})
    private void getListOfUserTweets(HttpServletRequest req, HttpServletResponse resp) {
        List<Post> allUserPosts = postService.findAllUserPosts(currentUser.getId());
        req.setAttribute(USER_POSTS_ATTRIBUTE, allUserPosts);
        req.getRequestDispatcher(MY_TWEETS_PAGE_PATH).forward(req, resp);
    }
}