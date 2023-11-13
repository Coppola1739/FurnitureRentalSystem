package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for the login page.
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class LoginController {

    @FXML
    private Button submitButton;

    @FXML
    private TextField user;

    @FXML
    private TextField password;

    @FXML
    private void validateCredentials(ActionEvent event) throws IOException, SQLException {

        try {
            if (this.crossreferenceCredentials()) {
                this.navigateTo(event, Constants.HOME_PAGE_FXML);
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Invalid username and password");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException exception) {
            Alert alert = new Alert(AlertType.ERROR, exception.getMessage());
            alert.showAndWait();
        }

    }

    private boolean crossreferenceCredentials() throws SQLException {
        UserDao loginDao = new UserDao();
        return loginDao.authorizeUser(this.user.getText(), this.password.getText());
    }

    private void navigateTo(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fxmlPath));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();

        SystemController controller = loader.getController();
        controller.setLoggedInLabel(this.user.getText());

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();

        Stage stage = (Stage) this.submitButton.getScene().getWindow();

        stage.close();
    }
}