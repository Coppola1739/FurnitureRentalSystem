package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.UserDao;
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

/**
 * Controller for the login page.
 * 
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class LoginController {

	@FXML
	private Button createAccountButton;

	@FXML
	private Button submitButton;

	@FXML
	private Button alterUserButton;

	@FXML
	private TextField user;

	@FXML
	private TextField password;

	@FXML
	private void validateCredentials(ActionEvent event) throws IOException, SQLException {

		if (this.crossreferenceCredentials()) {
			Alert alert = new Alert(AlertType.INFORMATION, "Valid username and password");
			this.changeScene(event, "/MembersPage.fxml");
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Invalid username and password");
			alert.showAndWait();
		}

	}

	private boolean crossreferenceCredentials() throws SQLException {
		UserDao loginDao = new UserDao();
		return loginDao.authorizeUser(this.user.getText(), this.password.getText());
	}

	@FXML
	void navigateToCreateAccountPage(ActionEvent event) throws IOException {
		this.changeScene(event, "/Register.fxml");
	}

	@FXML
	void navigateToAlterUserPage(ActionEvent event) throws IOException {
		try {
			if (this.crossreferenceCredentials()) {
				this.changeScene(event, "/AlterUser.fxml");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}


	private void changeScene(ActionEvent event, String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(fxmlPath));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();
		//Todo: this code should not be here especially as we add more fxmls.
		// Should be generalized to change scene based on path alone
		if (fxmlPath.equals("/AlterUser.fxml")) {
			newStage.setTitle("Alter User");
			AlterUserController controller = loader.getController();
			controller.bind(this.user.getText(), this.password.getText());
		} else {
			newStage.setTitle("Register");
		}
		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();

		Stage stage = (Stage) this.createAccountButton.getScene().getWindow();

		stage.close();
	}
}