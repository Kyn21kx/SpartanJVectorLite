package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class Vector2 implements Vector {

	protected static Vector2 contextOrigin;

	private double x;
	private double y;
	private Color color;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
		color = Color.BLACK;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public Vector getNormalized() {
		double mag = calculateMagnitude();
		double x = this.x / mag;
		double y = this.y / mag;
		return new Vector2(x, y);
	}

	@Override
	public void normalize() {
		double mag = calculateMagnitude();
		this.x /= mag;
		this.y /= mag;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public double calculateTheta() {
		return Math.toDegrees(Math.atan2(this.y, this.x));
	}

	@Override
	public double calculateMagnitude() {
		double powerX = Math.pow(x, 2);
		double powerY = Math.pow(y, 2);
		return Math.sqrt(powerX + powerY);
	}

	@Override
	public double distanceTo(Vector b) {

		return 0;
	}

	@Override
	public void add(Vector b) {
		this.x = this.x + b.getX();
		this.y = this.y + b.getY();
	}

	@Override
	public void drawToContext(GraphicsContext ctx) {
		ctx.setFill(color);

		double angle = calculateTheta();
		double len = calculateMagnitude();

		Transform transform = Transform.translate(contextOrigin.x, contextOrigin.y);
		//angle = adjustAngle(angle);
		transform = transform.createConcatenation(Transform.rotate(-angle, 0, 0));
		ctx.setTransform(new Affine(transform));

		ctx.strokeLine(0, 0, len, 0);
		ctx.fillPolygon(new double[]{len, len - 8, len - 8, len}, new double[]{0, - 8, 8, 0}, 4);
	}

	@Override
	public void drawToContext(GraphicsContext ctx, Vector origin) {

	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public static Vector2 polarToCartesian(double angle, double magnitude) {
		double x = magnitude * Math.cos(Math.toDegrees(angle));
		double y = magnitude * Math.sin(Math.toDegrees(angle));
		Vector2 v = new Vector2(x, y);
		return v;
	}
}
