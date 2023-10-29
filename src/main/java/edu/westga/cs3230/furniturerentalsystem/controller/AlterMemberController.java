package edu.westga.cs3230.furniturerentalsystem.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AlterMemberController extends SystemController{

	
	private Member currMember;
	
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
    void alterUser(ActionEvent event) {
        // Clearing the error text
        this.errorText.setText("");

        // Creating PersonalInformation object
        PersonalInformation pInfo = PersonalInformation.builder().firstName(this.firstNameTextField.getText().trim())
                .lastName(this.lastNameTextField.getText().trim()).registrationDate(new Date())
                .gender(this.genderComboBox.getValue().trim())
                .phoneNumber(this.phoneTextField.getText().replaceAll("[^0-9]", "").trim())
                .birthday(java.sql.Date.valueOf(this.birthdatePicker.getValue()))
                .address(this.streetAddressTextField.getText().trim()).city(this.cityTextField.getText().trim())
                .state(this.stateComboBox.getValue().trim()).zip(this.zipTextField.getText().trim()).build();

        try {

            UserDao alterUser = new UserDao();
            alterUser.alterUser(this.userTextField.getText().trim(), this.passwordTextField.getText().trim(),
                    "customer", pInfo);

            this.errorText.setText("Account updated successfully!");

        } catch (Exception ex) {
            this.errorText.setText(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        assert this.userTextField != null
                : "fx:id=\"userTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert this.passwordTextField != null
                : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert this.alterUserButton != null
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

    public void bind(String username, String password) {
        this.userTextField.setText(username);
        this.passwordTextField.setText(password);
        MemberDao dao = new MemberDao();

    }

    private void populateAllFields(PersonalInformation pinfo) {
        this.firstNameTextField.setText(pinfo.getFirstName());
        this.lastNameTextField.setText(pinfo.getLastName());
        this.birthdatePicker.setValue(LocalDate.parse(pinfo.getBirthday().toString()));
        System.out.println(pinfo.getBirthday().toString());
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
                this.alterUserButton.setDisable(true);
            } else {
                this.phoneTextField.setStyle("");
                this.invalidPhoneNumText.setVisible(false);
                this.alterUserButton.setDisable(false);
            }
        });

        this.zipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.isValidZipCode(newValue)) {
                this.invalidZipText.setVisible(true);
                this.zipTextField.setText(oldValue);
            } else {
                this.invalidZipText.setVisible(false);
                this.alterUserButton.setDisable(false);
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
        String phoneRegex = "\\d{3}-\\d{3}-\\d{4}";
        return phoneNum.matches(phoneRegex);
    }
    
    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        this.alterUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
    }
    
    @FXML
    void updateBirthday(ActionEvent event) {

    }

    @FXML
    void updateCity(ActionEvent event) {

    }

    @FXML
    void updateFirstName(ActionEvent event) {

    }

    @FXML
    void updateGender(ActionEvent event) {

    }

    @FXML
    void updateLastName(ActionEvent event) {

    }

    @FXML
    void updatePassword(ActionEvent event) {

    }

    @FXML
    void updatePhoneNumber(ActionEvent event) {

    }

    @FXML
    void updateState(ActionEvent event) {

    }

    @FXML
    void updateStreetAddress(ActionEvent event) {

    }

    @FXML
    void updateUsername(ActionEvent event) {

    }
}
