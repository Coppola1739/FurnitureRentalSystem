package edu.westga.cs3230.furniturerentalsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AlterUserController {
	
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField userTextField;

	    @FXML
	    private PasswordField passwordTextField;

	    @FXML
	    private Button createAccountButton;

	    @FXML
	    private TextField firstNameTextField;

	    @FXML
	    private TextField genderTextField;

	    @FXML
	    private TextField phoneTextField;

	    @FXML
	    private TextField lastNameTextField;

	    @FXML
	    private TextField streetAddressTextField;

	    @FXML
	    private TextField cityTextField;

	    @FXML
	    private TextField stateTextField;

	    @FXML
	    private TextField zipTextField;

	    @FXML
	    private Text errorText;

	    @FXML
	    private DatePicker birthdatePicker;
	    
//	    private boolean crossreferenceCredentials() throws SQLException {
//			UserDao loginDao = new UserDao();
//			return loginDao.authorizeUser(this.user.getText(), this.password.getText());
//		}

}
