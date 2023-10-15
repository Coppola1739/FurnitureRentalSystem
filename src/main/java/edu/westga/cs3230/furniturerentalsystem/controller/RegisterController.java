package edu.westga.cs3230.furniturerentalsystem.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {

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
    void createAccount(ActionEvent event) {
    	  // Clearing the error text
        errorText.setText("");

        // Validating the inputs
        if (userTextField.getText().trim().isEmpty() ||
            passwordTextField.getText().trim().isEmpty() ||
            firstNameTextField.getText().trim().isEmpty() ||
            lastNameTextField.getText().trim().isEmpty() ||
            genderTextField.getText().trim().isEmpty() ||
            phoneTextField.getText().trim().isEmpty() ||
            streetAddressTextField.getText().trim().isEmpty() ||
            cityTextField.getText().trim().isEmpty() ||
            stateTextField.getText().trim().isEmpty() ||
            zipTextField.getText().trim().isEmpty() ||
            birthdatePicker.getValue() == null) {
            
            errorText.setText("All fields are required!");
            return;
        }

     
        // Creating PersonalInformation object
        PersonalInformation pInfo = PersonalInformation.builder()
                .firstName(firstNameTextField.getText().trim())
                .lastName(lastNameTextField.getText().trim())
                .registrationDate(new Date())
                .gender(genderComboBox.getValue())
                .phoneNumber(phoneTextField.getText().replaceAll("[^0-9]", "").trim())
                .birthday(java.sql.Date.valueOf(birthdatePicker.getValue()))
                .address(streetAddressTextField.getText().trim())
                .city(cityTextField.getText().trim())
                .state(stateComboBox.getValue())
                .zip(zipTextField.getText().trim())
                .build();

        
        try {
            
            UserDao login = new UserDao();
            login.registerUser(this.userTextField.getText().trim(),this.passwordTextField.getText().trim(),"customer", pInfo);

            errorText.setText("Account created successfully!");

        } catch (IllegalArgumentException ex) {
            errorText.setText(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        assert userTextField != null : "fx:id=\"userTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'Register.fxml'.";
        assert firstNameTextField != null : "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert genderTextField != null : "fx:id=\"genderTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert phoneTextField != null : "fx:id=\"phoneTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert lastNameTextField != null : "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert streetAddressTextField != null : "fx:id=\"streetAddressTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert cityTextField != null : "fx:id=\"cityTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert stateTextField != null : "fx:id=\"stateTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert zipTextField != null : "fx:id=\"zipTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'Register.fxml'.";
        assert birthdatePicker != null : "fx:id=\"birthdatePicker\" was not injected: check your FXML file 'Register.fxml'.";
        this.setListenersForFields();
        genderComboBox.getItems().addAll("Male", "Female");
        this.populateStateComboBox();
    }
    
    private void setListenersForFields() {
    	phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidPhoneNum(newValue)) {
                this.phoneTextField.setStyle("-fx-border-color: red;");
                this.invalidPhoneNumText.setVisible(true);
                this.createAccountButton.setDisable(true);
            } else {
                this.phoneTextField.setStyle("");
                this.invalidPhoneNumText.setVisible(false);
                this.createAccountButton.setDisable(false);
            }
    	});
    	
    	zipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidZipCode(newValue)) {
                this.invalidZipText.setVisible(true);
                this.zipTextField.setText(oldValue);
            }else {
                this.invalidZipText.setVisible(false);
                this.createAccountButton.setDisable(false);
            }
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
    	this.stateComboBox.setItems(FXCollections.observableArrayList(
                "AL", "AK", "AZ", "AR", "CA",
                "CO", "CT", "DE", "FL", "GA",
                "HI", "ID", "IL", "IN", "IA",
                "KS", "KY", "LA", "ME", "MD",
                "MA", "MI", "MN", "MS", "MO",
                "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH",
                "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT",
                "VA", "WA", "WV", "WI", "WY"));
    }
    
}
