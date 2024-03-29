package sample;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller extends VBox {

	public final int CANVAS_WIDTH = (int)((Main.WIDTH / 2) * 1.8);
	public final int CANVAS_HEIGHT = (int)(Main.HEIGHT * 0.6);
	public final int GRAPH_RESOLUTION = 10;

	private CheckBox editButton;
	private Canvas canvas;
	private Button deleteButton;
	private Button clearButton;
	private Button manualEntry;
	private Button findButton;
	private Label cartesian, polar, normal;
	private ColorPicker colorPicker;
	private MenuBar menuBar;
	private GridPane controlPane;

	private Vector2 selectedVector;
	private ArrayList<Vector2> activeVectors;
	public GraphicsContext ctx;

	public Controller() {
		initializeMenuBar();
		initializeGlobals();
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);

		Label welcomeLabel = new Label("Welcome to the vector visualizer!");
		welcomeLabel.setFont(Font.font(24));
		welcomeLabel.setLayoutX(CANVAS_WIDTH / 2d);
		pane.add(welcomeLabel, 1, 0);


		GridPane propertiesPane = new GridPane();
		propertiesPane.setAlignment(Pos.BASELINE_LEFT);
		propertiesPane.add(cartesian, 2, 0);
		propertiesPane.add(polar, 2, 1);
		propertiesPane.add(normal, 2, 2);
		Vector2 canvasOrigin = new Vector2(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
		Vector.calibratePositions(canvasOrigin);

		setEvents();

		this.getChildren().addAll(menuBar, pane, canvas, propertiesPane, controlPane, colorPicker);
	}

	private void initializeMenuBar() {
		Menu fileMenu = new Menu("File");
		MenuItem saveItem = new MenuItem("Save");
		saveItem.setOnAction(event -> {
			DataManager.saveToFile(activeVectors.toArray(new Vector2[0]));
		});
		MenuItem loadItem = new MenuItem("Load");
		//Controller selfRef = this;
		loadItem.setOnAction(event -> {
			Stack<Vector> data = DataManager.loadFile();
			if (data == null) return;
			activeVectors.clear();
			selectedVector = Vector2.ZERO; //(0, 0)
			while(data.GetSize() > 0) {
				activeVectors.add((Vector2)data.Pop());
		 	}
			Draw();
		});
		fileMenu.getItems().addAll(saveItem, loadItem);
		menuBar = new MenuBar(fileMenu);
	}

	private void initializeGlobals() {
		controlPane = new GridPane();
		activeVectors = new ArrayList<>();
		colorPicker = new ColorPicker();
		editButton = new CheckBox("Edit mode");
		findButton = new Button("Select a vector by its coordinates");
		manualEntry = new Button("Change coordinates");
		deleteButton = new Button("Delete selected vector");
		clearButton = new Button("Clear canvas");
		cartesian = new Label("Selected vector:");
		polar = new Label("Polar coordinates:");
		normal = new Label("Normal vector:");
		canvas = new Canvas(CANVAS_WIDTH + 10, CANVAS_HEIGHT);
		ctx = this.canvas.getGraphicsContext2D();

		colorPicker.setValue(Color.BLACK);
		cartesian.setFont(Font.font(18));
		polar.setFont(Font.font(18));
		normal.setFont(Font.font(18));
		editButton.setFont(Font.font(18));
		deleteButton.setFont(Font.font(18));
		clearButton.setFont(Font.font(18));
		manualEntry.setFont(Font.font(18));
		findButton.setFont(Font.font(18));

		controlPane.setHgap(10);
		controlPane.setVgap(10);
		controlPane.setAlignment(Pos.CENTER);

		controlPane.add(editButton, 1, 0);
		controlPane.add(manualEntry, 2, 0);
		controlPane.add(deleteButton, 3, 0);
		controlPane.add(clearButton, 4, 0);
		controlPane.add(findButton, 5, 0);
	}

	private void removeSelected() {
		if (selectedVector != null) {
			activeVectors.remove(selectedVector);
			selectedVector = Vector2.ZERO;
			Draw();
		}
	}

	private void updateLabels() {
		if (selectedVector != null) {
			cartesian.setText("Selected vector: " + selectedVector.toString());
			polar.setText("Polar coordinates: " + selectedVector.polarString());
			normal.setText("Normal vector: " + selectedVector.getNormalized().toString());
		}
	}

	private void setEvents() {
		findButton.setOnMouseClicked(event -> {
			//Here we'll prompt to select the vector
			InputPrompt promptX = new InputPrompt(selectedVector.getX(), "",
					"Enter the X coordinate (will be clamped to fit the canvas' dimensions)");
			InputPrompt promptY = new InputPrompt(selectedVector.getX(), "",
					"Enter the Y coordinate (will be clamped to fit the canvas' dimensions)");
			Vector2 target;
			{
				double resultX = promptX.showAndReturn();
				double resultY = promptY.showAndReturn();
				target = new Vector2(resultX, resultY);
			}
			Vector2[] arr = activeVectors.toArray(new Vector2[0]);
			int pos = DataManager.interpolationSearch(arr, 0, arr.length - 1, target, new VectorComparator());
			System.out.println(pos);
			if (pos != -1) {
				selectedVector = activeVectors.get(pos);
				updateLabels();
			}
			else
				System.out.println("Error, not found!");
		});

		manualEntry.setOnMouseClicked(event-> {
			if (selectedVector != Vector2.ZERO && selectedVector != null) {
				InputPrompt promptX = new InputPrompt(selectedVector.getX(), "",
						"Enter the X coordinate (will be clamped to fit the canvas' dimensions)");
				InputPrompt promptY = new InputPrompt(selectedVector.getX(), "",
						"Enter the Y coordinate (will be clamped to fit the canvas' dimensions)");
				double resultX = promptX.showAndReturn();
				double resultY = promptY.showAndReturn();
				selectedVector.setX(clamp(resultX, -CANVAS_WIDTH / 2, CANVAS_WIDTH / 2));
				selectedVector.setY(clamp(resultY, -CANVAS_HEIGHT / 2, CANVAS_HEIGHT / 2));

				Vector2[] arr = activeVectors.toArray(new Vector2[0]);
				DataManager.insertionSort(arr, new VectorComparator());
				activeVectors = new ArrayList<>(Arrays.asList(arr));
				Draw();
			}
		});

		clearButton.setOnMouseClicked(event -> {
			activeVectors.clear();
			selectedVector = Vector2.ZERO;
			Draw();
		});

		deleteButton.setOnMouseClicked(event -> {
			removeSelected();
		});

		this.setOnMouseClicked(event -> {
			Vector2 mouseCreated = toCanvasPos(event.getSceneX(), event.getSceneY());
			if (editButton.isSelected()) {
				//Create a new vector, draw it, and add it to the list
				mouseCreated.setColor(colorPicker.getValue());
				mouseCreated.drawToContext(ctx);
				activeVectors.add(mouseCreated);
				Vector2[] arr = activeVectors.toArray(new Vector2[0]);
				DataManager.insertionSort(arr, new VectorComparator());
				activeVectors = new ArrayList<>(Arrays.asList(arr));
				selectedVector = mouseCreated;
			}
			else {
				//Here we will do mouse picking
				//Loop through all the vectors, and get the closest one to be the selected vector
				double distance = Double.MAX_VALUE;
				for (int i = 0; i < activeVectors.size(); i++) {
					//Get the distance here and if it is less than the current vector's distance, select it
					double currDistance = Vector2.distance(mouseCreated, activeVectors.get(i));
					if (currDistance < distance && currDistance < 100) {
						distance = currDistance;
						selectedVector = activeVectors.get(i);
					}
				}
			}
			if (selectedVector != null) {
				updateLabels();
			}
		});
	}

	private Vector2 toCanvasPos(double x, double y) {
		double mX = clamp(x, 60, CANVAS_WIDTH);
		double mY = clamp(y, 0, CANVAS_HEIGHT);
		return new Vector2(mX - (CANVAS_WIDTH / 2d), ((CANVAS_HEIGHT + 60) / 2d) - mY);
	}

	private double clamp(double val, double min, double max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	public void Draw() {
		ctx.setTransform(new Affine());
		ctx.clearRect(0, 0, CANVAS_WIDTH + 10, CANVAS_HEIGHT);
		DrawBorders();
		DrawCartesianPlane();
		for (int i = 0; i < activeVectors.size(); i++) {
			activeVectors.get(i).drawToContext(ctx);
		}
		updateLabels();
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
