package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;

import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Member;

import edu.westga.cs3230.furniturerentalsystem.util.Constants;

import edu.westga.cs3230.furniturerentalsystem.util.EmployeeStringFormatter;
import edu.westga.cs3230.furniturerentalsystem.util.MemberStringFormatter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
        this.addListenerToAlterEmployeeButton();
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
    void editEmployee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Constants.ALTER_EMPLOYEE_FXML));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();

        AlterEmployeeController controller = loader.getController();
        controller.setLoggedInLabel(super.loggedInUser);

        controller.setSelectedUser(this.employeeListView.getSelectionModel().getSelectedItem());
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
        Stage stage = (Stage) this.editEmployeeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @FXML
    void navigateToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Constants.HOME_PAGE_FXML));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();

        SystemController controller = loader.getController();
        controller.setLoggedInLabel(super.loggedInUser);

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
        Stage stage = (Stage) this.homeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void registerEmployee(ActionEvent event) {
        try {
            this.navigateTo(event, Constants.REGISTER_FXML);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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

    private void addListenerToAlterEmployeeButton() {
        this.editEmployeeButton.setDisable(true);
        this.employeeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.editEmployeeButton.setDisable(newValue == null);
        });
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

        Stage stage = (Stage) this.registerEmployeeButton.getScene().getWindow();

        stage.close();
    }
}

