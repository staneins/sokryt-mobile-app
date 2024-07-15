package controller;

import entity.Poem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.Logic;

import java.io.IOException;

import static javafx.collections.FXCollections.observableArrayList;

public class SokrytController {

    Logic logic;

    {
        try {
            logic = new Logic();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void printPoemsList() {
        ObservableList<Poem> accountList = observableArrayList(yourArrayList);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        accountNrCol.setCellValueFactory(new PropertyValueFactory<>("accountNr"));
        table.setItems(accountList);
    }
}