package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Controller extends VBox {

	public final int CANVAS_WIDTH = (int)((Main.WIDTH / 2) * 1.8);
	public final int CANVAS_HEIGHT = (int)(Main.HEIGHT * 0.6);
	public final int GRAPH_RESOLUTION = 10;

	private CheckBox editButton;
	private Canvas canvas;
	private GraphicsContext ctx;

	public Controller() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);

		Label welcomeLabel = new Label("Welcome to the vector visualizer!");
		welcomeLabel.setFont(Font.font(24));
		welcomeLabel.setLayoutX(CANVAS_WIDTH / 2d);

		pane.add(welcomeLabel, 1, 0);
		canvas = new Canvas(CANVAS_WIDTH + 10, CANVAS_HEIGHT);
		Vector2 canvasOrigin = new Vector2(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
		Vector.calibratePositions(canvasOrigin);
		ctx = this.canvas.getGraphicsContext2D();

		editButton = new CheckBox("Edit mode");
		this.getChildren().addAll(pane, canvas, editButton);
	}
	
	public void Draw() {
		DrawBorders();
		DrawCartesianPlane();
		//Let's just draw one vector
		Vector2 v = new Vector2(-125, 100);
		v.drawToContext(ctx);
	}

	private void DrawCartesianPlane() {
		ctx.setFill(Color.BLACK);
		ctx.fillRect(CANVAS_WIDTH / 2, 0, 1, CANVAS_HEIGHT);
		ctx.strokeText("y", (CANVAS_WIDTH / 2) - 10, 10);
		ctx.fillRect(60, CANVAS_HEIGHT / 2, CANVAS_WIDTH, 1);
		ctx.strokeText("x", CANVAS_WIDTH + 2, (CANVAS_HEIGHT / 2) - 2);
		for (int x = 60; x <= CANVAS_WIDTH; x++) {
			if (x > 60 && x % GRAPH_RESOLUTION == 0) {
				ctx.fillRect(x - 4, CANVAS_HEIGHT / 2, 1, 5);
			}
		}
		for (int y = 0; y < CANVAS_HEIGHT; y++) {
			if (y > 0 && y % GRAPH_RESOLUTION == 0)
				ctx.fillRect(CANVAS_WIDTH / 2, y , 5, 1);
		}
	}

	private void DrawBorders() {
		ctx.setFill(Color.BLACK);
		ctx.fillRect(60, CANVAS_HEIGHT * 0.998, CANVAS_WIDTH, 1);
		ctx.fillRect(60, 0, CANVAS_WIDTH, 1);
		ctx.fillRect(60, 0, 1, CANVAS_HEIGHT);
		ctx.fillRect(CANVAS_WIDTH + 9, 0, 1, CANVAS_HEIGHT);
	}

}