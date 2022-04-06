package by.teachmeskills.sweater.constant.mysql;

import java.time.format.DateTimeFormatter;

public class MySqlSchemaConstants {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_ACCESS_LEVEL = "access_level";
    public static final String COLUMN_MAX_USER_ID = "max_user_id";
    public static final String COLUMN_MAX_POST_ID = "max_post_id";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_LIKES = "likes";

    public static final String BASIC_SEPARATOR = ", ";
    public static final DateTimeFormatter MY_SQL_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private MySqlSchemaConstants() {
    }
}