package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller();
        primaryStage.setTitle("Vector visualizer");
        primaryStage.setScene(new Scene(controller, WIDTH, HEIGHT));
        primaryStage.show();
        controller.Draw();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
