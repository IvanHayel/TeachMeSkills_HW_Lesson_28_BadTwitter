package by.teachmeskills.bad_twitter.service;


import by.teachmeskills.bad_twitter.util.MySqlDriverManager;
import by.teachmeskills.bad_twitter.util.MySqlScriptManager;

import java.io.Serializable;

public abstract class EntityService implements Serializable {
    protected static final long serialVersionUID = 1L;

    protected static final MySqlDriverManager DRIVER_MANAGER = MySqlDriverManager.getInstance();
    protected static final MySqlScriptManager SCRIPT_MANAGER = MySqlScriptManager.getInstance();

    protected static final Integer FIRST_PARAMETER = 1;
    protected static final Integer SECOND_PARAMETER = 2;
    protected static final Integer THIRD_PARAMETER = 3;
    protected static final Integer FOURTH_PARAMETER = 4;
    protected static final Integer FIFTH_PARAMETER = 5;
    protected static final Integer SIXTH_PARAMETER = 6;
    protected static final Integer SEVENTH_PARAMETER = 7;
    protected static final Integer EIGHTH_PARAMETER = 8;

    protected static final String ID = "id";
    protected static final String LOGIN = "login";
    protected static final String PASSWORD = "password";
    protected static final String NAME = "name";
    protected static final String SURNAME = "surname";
    protected static final String ACCESS_LEVEL = "access_level";
    protected static final String CONTENT = "content";
    protected static final String OWNER_ID = "owner_id";
    protected static final String LIKES = "likes";
    protected static final String MAX_USER_ID = "max_user_id";
    protected static final String MAX_POST_ID = "max_post_id";

    protected static final int SQL_STATEMENT_UPDATE_NOTHING = 0;
}