package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.PoemRepo;
import repository.dbConnection.DBConnection;
import service.PoemService;

import java.io.IOException;

public class SokrytApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SokrytApplication.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 640);

        DBConnection dbConnection = new DBConnection();

        PoemRepo poemRepo = new PoemRepo(dbConnection);

        PoemService poemService = new PoemService(poemRepo);

        SokrytController controller = fxmlLoader.getController();
        controller.setPoemService(poemService);

        controller.printPoemsList();

        stage.setTitle("Sokryt Application");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
