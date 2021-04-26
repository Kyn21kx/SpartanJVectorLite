package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.Comparator;

public class Main extends Application {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    private static Stage st;

    public static Stage getStage() {
        return st;
    }

    @Override
    public void start(Stage primaryStage) {
        //Entry point of the application
        st = primaryStage;
        Controller controller = new Controller();
        primaryStage.setTitle("Vector visualizer");
        primaryStage.setScene(new Scene(controller, WIDTH, HEIGHT));
        primaryStage.show();
        //Vector drawing function
        controller.Draw();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
