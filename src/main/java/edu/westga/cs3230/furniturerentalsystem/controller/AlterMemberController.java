package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EditMemberDao;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlterMemberController extends SystemController {

	private Member currMember;
	private EditMemberDao editMemberDao;

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
	private TextField stateTextField;

	@FXML
	private TextField zipTextField;

	@FXML
	private Text errorText;

	@FXML
	private DatePicker birthdatePicker;

	@FXML
	private Button updateBirthdayButton;

	@FXML
	private Button updateCityButton;

	@FXML
	private Button updateFirstNameButton;

	@FXML
	private Button updateGenderButton;

	@FXML
	private Button updateLastNameButton;

	@FXML
	private Button updatePasswordButton;

	@FXML
	private Button updatePhoneNumberButton;

	@FXML
	private Button updateStateButton;

	@FXML
	private Button updateStreetAddressButton;

	@FXML
	private Button updateUsernameButton;

	@FXML
	private Button updateZipButton;

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
		this.editMemberDao = new EditMemberDao();
	}

	public void bind(String username, String password) {
		this.userTextField.setText(username);
		this.passwordTextField.setText(password);
		MemberDao dao = new MemberDao();

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
		this.currMember = member;
		this.populateAllFields(this.currMember.getPInfo());
	}

	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private void setListenersForFields() {
		this.phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!this.isValidPhoneNum(newValue)) {
				this.phoneTextField.setStyle("-fx-border-color: red;");
				this.invalidPhoneNumText.setVisible(true);
			} else {
				this.phoneTextField.setStyle("");
				this.invalidPhoneNumText.setVisible(false);
			}
		});

		this.zipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!this.isValidZipCode(newValue)) {
				this.invalidZipText.setVisible(true);
				this.zipTextField.setText(oldValue);
			} else {
				this.invalidZipText.setVisible(false);
			}
		});

	}

	private boolean isValidZipCode(String zipCode) {
		return zipCode.matches("\\d{5}");
	}

	private void populateStateComboBox() {
		this.stateComboBox.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
				"FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
				"MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
				"TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
	}

	private boolean isValidPhoneNum(String phoneNum) {
		boolean matches = false;
		String phoneRegexFormatted = "\\d{3}-\\d{3}-\\d{4}";
		String phoneRegexUnformatted = "^\\d{10}$";
		if (phoneNum.matches(phoneRegexUnformatted) || phoneNum.matches(phoneRegexFormatted)) {
			matches = true;
		}
		return matches;
	}

	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		this.alterUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
	}

	@FXML
	void updateBirthday(ActionEvent event) {
		this.editMemberDao.updateBirthday(this.birthdatePicker.getValue());
	}

	@FXML
	void updateCity(ActionEvent event) {
		this.editMemberDao.updateCity(this.cityTextField.getText());
	}

	@FXML
	void updateFirstName(ActionEvent event) {
		this.editMemberDao.updateFirstName(this.firstNameTextField.getText());
	}

	@FXML
	void updateGender(ActionEvent event) {
		this.editMemberDao.updateGender(this.genderComboBox.getValue());
	}

	@FXML
	void updateLastName(ActionEvent event) {
		this.editMemberDao.updateLastName(this.lastNameTextField.getText());
	}

	@FXML
	void updatePhoneNumber(ActionEvent event) {
		this.editMemberDao.updatePhoneNumber(this.phoneTextField.getText());
	}

	@FXML
	void updateState(ActionEvent event) {
		this.editMemberDao.updateState(this.stateComboBox.getValue());
	}

	@FXML
	void updateStreetAddress(ActionEvent event) {
		this.editMemberDao.updateStreetAddress(this.streetAddressTextField.getText());
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
