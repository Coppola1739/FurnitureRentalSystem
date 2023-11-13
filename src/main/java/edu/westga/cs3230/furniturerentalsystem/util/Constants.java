package edu.westga.cs3230.furniturerentalsystem.util;

/**
 * Constants class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class Constants {
    public static final String DB_USERNAME = "cs3230f23c";
    public static final String DB_PASSWORD = "qjvw6rTXAXCmmR7EUBU@";
    public static final String DB_URL = "160.10.217.6:3306";
    public static final String CONNECTION_STRING = String.format("jdbc:mysql://%s/%s?user=%s&password=%s", DB_URL, DB_USERNAME, DB_USERNAME, DB_PASSWORD);
    public static final String ALTER_USER_FXML = "/AlterUser.fxml";
    public static final String LOGIN_FXML = "/Login.fxml";
    public static final String MEMBERS_PAGE_FXML = "/MembersPage.fxml";
    public static final String FURNITURE_PAGE_FXML = "/Furniture.fxml";
    public static final String REGISTER_FXML = "/Register.fxml";
    public static final String REGISTER_EMPLOYEE_FXML = "/RegisterEmployee.fxml";
    public static final String HOME_PAGE_FXML = "/HomePage.fxml";
    public static final String ADMIN_PAGE_FXML = "/AdminPage.fxml";
    public static final String TRANSACTION_PAGE_FXML = "/Transaction.fxml";
    public static final String ALTER_EMPLOYEE_FXML = "/AlterEmployee.fxml";
    public static final String FAILED_SQL = "Try again later.";
    public static final String USERNAME_INUSE = "Username already used. Use another";
    public static final String CLOSING_QUOTATION = "'";
}
