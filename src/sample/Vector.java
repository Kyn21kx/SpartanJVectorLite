package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Vector {

	double getX();

	double getY();

	Vector getNormalized();

	void normalize();

	void setX(double x);

	void setY(double y);

	void setColor(Color color);

	double calculateTheta();

	double calculateMagnitude();

	void add(Vector b);

	double dot(Vector v);

	void drawToContext(GraphicsContext ctx);

	String polarString();

	static void calibratePositions(Vector origin) {
		Vector2.contextOrigin = (Vector2)origin;
	}

}
