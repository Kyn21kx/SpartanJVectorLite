package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Vector {

	double getX();

	double getY();

	void setX(double x);

	void setY(double y);

	void setColor(Color color);

	double calculateTheta();

	double calculateMagnitude();

	void drawToContext(GraphicsContext ctx);

	void drawToContext(GraphicsContext ctx, Vector origin, boolean drawTip);

	static void calibratePositions(Vector origin) {
		Vector2.contextOrigin = (Vector2)origin;
	}

}
