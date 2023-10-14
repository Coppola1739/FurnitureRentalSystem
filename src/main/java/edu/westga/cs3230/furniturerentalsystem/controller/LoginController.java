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
import javafx.scene.control.ButtonType;
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
	private void validCredentials(ActionEvent event) throws IOException, SQLException {

		if (this.crossreferenceCredentials()) {
			Alert alert = new Alert(AlertType.INFORMATION, "Valid username and password");
			alert.showAndWait();
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
		this.changeScene(event, "view/Register.fxml");
	}

	@FXML
	void navigateToAlterUserPage(ActionEvent event) throws IOException {
		try {
			if (this.crossreferenceCredentials()) {
				UserDao alterUserDao = new UserDao();
				//alterUserDao.(this.user.getText(), this.password.getText());
				this.changeScene(event, "view/AlterUser.fxml");
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
		Stage createAccountStage = new Stage();
		createAccountStage.setTitle("Register");
		createAccountStage.setScene(scene);
		createAccountStage.initModality(Modality.APPLICATION_MODAL);

		createAccountStage.show();

		Stage stage = (Stage) this.createAccountButton.getScene().getWindow();

		stage.close();
	}
}