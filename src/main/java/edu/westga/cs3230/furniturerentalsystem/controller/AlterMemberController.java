package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AlterMemberController extends SystemController {

	private Member currUser;
	private boolean validPhoneNum;
	private boolean validZipCode;

	@FXML
	private Label failedUpdateLabel;

	@FXML
	private Label successfulUpdateLabel;

	@FXML
	private Label alterUserNameLabel;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Text invalidZipText;

	@FXML
	private URL location;

	@FXML
	private ComboBox<String> stateComboBox;

	@FXML
	private ComboBox<String> genderComboBox;

	@FXML
	private TextField userTextField;

	@FXML
	private Text invalidPhoneNumText;

	@FXML
	private PasswordField passwordTextField;

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
	private TextField zipTextField;

	@FXML
	private Text errorText;

	@FXML
	private DatePicker birthdatePicker;

	@FXML
	private Button updateUserButton;

	@FXML
	void initialize() {
		assert this.userTextField != null
				: "fx:id=\"userTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.passwordTextField != null
				: "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Register.fxml'.";
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
		assert this.zipTextField != null
				: "fx:id=\"zipTextField\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'Register.fxml'.";
		assert this.birthdatePicker != null
				: "fx:id=\"birthdatePicker\" was not injected: check your FXML file 'Register.fxml'.";
		this.setListenersForFields();
		this.genderComboBox.getItems().addAll("Male", "Female");
		this.populateStateComboBox();
		this.setAllFieldsToClearLabels();
		this.validPhoneNum = true;
		this.validZipCode = true;
	}

	@FXML
	private void setAllFieldsToClearLabels() {
		this.clearLabelsOnFocus(this.cityTextField);
		this.clearLabelsOnFocus(this.firstNameTextField);
		this.clearLabelsOnFocus(this.lastNameTextField);
		this.clearLabelsOnFocus(this.phoneTextField);
		this.clearLabelsOnFocus(this.streetAddressTextField);
		this.clearLabelsOnFocus(this.zipTextField);
	}

	private void populateAllFields(PersonalInformation pinfo) {
		this.firstNameTextField.setText(pinfo.getFirstName());
		this.lastNameTextField.setText(pinfo.getLastName());
		this.birthdatePicker.setValue(LocalDate.parse(pinfo.getBirthday().toString()));
		this.cityTextField.setText(pinfo.getCity());
		this.genderComboBox.setValue(pinfo.getGender());
		this.zipTextField.setText(pinfo.getZip());
		this.phoneTextField.setText(pinfo.getPhoneNumber());
		this.streetAddressTextField.setText(pinfo.getAddress());
		this.stateComboBox.setValue(pinfo.getState());
	}

	public void setSelectedUser(Member member) {
		this.currUser = member;
		this.populateAllFields(this.currUser.getPInfo());
	}

	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private void setListenersForFields() {
	    this.phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	        String filteredValue = newValue.replaceAll("[^\\d-]", "");
	        if (!filteredValue.equals(newValue)) {
	            this.phoneTextField.setText(filteredValue);
	        }

	        this.validPhoneNum = this.isValidPhoneNum(filteredValue);
	        this.phoneTextField.setStyle(this.validPhoneNum ? "" : "-fx-border-color: red;");
	        this.invalidPhoneNumText.setVisible(!this.validPhoneNum);
	        this.updateUserButton.setDisable(!(this.validPhoneNum && this.validZipCode));
	    });

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
	        this.updateUserButton.setDisable(!(this.validPhoneNum && this.validZipCode));
	    });
	}

	private boolean isValidZipCode(String zipCode) {
	    return zipCode.matches("\\d{5}");
	}

	private boolean isValidPhoneNum(String phoneNum) {
	    String phoneRegexFormatted = "\\d{3}-\\d{3}-\\d{4}";
	    String phoneRegexUnformatted = "^\\d{10}$";
	    return phoneNum.matches(phoneRegexUnformatted) || phoneNum.matches(phoneRegexFormatted);
	}

	private void populateStateComboBox() {
		this.stateComboBox.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
				"FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
				"MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
				"TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
	}

	
	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		Employee employee = EmployeeDao.getEmployeeByUsername(username).get(0);
		this.alterUserNameLabel.textProperty().set("Logged In: " + employee.getPInfo().getFirstName() + " " + employee.getPInfo().getLastName());
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
	void updateUserClick(ActionEvent event) throws SQLException {
		PersonalInformation pInfo = this.createPersonalInformation();
		try {
			UserDao updateUser = new UserDao();
			boolean successful = updateUser.alterMember(this.currUser.getMemberId(), pInfo);

			this.failedUpdateLabel.setVisible(!successful);
			this.successfulUpdateLabel.setVisible(successful);

		} catch (IllegalArgumentException ex) {
			this.errorText.setText(ex.getMessage());
		}
	}

	private void clearLabelsOnFocus(TextField textField) {
		textField.setOnMouseClicked(event -> {
			this.successfulUpdateLabel.setVisible(false);
			this.failedUpdateLabel.setVisible(false);
		});
	}

	@FXML
	void backToMemberPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.MEMBERS_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		MemberController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		Stage stage = (Stage) this.cityTextField.getScene().getWindow();
		stage.close();
	}
}
