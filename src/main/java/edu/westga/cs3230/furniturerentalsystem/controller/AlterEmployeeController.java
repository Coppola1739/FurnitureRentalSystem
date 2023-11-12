package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class AlterEmployeeController extends SystemController{
    private Employee currEmployee;
    private boolean validPhoneNum;
    private boolean validZipCode;
    @FXML
    private Label alterUserNameLabel;
    @FXML
    private Button backToAdminPageButton;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private TextField cityTextField;

    @FXML
    private Text errorText;

    @FXML
    private Label failedUpdateLabel;

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
    private TextField phoneTextField;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private TextField streetAddressTextField;

    @FXML
    private Label successfulUpdateLabel;

    @FXML
    private Button updateUserButton;

    @FXML
    private TextField zipTextField;

    @FXML
    void initialize(){
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

    public void setSelectedUser(Employee employee) {
        this.currEmployee = employee;
        this.populateAllFields(this.currEmployee.getPInfo());
    }
    private void setListenersForFields() {
        this.phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.isValidPhoneNum(newValue)) {
                this.phoneTextField.setStyle("-fx-border-color: red;");
                this.invalidPhoneNumText.setVisible(true);
                this.validPhoneNum = false;
                this.updateUserButton.setDisable(true);
            } else {
                this.phoneTextField.setStyle("");
                this.invalidPhoneNumText.setVisible(false);
                this.validPhoneNum = true;
                if (this.validZipCode) {
                    this.updateUserButton.setDisable(false);
                }
            }
        });
        this.zipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.isValidZipCode(newValue)) {
                this.zipTextField.setStyle("-fx-border-color: red;");
                this.invalidZipText.setVisible(true);
                this.validZipCode = false;
                this.updateUserButton.setDisable(true);
            } else {
                this.zipTextField.setStyle("");
                this.invalidZipText.setVisible(false);
                this.validZipCode = true;
                if (this.validPhoneNum) {
                    this.updateUserButton.setDisable(false);
                }
            }
        });
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

    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        this.alterUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
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

    @FXML
    void updateUserClick(ActionEvent event) throws SQLException {
        PersonalInformation pInfo = this.createPersonalInformation();
        try {
            EmployeeDao updateUser = new EmployeeDao();
            boolean successful = updateUser.alterEmployee(this.currEmployee.getEmployeeNum(), pInfo);

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
    void backToAdminPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Constants.ADMIN_PAGE_FXML));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();

        MemberController controller = loader.getController();
        controller.setLoggedInLabel(super.loggedInUser);

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
        Stage stage = (Stage) this.backToAdminPageButton.getScene().getWindow();
        stage.close();
    }
}
