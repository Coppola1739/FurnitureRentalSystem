package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.util.EmployeeStringFormatter;
import edu.westga.cs3230.furniturerentalsystem.util.MemberStringFormatter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminController extends SystemController {

    @FXML
    private Button ClearSearchButton;

    @FXML
    private Label EmployeesListLabel;

    @FXML
    private Button SearchButton;

    @FXML
    private Label SearchListLabel;

    @FXML
    private Button deleteEmployeeButton;

    @FXML
    private Button editEmployeeButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ListView<Employee> employeeListView;

    @FXML
    private TextField employeeSearchTextField;

    @FXML
    private Label memberUserNameLabel;

    @FXML
    private Button registerEmployeeButton;

    private EmployeeDao employeeDao;

    @FXML
    void initialize() {
        this.employeeDao = new EmployeeDao();
        this.loadEmployeeListView(EmployeeDao.getAllEmployees());
        this.setListViewDoubleClickHandler();
        this.addListenerToAlterUserButton();
    }

    private void setListViewDoubleClickHandler() {
        this.employeeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Employee selectedMember = AdminController.this.employeeListView.getSelectionModel().getSelectedItem();
                    if (selectedMember != null) {
                        AdminController.this.showMemberDetailsPopup(selectedMember);
                    }
                }
            }
        });
    }

    private void showMemberDetailsPopup(Employee employee) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employee Details");
        alert.setHeaderText("Employee Number: " + employee.getEmployeeNum());

        TextArea textArea = new TextArea();
        EmployeeStringFormatter employeeStringFormatter = new EmployeeStringFormatter();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(employeeStringFormatter.formatEmployeeString(employee));

        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    private void loadEmployeeListView(ArrayList<Employee> employees) {
        this.employeeListView.setItems(FXCollections.observableArrayList(employees));
    }

    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        this.memberUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
    }

    @FXML
    void clearEmployeeSearch(ActionEvent event) {
        this.employeeSearchTextField.setText("");
        this.loadEmployeeListView(EmployeeDao.getAllEmployees());
    }

    @FXML
    void deleteEmployee(ActionEvent event) {

    }

    @FXML
    void editEmployee(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @FXML
    void navigateToHomePage(ActionEvent event) {

    }

    @FXML
    void registerEmployee(ActionEvent event) {

    }

    @FXML
    void searchForEmployees(ActionEvent event) {
        String searchText = this.employeeSearchTextField.getText();
        if (searchText.isEmpty()) {
            return;
        }
        ArrayList<Employee> employeesByEmployeeNumber = EmployeeDao.getEmployeesByEmployeeNumber(searchText);
        this.employeeListView.setItems(FXCollections.observableArrayList(employeesByEmployeeNumber));
    }
    private void addListenerToAlterUserButton() {
        this.editEmployeeButton.setDisable(true);
        this.employeeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.editEmployeeButton.setDisable(newValue == null);
        });
    }

}

