package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.model.Customer;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the login page.
 * 
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class LoginController {

	@FXML
	private Button createAccountButton;
	@FXML
	private TextField user;
	@FXML
	private TextField password;

	@FXML
	private void validCredentials(ActionEvent event) throws IOException, SQLException {

//		if (this.crossreferenceCredentials()) {
//			this.changeScene(this.user, Utilities.AppConstants.MAIN_GUI_FXML);
//		} else {
//			this.alertError(this.user);
//			this.alertError(this.password);
//		}

	}
//
	private boolean crossreferenceCredentials() throws SQLException {
		EmployeeDao employeeDao = new EmployeeDao();
		return employeeDao.authorizeEmployee(this.user.getText(), this.password.getText());
	}
//
	@FXML
	void navigateToCreateAccountPage(ActionEvent event) throws IOException {
//		this.changeScene(this.user, Utilities.AppConstants.CREATE_ACCOUNT_FXML);
	}
}