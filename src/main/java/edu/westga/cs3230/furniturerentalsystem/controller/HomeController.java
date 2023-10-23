package edu.westga.cs3230.furniturerentalsystem.controller;


import edu.westga.cs3230.furniturerentalsystem.Main;
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

public class HomeController extends SystemController {

    @FXML
    private Button FurnitureNavigationButton;

    @FXML
    private Label HomeUserNameLabel;

    @FXML
    private Button MembersNavigationButton;

    @FXML
    private Button TransactionsNavigationButton;

    @FXML
    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        this.HomeUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
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
        Stage stage = (Stage) this.MembersNavigationButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void navigateToFurniturePage(ActionEvent event) {

    }


    @FXML
    void navigateToTransactionsPage(ActionEvent event) {

    }

}