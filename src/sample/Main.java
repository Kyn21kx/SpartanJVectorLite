package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = new Controller();
        primaryStage.setTitle("Vector visualizer");
        primaryStage.setScene(new Scene(controller, WIDTH, HEIGHT));
        primaryStage.show();
        controller.Draw();
        /*
        Vector2 v = new Vector2(50, 50);
        for (int i = 0; i < 3; i++) {
            v.setX(v.getX() * 1.654);
            v.setY(v.getY() * 1.4);
            v.drawToContext(controller.ctx);
        }*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
