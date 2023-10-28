package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.MemberStringFormatter;
import edu.westga.cs3230.furniturerentalsystem.util.SearchFilter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Member Controller for member page
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class MemberController extends SystemController {
    private MemberDao memberDao;

    @FXML
    private Button editMemberButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button registerMemberButton;

    @FXML
    private TextField memberSearchTextField;

    @FXML
    private Label memberUserNameLabel;

    @FXML
    private ComboBox<SearchFilter> searchFilterComboBox;

    @FXML
    private ListView<Member> memberListView;

    @FXML
    void initialize() {
        this.memberDao = new MemberDao();
        this.loadMemberListView(this.memberDao.getAllMembers());
        this.loadSearchFilterOptions();
        this.setListViewDoubleClickHandler();
    }

    /**
     * Sets the logged in label on the view
     *
     * @param username the username to set
     */
    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        this.memberUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
    }

    @FXML
    void editMember(ActionEvent event)  throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Constants.ALTER_USER_FXML));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();
        
        SystemController controller = loader.getController();
        controller.setLoggedInLabel(super.loggedInUser);

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
        Stage stage = (Stage) this.registerMemberButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void registerMember(ActionEvent event) {
        try {
            this.navigateTo(event, Constants.REGISTER_FXML);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
                this.memberListView.setItems(FXCollections.observableArrayList(membersByMemberId));
                break;
            case PHONE_NUMBER:
                ArrayList<Member> membersByPhoneNumber = this.memberDao.getMembersByPhoneNumber(searchText);
                this.memberListView.setItems(FXCollections.observableArrayList(membersByPhoneNumber));
                break;
            case NAME:
                ArrayList<Member> membersByName = this.memberDao.getMembersByName(searchText);
                this.memberListView.setItems(FXCollections.observableArrayList(membersByName));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedFilter);
        }
    }

    // Todo: these navigate methods and others through out the code can be refactored
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

        Stage stage = (Stage) this.editMemberButton.getScene().getWindow();

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
    void clearMemberSearch(ActionEvent event) {
        this.searchFilterComboBox.getSelectionModel().clearSelection();
        this.memberSearchTextField.setText("");
        this.loadMemberListView(this.memberDao.getAllMembers());
    }

    private void setListViewDoubleClickHandler() {
        this.memberListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Member selectedMember = MemberController.this.memberListView.getSelectionModel().getSelectedItem();
                    if (selectedMember != null) {
                        MemberController.this.showMemberDetailsPopup(selectedMember);
                    }
                }
            }
        });
    }

    private void showMemberDetailsPopup(Member member) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Member Details");
        alert.setHeaderText("Member ID: " + member.getMemberId());

        TextArea textArea = new TextArea();
        MemberStringFormatter memberStringFormatter = new MemberStringFormatter();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(memberStringFormatter.formatMemberString(member));

        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    private void loadSearchFilterOptions() {
        this.searchFilterComboBox.getItems().addAll(SearchFilter.values());
    }

    private void loadMemberListView(ArrayList<Member> members) {
        this.memberListView.setItems(FXCollections.observableArrayList(members));
    }
}
