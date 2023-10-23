package edu.westga.cs3230.furniturerentalsystem.util;

public class Constants {
    public static final String DB_USERNAME = "cs3230f23c";
    public static final String DB_PASSWORD = "qjvw6rTXAXCmmR7EUBU@";
    public static final String DB_URL = "160.10.217.6:3306";
    public static final String CONNECTION_STRING = "jdbc:mysql://" + DB_URL + "/" + DB_USERNAME + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD;
    public static final String SEARCH_FILTER_PROMPT_TEXT = "Search Filter";
    public static final String ALTER_USER_FXML = "/AlterUser.fxml";
    public static final String LOGIN_FXML = "/Login.fxml";
    public static final String MEMBERS_PAGE_FXML = "/MembersPage.fxml";
    public static final String REGISTER_FXML = "/Register.fxml";

}
