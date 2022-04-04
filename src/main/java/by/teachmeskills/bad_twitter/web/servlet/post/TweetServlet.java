package by.teachmeskills.bad_twitter.web.servlet.post;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "TweetServlet", urlPatterns = "/tweet/*")
public class TweetServlet extends HttpServlet {
}