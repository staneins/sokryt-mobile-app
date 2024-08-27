package controller;

import entity.Poem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.PoemService;

import java.io.IOException;

import static javafx.collections.FXCollections.observableArrayList;

public class SokrytController {

    PoemService poemService;


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