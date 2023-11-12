package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.UserStatus;
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
import java.util.Date;

public class RegisterEmployeeController extends SystemController{

    @FXML
    private Button backButton;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private TextField cityTextField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private Text invalidPhoneNumText;

    @FXML
    private Text invalidZipText;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Label memberUserNameLabel;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private TextField streetAddressTextField;

    @FXML
    private TextField userTextField;

    @FXML
    private TextField zipTextField;

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
            login.registerUser(this.userTextField.getText().trim(), this.passwordTextField.getText().trim(), UserStatus.EMPLOYEE,
                    pInfo);

            this.errorText.setText("Account created successfully!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account Created");
            alert.showAndWait();
            this.navigateTo(event, Constants.ADMIN_PAGE_FXML);
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
        this.memberUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
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
            this.navigateTo(event, Constants.ADMIN_PAGE_FXML);
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
    }

    private void setListenersForFields() {
        this.phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.isValidPhoneNum(newValue)) {
                this.phoneTextField.setStyle("-fx-border-color: red;");
                this.invalidPhoneNumText.setVisible(true);
                this.createAccountButton.setDisable(true);
            } else {
                this.phoneTextField.setStyle("");
                this.invalidPhoneNumText.setVisible(false);
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
        this.stateComboBox.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
                "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
                "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
                "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
    }


}
