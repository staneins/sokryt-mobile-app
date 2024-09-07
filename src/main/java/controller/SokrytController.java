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
import javafx.scene.layout.VBox;
import service.PoemService;

import java.util.List;

public class SokrytController {

    private PoemService poemService;

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
//        ObservableList<Poem> testData = FXCollections.observableArrayList(
//                new Poem(1, "Тестовый Стих 1", "Тестовый текст 1"),
//                new Poem(2, "Тестовый Стих 2", "Тестовый текст 2")
//        );
//
//        table.setItems(testData);
    }

    protected void printPoemsList() {
        List<Poem> poems = poemService.getAllPoems();

        if (poems.isEmpty()) {
            table.setVisible(false);
            pagination.setVisible(false);
            return;
        }

//        table.setVisible(true);
//        pagination.setVisible(true);

        int pageCount = (int) Math.ceil((double) poems.size() / POEMS_PER_PAGE);
        pagination.setPageCount(pageCount);

        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * POEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + POEMS_PER_PAGE, poems.size());

            System.out.println("Показываем стихи с " + fromIndex + " по " + toIndex);

            if (fromIndex >= poems.size()) {
                return new Label("Страница пуста");
            }

            List<Poem> poemsSubList = poems.subList(fromIndex, toIndex);
            ObservableList<Poem> poemObservableList = FXCollections.observableArrayList(poemsSubList);

            // Устанавливаем данные в таблицу
            table.setItems(poemObservableList);

            // Принудительное обновление таблицы
            table.refresh();
//            table.requestLayout();
//            table.setMinHeight(540);

            // Отладочная информация
            for (Poem poem : poemObservableList) {
                System.out.println("Стих в таблице: " + poem.getTitle());
            }
            System.out.println("TableView visible: " + table.isVisible());
            System.out.println("Number of poems in this page: " + poemObservableList.size());

            return table;
        });
    }
}

