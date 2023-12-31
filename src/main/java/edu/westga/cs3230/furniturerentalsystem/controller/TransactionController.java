package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.FurnitureDao;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Furniture;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Transaction;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.SearchFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class TransactionController extends SystemController {

	private static final String NO_SELECTION = "-No Selection-";

	private FurnitureDao furnitureDao;

	private MemberDao memberDao;

	@FXML
	private Button makeReturnButton;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField memberSearchTextField;

	@FXML
	private Label membersListLabel;

	@FXML
	private ComboBox<SearchFilter> searchFilterComboBox;

	@FXML
	private Button searchMemberButton;

	@FXML
	private Button clearSearchButton;

	@FXML
	private Label searchFilterLabel;

	@FXML
	private Button logOutButton;

	@FXML
	private Button homeButton;

	@FXML
	private TextField furnitureIDSearchBox;

	@FXML
	private ComboBox<String> furnitureStyleComboBox;

	@FXML
	private ComboBox<String> furnitureCategoryComboBox;

	@FXML
	private Button searchButton;

	@FXML
	private ListView<Furniture> furnitureListView;

	@FXML
	private Label furnitureUserNameLabel;

	@FXML
	private TextField memberTextField;

	@FXML
	private ListView<Furniture> cartListView;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private ListView<Member> membersListView;

	@FXML
	private Button addRentalButton;

	@FXML
	void submitRental(ActionEvent event) {
		if (!this.isValidSubmission()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Submission Error");
			alert.setContentText(
					"Please ensure you've selected a member and that the start date is before the end date.");
			alert.showAndWait();
		} else {
			if (this.showConfirmationAlert("Confirmation", "Make rental",
					"Are you sure you want to make this rental?")) {
				try {
					Transaction tr = Transaction.builder().rentalId("unknown").memberId(this.memberTextField.getText())
							.selectedItems(this.cartListView.getItems())
							.employeeNum(EmployeeDao.getEmployeeNumByUsername(loggedInUser)).build();
					String rentalId = RentalDao.addRental(tr, java.sql.Date.valueOf(this.startDatePicker.getValue()),
							java.sql.Date.valueOf(this.endDatePicker.getValue()));
					tr.setRentalId(rentalId);
					if (rentalId == null) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Fail");
						alert.setHeaderText("Failed to Add");
						alert.setContentText("Order could not be made at this time");
						alert.showAndWait();
					} else {
						this.showReceipt(tr);
					}
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Server error");
					alert.setContentText("Please ensure that you are signed in");
					alert.showAndWait();
				}
			}
		}
	}

	private void showReceipt(Transaction tr) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Rental Created");
		alert.setContentText("Here is your receipt:\n" + tr.generateReceipt());
		alert.showAndWait();
	}

	public boolean showConfirmationAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		ButtonType buttonTypeYes = new ButtonType("Yes");
		ButtonType buttonTypeNo = new ButtonType("No");

		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == buttonTypeYes;
	}

	@FXML
	void searchForMembers(ActionEvent event) {
		SearchFilter selectedFilter = this.searchFilterComboBox.getValue();
		String searchText = this.memberSearchTextField.getText();

		if (selectedFilter == null || searchText.isEmpty()) {
			return;
		}

		switch (selectedFilter) {
		case MEMBER_ID:
			ArrayList<Member> membersByMemberId = this.memberDao.getMembersByMemberId(searchText);
			this.membersListView.setItems(FXCollections.observableArrayList(membersByMemberId));
			break;
		case PHONE_NUMBER:
			ArrayList<Member> membersByPhoneNumber = this.memberDao.getMembersByPhoneNumber(searchText);
			this.membersListView.setItems(FXCollections.observableArrayList(membersByPhoneNumber));
			break;
		case NAME:
			ArrayList<Member> membersByName = this.memberDao.getMembersByName(searchText);
			this.membersListView.setItems(FXCollections.observableArrayList(membersByName));
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + selectedFilter);
		}
	}

	// Todo: these navigate methods and others through out the code can be
	// refactored
	@FXML
	void logOut(ActionEvent event) throws IOException {
		this.changeWindow(Constants.LOGIN_FXML);
		Stage stage = (Stage) this.logOutButton.getScene().getWindow();

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

	@FXML
	void navigateToHomePage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.HOME_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		SystemController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);
		Stage stage = (Stage) this.homeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void makeReturn() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Constants.RETURNS_PAGE_FXML));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		ReturnsController controller = loader.getController();
		controller.setLoggedInLabel(super.loggedInUser);

		controller.setSelectedUser(this.membersListView.getSelectionModel().getSelectedItem());
		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		Stage stage = (Stage) this.homeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void clearMemberSearch(ActionEvent event) {
		this.searchFilterComboBox.getSelectionModel().clearSelection();
		this.memberSearchTextField.setText("");
		this.loadMemberListView(this.memberDao.getAllMembers());
	}

	private void loadSearchFilterOptions() {
		this.searchFilterComboBox.getItems().addAll(SearchFilter.values());
	}

	private void loadMemberListView(ArrayList<Member> members) {
		this.membersListView.setItems(FXCollections.observableArrayList(members));
	}

	private void populateTypeComboBox() {
		this.furnitureCategoryComboBox
				.setItems(FXCollections.observableArrayList("Cabinet", "Sofa", "Chair", "Table", NO_SELECTION));
	}

	private void populateStyleComboBox() {
		this.furnitureStyleComboBox.setItems(
				FXCollections.observableArrayList("Modern", "Traditional", "Rustic", "Scandinavian", NO_SELECTION));
	}

	@Override
	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		Employee employee = EmployeeDao.getEmployeeByUsername(username).get(0);
		this.furnitureUserNameLabel.textProperty()
				.set("Logged In: " + employee.getPInfo().getFirstName() + " " + employee.getPInfo().getLastName());
	}

	@FXML
	void backToLandingPage(ActionEvent event) throws IOException {
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
		Stage thisStage = (Stage) this.homeButton.getScene().getWindow();
		thisStage.close();
	}

	@FXML
	void searchFurniture(ActionEvent event) {
		this.loadFurnitureListView(this.furnitureDao.getFurnitureById(this.furnitureIDSearchBox.getText()));
	}

	private void loadFurnitureListView(ArrayList<Furniture> allFurniture) {
		this.furnitureListView.setItems(FXCollections.observableArrayList(allFurniture));
	}

	private void addListenerForStyleComboBox() {
		this.furnitureStyleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			String furnitureCategory = this.furnitureCategoryComboBox.getValue();
			if (newValue.equals(NO_SELECTION) && (furnitureCategory.equals(NO_SELECTION))) {
				this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
			} else if (newValue.equals(NO_SELECTION) && (!furnitureCategory.equals(NO_SELECTION))) {
				this.loadFurnitureListView(this.furnitureDao.getFurnitureByCategory(furnitureCategory));

			} else if (!newValue.equals(NO_SELECTION) && (!furnitureCategory.equals(NO_SELECTION))) {
				this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyleAndCategory(newValue,
						this.furnitureCategoryComboBox.getValue()));

			} else {
				this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyle(newValue));
			}
		});
	}

	private void addListenerForCategoryComboBox() {
		this.furnitureCategoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			String furnitureStyle = this.furnitureStyleComboBox.getValue();
			if (newValue.equals(NO_SELECTION) && (furnitureStyle.equals(NO_SELECTION))) {
				this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
			} else if (newValue.equals(NO_SELECTION) && (!furnitureStyle.equals(NO_SELECTION))) {
				this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyle(furnitureStyle));
			} else if (!newValue.equals(NO_SELECTION) && (!furnitureStyle.equals(NO_SELECTION))) {
				this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyleAndCategory(furnitureStyle, newValue));
			} else {
				this.loadFurnitureListView(this.furnitureDao.getFurnitureByCategory(newValue));
			}
		});
	}

	private void handleFurnitureListDoubleClick() {
		this.furnitureListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				Furniture selectedFurniture = this.furnitureListView.getSelectionModel().getSelectedItem();
				if (selectedFurniture != null) {
					this.cartListView.getItems().add(selectedFurniture);
					this.sortCartListView();
				}
			}
		});
	}

	private void handleCartListDoubleClick() {
		this.cartListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				Furniture selectedItem = this.cartListView.getSelectionModel().getSelectedItem();
				if (selectedItem != null) {
					this.cartListView.getItems().remove(selectedItem);
					this.sortCartListView();
				}
			}
		});
	}

	private void handleMembersListDoubleClick() {
		this.membersListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				Member selectedMember = this.membersListView.getSelectionModel().getSelectedItem();
				if (selectedMember != null) {
					this.memberTextField.setText(selectedMember.getMemberId());
				}
			}
		});
	}

	private boolean isValidSubmission() {

		if (this.memberTextField.getText() == null || this.memberTextField.getText().trim().isEmpty()) {
			return false;
		}

		if (this.startDatePicker.getValue() != null && this.endDatePicker.getValue() != null) {
			if (this.startDatePicker.getValue().isAfter(this.endDatePicker.getValue())) {
				return false;
			}
		} else {
			return false;
		}

		if (this.cartListView.getItems().isEmpty()) {
			return false;
		}

		return true;
	}

	private Map<Furniture, Integer> getFurnitureFrequency(List<Furniture> furnitureList) {
		Map<Furniture, Integer> frequencyMap = new HashMap<>();
		for (Furniture furniture : furnitureList) {
			frequencyMap.put(furniture, frequencyMap.getOrDefault(furniture, 0) + 1);
		}
		return frequencyMap;
	}

	private void sortCartListView() {
		ObservableList<Furniture> cartItems = this.cartListView.getItems();
		Map<Furniture, Integer> frequencyMap = this.getFurnitureFrequency(cartItems);
		FXCollections.sort(cartItems, (f1, f2) -> {
			return frequencyMap.get(f2).compareTo(frequencyMap.get(f1));
		});
		this.cartListView.setItems(cartItems);
	}

	private void addListenerToMakeReturnButton() {
		this.makeReturnButton.setDisable(true);
		this.membersListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						this.makeReturnButton.setDisable(false);
					} else {
						this.makeReturnButton.setDisable(true);
					}
				});
	}

	@FXML
	void initialize() {
		this.memberTextField.setEditable(false);
		this.populateStyleComboBox();
		this.populateTypeComboBox();
		this.furnitureDao = new FurnitureDao();
		this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
		this.furnitureStyleComboBox.setValue(NO_SELECTION);
		this.furnitureCategoryComboBox.setValue(NO_SELECTION);
		this.addListenerForStyleComboBox();
		this.addListenerForCategoryComboBox();
		this.addListenerToMakeReturnButton();
		this.memberDao = new MemberDao();
		this.loadMemberListView(this.memberDao.getAllMembers());
		this.loadSearchFilterOptions();
		this.handleFurnitureListDoubleClick();
		this.handleCartListDoubleClick();
		this.handleMembersListDoubleClick();
	}
}
