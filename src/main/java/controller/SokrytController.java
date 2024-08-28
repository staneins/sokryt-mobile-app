package controller;

import entity.Poem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.PoemService;

import java.util.List;

public class SokrytController {

    private PoemService poemService;

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Poem> table;

    @FXML
    private TableColumn<Poem, String> titleColumn;

    public SokrytController() {
    }

    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @FXML
    public void initialize() {
    }

    protected void printPoemsList() {
        List<Poem> poems = poemService.getAllPoems(); // Получаем список всех стихов из сервиса
        ObservableList<Poem> poemObservableList = FXCollections.observableArrayList(poems);

        table.setItems(poemObservableList);
    }
}
