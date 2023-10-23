package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.SearchFilter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MemberController extends SystemController {
    private MemberDao memberDao;

    @FXML
    private Button EditMemberButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button RegisterMemberButton;

    @FXML
    private TextField MemberSearchTextField;

    @FXML
    private Label MemberUserNameLabel;

    @FXML
    private ComboBox<SearchFilter> SearchFilterComboBox;

    @FXML
    private ListView<Member> MembersListView;

    @FXML
    void initialize() {
        this.memberDao = new MemberDao();
        this.loadMemberListView(this.memberDao.getAllMembers());
        this.loadSearchFilterOptions();
    }

    @FXML
    public void setLoggedInLabel(String username) {
        this.MemberUserNameLabel.textProperty().set("Logged In: " + username);
    }

    private void loadSearchFilterOptions() {
        this.SearchFilterComboBox.getItems().addAll(SearchFilter.values());
    }

    private void loadMemberListView(ArrayList<Member> members) {
        this.MembersListView.setItems(FXCollections.observableArrayList(members));
    }


    @FXML
    void editMember(ActionEvent event) {

    }

    @FXML
    void registerMember(ActionEvent event) {

    }

    @FXML
    void SearchForMembers(ActionEvent event) {
        SearchFilter selectedFilter = this.SearchFilterComboBox.getValue();
        String searchText = this.MemberSearchTextField.getText();

        if (selectedFilter == null || searchText.isEmpty()) {
            return;
        }

        switch (selectedFilter) {
            case MEMBER_ID:
                ArrayList<Member> membersByMemberId = this.memberDao.getMembersByMemberId(searchText);
                this.MembersListView.setItems(FXCollections.observableArrayList(membersByMemberId));
                break;
            case PHONE_NUMBER:
                ArrayList<Member> membersByPhoneNumber = this.memberDao.getMembersByPhoneNumber(searchText);
                this.MembersListView.setItems(FXCollections.observableArrayList(membersByPhoneNumber));
                break;
            case NAME:
                ArrayList<Member> membersByName = this.memberDao.getMembersByName(searchText);
                this.MembersListView.setItems(FXCollections.observableArrayList(membersByName));
                break;
        }
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
        Stage stage = (Stage) this.LogOutButton.getScene().getWindow();

        stage.close();
    }

    @FXML
    void clearMemberSearch(ActionEvent event) {
        this.SearchFilterComboBox.getSelectionModel().clearSelection();
        this.MemberSearchTextField.setText("");
        this.loadMemberListView(this.memberDao.getAllMembers());
    }
    //Todo: create a pop out window to display a members full information when clicked in listview
}
