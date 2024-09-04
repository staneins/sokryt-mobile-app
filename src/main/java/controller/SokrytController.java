package controller;

import entity.Poem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private Pagination pagination;

    private static final int POEMS_PER_PAGE = 22;

    public SokrytController() {
    }

    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * POEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + POEMS_PER_PAGE, poemService.getAllPoems().size());
        List<Poem> poemsSubList = poemService.getAllPoems().subList(fromIndex, toIndex);

        ObservableList<Poem> poemObservableList = FXCollections.observableArrayList(poemsSubList);
        table.setItems(poemObservableList);
        return table;
    }

    protected void printPoemsList() {
        List<Poem> poems = poemService.getAllPoems();
        int pageCount = (int) Math.ceil((double) poems.size() / POEMS_PER_PAGE);
        pagination.setPageCount(pageCount);

        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * POEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + POEMS_PER_PAGE, poems.size());
            List<Poem> poemsSubList = poems.subList(fromIndex, toIndex);

        ObservableList<Poem> poemObservableList = FXCollections.observableArrayList(poemsSubList);
        table.setItems(poemObservableList);
            return table;
    });
}
}
