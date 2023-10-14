package edu.westga.cs3230.furniturerentalsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import javafx.event.ActionEvent;
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
	private Button alterUserButton;

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

	@FXML
	void alterUser(ActionEvent event) {
		// Clearing the error text
		errorText.setText("");

		// Validating the inputs
		if (firstNameTextField.getText().trim().isEmpty() || lastNameTextField.getText().trim().isEmpty()
				|| genderTextField.getText().trim().isEmpty() || phoneTextField.getText().trim().isEmpty()
				|| streetAddressTextField.getText().trim().isEmpty() || cityTextField.getText().trim().isEmpty()
				|| stateTextField.getText().trim().isEmpty() || zipTextField.getText().trim().isEmpty()
				|| birthdatePicker.getValue() == null) {

			errorText.setText("All fields are required!");
			return;
		}

		// Creating PersonalInformation object
		PersonalInformation pInfo = new PersonalInformation(firstNameTextField.getText().trim(),
				lastNameTextField.getText().trim(), new Date(), genderTextField.getText().trim(),
				phoneTextField.getText().trim(), java.sql.Date.valueOf(birthdatePicker.getValue()),
				streetAddressTextField.getText().trim(), cityTextField.getText().trim(),
				stateTextField.getText().trim(), zipTextField.getText().trim());

		try {

			UserDao alterUser = new UserDao();
			alterUser.alterUser(this.userTextField.getText().trim(), this.passwordTextField.getText().trim(), "customer",
					pInfo);

			errorText.setText("Account updated successfully!");

		} catch (Exception ex) {
			errorText.setText(ex.getMessage());
		}
	}

	@FXML
	void initialize() {
		assert userTextField != null
				: "fx:id=\"userTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert passwordTextField != null
				: "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert alterUserButton != null
				: "fx:id=\"createAccountButton\" was not injected: check your FXML file 'Register.fxml'.";
		assert firstNameTextField != null
				: "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert genderTextField != null
				: "fx:id=\"genderTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert phoneTextField != null
				: "fx:id=\"phoneTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert lastNameTextField != null
				: "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert streetAddressTextField != null
				: "fx:id=\"streetAddressTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert cityTextField != null
				: "fx:id=\"cityTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert stateTextField != null
				: "fx:id=\"stateTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert zipTextField != null : "fx:id=\"zipTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'Register.fxml'.";
		assert birthdatePicker != null
				: "fx:id=\"birthdatePicker\" was not injected: check your FXML file 'Register.fxml'.";

	}
}
