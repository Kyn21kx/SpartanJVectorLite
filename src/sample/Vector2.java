package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class Vector2 implements Vector {

	public static final Vector2 ZERO = new Vector2(0, 0);
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
	public void add(Vector b) {
		this.x = this.x + b.getX();
		this.y = this.y + b.getY();
	}

	@Override
	public double dot(Vector v) {
		return this.x * v.getX() + this.y * v.getY();
	}

	@Override
	public void drawToContext(GraphicsContext ctx) {
		ctx.setFill(color);

		double angle = calculateTheta();
		double len = calculateMagnitude();

		Transform transform = Transform.translate(contextOrigin.x, contextOrigin.y);
		transform = transform.createConcatenation(Transform.rotate(-angle, 0, 0));
		ctx.setTransform(new Affine(transform));

		ctx.strokeLine(0, 0, len, 0);
		ctx.fillPolygon(new double[]{len, len - 8, len - 8, len}, new double[]{0, - 8, 8, 0}, 4);
	}

	@Override
	public String polarString() {
		return "Magnitude = " + this.calculateMagnitude() + "; Angle = " + this.calculateTheta();
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public static Vector2 polarToCartesian(double angle, double magnitude) {
		double x = magnitude * Math.toDegrees(Math.cos(angle));
		double y = magnitude * Math.toDegrees(Math.sin(angle));
		Vector2 v = new Vector2(x, y);
		return v;
	}

	public static double distance(Vector2 a, Vector2 b) {
		double poweredSums = Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2);
		return Math.sqrt(poweredSums);
	}

}
