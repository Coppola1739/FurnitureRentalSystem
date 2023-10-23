package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class MemberController extends SystemController {
    private MemberDao memberDao;

    @FXML
    private ScrollPane MemberScrollPane;

    @FXML
    private TextField MemberSearchTextField;

    @FXML
    private Label MemberUserNameLabel;

    @FXML
    private ListView<Member> MembersListView;

    @FXML
    void initialize() {
        this.memberDao = new MemberDao();
    }

    @FXML
    public void setLoggedInLabel(String username) {
        this.MemberUserNameLabel.textProperty().set("Logged In: " + username);
        this.loadMemberScrollPane();
    }

    private void loadMemberScrollPane() {
        this.MembersListView.setItems(FXCollections.observableArrayList(this.memberDao.getAllMembers()));
    }


    @FXML
    void editMember(ActionEvent event) {

    }

    @FXML
    void registerMember(ActionEvent event) {

    }
}
