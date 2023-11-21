package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.EmployeeStringFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private TableView<List<SimpleStringProperty>> sqlQueryTableView;

    @FXML
    private TextField sqlQueryTextField;

    @FXML
    private Button executeSqlQueryButton;

    @FXML
    void initialize() {
        this.employeeDao = new EmployeeDao();
        this.loadEmployeeListView(EmployeeDao.getAllEnabledEmployees());
        this.setListViewDoubleClickHandler();
        this.addListenerToAlterEmployeeButton();
        this.addListenerToDeleteEmployeeButton();
        this.initializeSqlQueryTableView();
    }

    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        Employee employee = EmployeeDao.getEmployeeByUsername(username).get(0);
        this.memberUserNameLabel.textProperty().set("Logged In: " + employee.getPInfo().getFirstName() + " " + employee.getPInfo().getLastName());
    }

    private void initializeSqlQueryTableView(){
        this.sqlQueryTableView.getColumns().clear();
    }

    @FXML
    void executeSqlQuery(ActionEvent event) {
        this.sqlQueryTableView.getColumns().clear();
        ObservableList<List<SimpleStringProperty>> data = FXCollections.observableArrayList();
        data.clear();
        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(this.sqlQueryTextField.getText())) {
            try (ResultSet rs = checkStmt.executeQuery()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    final int columnIndex = i - 1;
                    TableColumn<List<SimpleStringProperty>, String> column = new TableColumn<>(rs.getMetaData().getColumnName(i));
                    column.setCellValueFactory(cellData -> {
                        List<SimpleStringProperty> row = cellData.getValue();
                        return row != null && row.size() > columnIndex ? row.get(columnIndex) : null;
                    });
                    this.sqlQueryTableView.getColumns().add(column);
                }
                while (rs.next()) {
                    List<SimpleStringProperty> row = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(new SimpleStringProperty(rs.getString(i)));
                    }
                    data.add(row);
                }
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
            alert.showAndWait();
        }
        this.sqlQueryTableView.setItems(data);
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

    @FXML
    void clearEmployeeSearch(ActionEvent event) {
        this.employeeSearchTextField.setText("");
        this.loadEmployeeListView(EmployeeDao.getAllEnabledEmployees());
    }

    @FXML
    void deleteEmployee(ActionEvent event) throws SQLException {
        Employee employee = AdminController.this.employeeListView.getSelectionModel().getSelectedItem();
        this.employeeDao.disableEmployee(employee);
        this.loadEmployeeListView(EmployeeDao.getAllEnabledEmployees());
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
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Constants.LOGIN_FXML));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
        Stage stage = (Stage) this.logOutButton.getScene().getWindow();

        stage.close();
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
            this.navigateTo(event, Constants.REGISTER_EMPLOYEE_FXML);
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

    private void addListenerToDeleteEmployeeButton() {
        this.deleteEmployeeButton.setDisable(true);
        this.employeeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.deleteEmployeeButton.setDisable(newValue == null);
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