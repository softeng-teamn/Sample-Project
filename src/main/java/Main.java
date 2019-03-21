import control.DBController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 317, 412));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            DBController.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
