package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.util.SearchFilter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MemberController extends SystemController {
    private MemberDao memberDao;

    @FXML
    private Button EditMemberButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button RegisterMemberButton;

    @FXML
    private ScrollPane MemberScrollPane;

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
    }

    @FXML
    public void setLoggedInLabel(String username) {
        this.MemberUserNameLabel.textProperty().set("Logged In: " + username);
        this.loadMemberScrollPane();
        this.loadSearchFilterOptions();
    }

    private void loadSearchFilterOptions() {
        this.SearchFilterComboBox.getItems().addAll(SearchFilter.values());
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

    @FXML
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/Login.fxml"));
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

}
