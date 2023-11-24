package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.UserStatus;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterController extends SystemController {

	private boolean validPhoneNum;
	private boolean validZipCode;

	@FXML
	private ComboBox<String> genderComboBox;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Text invalidZipText;

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
	private ComboBox<String> stateComboBox;

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
	private Text invalidPhoneNumText;

	@FXML
	private DatePicker birthdatePicker;

	@FXML
	private Button backButton;
	@FXML
	private Label memberUserNameLabel;

	@FXML
	void createAccount(ActionEvent event) {
		this.errorText.setText("");

		if (this.userTextField.getText().trim().isEmpty() || this.passwordTextField.getText().trim().isEmpty()
				|| this.firstNameTextField.getText().trim().isEmpty()
				|| this.lastNameTextField.getText().trim().isEmpty() || this.genderComboBox.getValue().trim().isEmpty()
				|| this.phoneTextField.getText().trim().isEmpty()
				|| this.streetAddressTextField.getText().trim().isEmpty()
				|| this.cityTextField.getText().trim().isEmpty() || this.stateComboBox.getValue().trim().isEmpty()
				|| this.zipTextField.getText().trim().isEmpty() || this.birthdatePicker.getValue() == null
				|| !this.isValidZipCode(this.zipTextField.getText())) {

			this.errorText.setText("All fields are required!");
			return;
		}
		PersonalInformation pInfo = this.createPersonalInformation();
		try {

			UserDao login = new UserDao();
			login.registerUser(this.userTextField.getText().trim(), this.passwordTextField.getText().trim(),
					UserStatus.MEMBER, pInfo);

			this.errorText.setText("Account created successfully!");
			Alert alert = new Alert(AlertType.INFORMATION, "Account Created");
			alert.showAndWait();
			this.navigateTo(event, Constants.MEMBERS_PAGE_FXML);
		} catch (IllegalArgumentException ex) {
			this.errorText.setText(ex.getMessage());
		} catch (IOException e) {
			this.errorText.setText("Failed to add account.");
		}
	}

	private void navigateTo(ActionEvent event, String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(fxmlPath));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		SystemController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();

		Stage stage = (Stage) this.createAccountButton.getScene().getWindow();

		stage.close();
	}

	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		Employee employee = EmployeeDao.getEmployeeByUsername(username).get(0);
		this.memberUserNameLabel.textProperty()
				.set("Logged In: " + employee.getPInfo().getFirstName() + " " + employee.getPInfo().getLastName());
	}

	private PersonalInformation createPersonalInformation() {
		PersonalInformation pInfo = PersonalInformation.builder().firstName(this.firstNameTextField.getText().trim())
				.lastName(this.lastNameTextField.getText().trim()).registrationDate(new Date())
				.gender(this.genderComboBox.getValue())
				.phoneNumber(this.phoneTextField.getText().replaceAll("[^0-9]", "").trim())
				.birthday(java.sql.Date.valueOf(this.birthdatePicker.getValue()))
				.address(this.streetAddressTextField.getText().trim()).city(this.cityTextField.getText().trim())
				.state(this.stateComboBox.getValue()).zip(this.zipTextField.getText().trim()).build();
		return pInfo;
	}

	@FXML
	void goBack(ActionEvent event) {
		try {
			this.navigateTo(event, Constants.MEMBERS_PAGE_FXML);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		assert this.userTextField != null
				: "fx:id=\"userTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.passwordTextField != null
				: "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.createAccountButton != null
				: "fx:id=\"createAccountButton\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.firstNameTextField != null
				: "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.genderTextField != null
				: "fx:id=\"genderTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.phoneTextField != null
				: "fx:id=\"phoneTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.lastNameTextField != null
				: "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.streetAddressTextField != null
				: "fx:id=\"streetAddressTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.cityTextField != null
				: "fx:id=\"cityTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.stateTextField != null
				: "fx:id=\"stateTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.zipTextField != null
				: "fx:id=\"zipTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.birthdatePicker != null
				: "fx:id=\"birthdatePicker\" was not injected: check your FXML file 'Register.fxml'.";
		this.setListenersForFields();
		this.genderComboBox.getItems().addAll("Male", "Female");
		this.populateStateComboBox();
	}

	private void setListenersForFields() {
		this.setupPhoneTextField();

		this.setupZipTextField();
	}

	private void setupPhoneTextField() {
		this.phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {

			String filteredValue = newValue.replaceAll("[^\\d-]", "");
			if (!filteredValue.equals(newValue)) {
				this.phoneTextField.setText(filteredValue);
			}

			if (filteredValue.replaceAll("-", "").length() > 10) {
				filteredValue = oldValue;
				this.phoneTextField.setText(filteredValue);
			}

			this.validPhoneNum = this.isValidPhoneNum(filteredValue);
			this.phoneTextField.setStyle(this.validPhoneNum ? "" : "-fx-border-color: red;");
			this.invalidPhoneNumText.setVisible(!this.validPhoneNum);
			this.createAccountButton.setDisable(!(this.validPhoneNum && this.validZipCode));
		});
	}

	private void setupZipTextField() {
		this.zipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^\\d]", "");
			if (!filteredValue.equals(newValue)) {
				this.zipTextField.setText(filteredValue);
			}

			if (filteredValue.length() > 5) {
				filteredValue = filteredValue.substring(0, 5);
				this.zipTextField.setText(filteredValue);
			}

			this.validZipCode = this.isValidZipCode(filteredValue);
			this.zipTextField.setStyle(this.validZipCode ? "" : "-fx-border-color: red;");
			this.invalidZipText.setVisible(!this.validZipCode);
			this.createAccountButton.setDisable(!(this.validPhoneNum && this.validZipCode));
		});
	}

	private boolean isValidZipCode(String zipCode) {
		return zipCode.matches("\\d{5}");
	}

	private boolean isValidPhoneNum(String phoneNum) {
		String phoneRegex = "\\d{3}-\\d{3}-\\d{4}";
		return phoneNum.matches(phoneRegex);
	}

	private void populateStateComboBox() {
		this.stateComboBox.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
				"FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
				"MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
				"TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
	}

}
