package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.dao.ReturnDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Controller for home page
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class HomeController extends SystemController {

	@FXML
	private Button furnitureNavigationButton;

	@FXML
	private Label homeUserNameLabel;

	@FXML
	private Button membersNavigationButton;
	@FXML
	private Button adminNavigationButton;

	@FXML
	private Button transactionsNavigationButton;

	@FXML
	private Label rentalsLabel;

	@FXML
	private Label moneyRentalsLabel;

	@FXML
	private Label returnsLabel;

	@FXML
	private Label amountReturnsLabel;
	
	@FXML
    private Button logoutButton;

    @FXML
    void logout(ActionEvent event) {
    	try {
			this.changeWindow(Constants.LOGIN_FXML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) this.logoutButton.getScene().getWindow();

		stage.close();
	}

	private void changeWindow(String window) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(window));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
	}

	/**
	 * Sets the logged in label of the view
	 *
	 * @param username the username to set
	 */
	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		Employee employee = EmployeeDao.getEmployeeByUsername(username).get(0);
		this.adminNavigationButton.setVisible(employee.getRole().equals("manager"));
		this.homeUserNameLabel.textProperty()
				.set("Logged In: " + employee.getPInfo().getFirstName() + " " + employee.getPInfo().getLastName());
		RentalDao rentalDao = new RentalDao();
		double[] rentalInfo = rentalDao.getEmployeeRentalCountAndAmount(username);
		this.rentalsLabel.textProperty().set((int) rentalInfo[0] + "");
		this.moneyRentalsLabel.textProperty().set(this.formatAmountAsDollar(rentalInfo[1]));
		this.returnsLabel.textProperty().set(ReturnDao.getEmployeeReturnCount(username) + "");
		this.amountReturnsLabel.textProperty()
				.set(this.formatAmountAsDollar(ReturnDao.getEmployeeTotalFines(username)));
	}

	private String formatAmountAsDollar(double amount) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
		return currencyFormat.format(amount);
	}

	@FXML
	void navigateToAdminPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.ADMIN_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		SystemController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		Stage stage = (Stage) this.membersNavigationButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void navigateToMembersPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.MEMBERS_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		SystemController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		Stage stage = (Stage) this.membersNavigationButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void navigateToFurniturePage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.FURNITURE_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		SystemController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		Stage stage = (Stage) this.membersNavigationButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void navigateToTransactionsPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.TRANSACTION_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		SystemController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		Stage stage = (Stage) this.membersNavigationButton.getScene().getWindow();
		stage.close();
	}

}
