package by.teachmeskills.sweater.constant;

public class SweaterWebConstants {
    // TODO: Эндпоинты нет смысла выносить в константы. они определяющие и принадлежат сервлетам.
    public static final String PAGE_PATH_HOME = "/pages/home.jsp";
    public static final String PAGE_PATH_SIGN_IN = "/pages/sign-in.jsp";
    public static final String PAGE_PATH_SIGN_UP = "/pages/sign-up.jsp";
    public static final String PAGE_PATH_ALL_TWEETS = "/pages/all-tweets.jsp";
    public static final String PAGE_PATH_MY_TWEETS = "/pages/my-tweets.jsp";
    public static final String PAGE_PATH_TWEET_EDIT = "/pages/tweet-edit.jsp";
    public static final String PAGE_PATH_NEW_TWEET = "/pages/new-tweet.jsp";
    public static final String PAGE_PATH_TWEET = "/pages/tweet.jsp";
    public static final String PAGE_PATH_ADMIN_PANEL = "/pages/admin-panel.jsp";

    public static final String PATH_HOME = "/";
    public static final String PATH_SIGN_IN = "/sign-in";
    public static final String PATH_SIGN_UP = "/sign-up";
    public static final String PATH_TWEET = "/tweet";
    public static final String PATH_NEW_TWEET = "/new-tweet";
    public static final String PATH_ALL_TWEETS = "/all-tweets";
    public static final String PATH_MY_TWEETS = "/my-tweets";
    public static final String PATH_DELETE_TWEET = "/delete-tweet";
    public static final String PATH_DELETE_USER = "/delete-user";
    public static final String PATH_EDIT_TWEET = "/edit-tweet";
    public static final String PATH_ADMIN_PANEL = "/admin-panel";
    public static final String PATH_LOGOUT = "/logout";

    public static final String SESSION_ATTRIBUTE_USER = "user";
    public static final String SESSION_ATTRIBUTE_ACCESS_LEVEL = "accessLevel";
    public static final String SESSION_ATTRIBUTE_POST = "post";

    public static final String REQUEST_ATTRIBUTE_ALL_POSTS = "allPosts";
    public static final String REQUEST_ATTRIBUTE_USER_POSTS = "userPosts";
    public static final String REQUEST_ATTRIBUTE_ALL_USERS = "allUsers";

    // TODO: web constant should be line in web package
    public static final String REQUEST_PARAMETER_LOGIN = "login";
    public static final String REQUEST_PARAMETER_PASSWORD = "password";
    public static final String REQUEST_PARAMETER_NAME = "name";
    public static final String REQUEST_PARAMETER_SURNAME = "surname";
    public static final String REQUEST_PARAMETER_POST_ID = "post-id";
    public static final String REQUEST_PARAMETER_USER_ID = "user-id";
    public static final String REQUEST_PARAMETER_POST_CONTENT = "post-content";
    public static final String REQUEST_PARAMETER_COMMENT_CONTENT = "comment-content";
    public static final String REQUEST_PARAMETER_LIKE = "like";
    public static final String REQUEST_PARAMETER_EMAIL = "email";

    public static final int USER_ACCESS_LEVEL_ADMIN = 1;

    public static final String OWNER_EMAIL = "tms.hayel@gmail.com";

    private SweaterWebConstants() {
    }
}