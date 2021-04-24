package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
		return Math.atan(Math.toDegrees((y / x)));
	}

	@Override
	public double calculateMagnitude() {
		double powerX = Math.pow(x, 2);
		double powerY = Math.pow(y, 2);
		return Math.sqrt(powerX + powerY);
	}

	@Override
	public void drawToContext(GraphicsContext ctx) {
		ctx.setStroke(color);
		double graphicX = this.x + contextOrigin.x;
		double graphicY = contextOrigin.y - this.y;
		ctx.strokeLine(contextOrigin.x, contextOrigin.y, graphicX, graphicY);
		ctx.strokeLine(graphicX, graphicY, this.x + contextOrigin.x, contextOrigin.y - this.y);

		double angle = this.calculateTheta();
		Vector2 leftArrowTip = Vector2.polarToCartesian(angle + 45, 10);
		Vector2 rightArrowTip = Vector2.polarToCartesian(angle - 45, 10);
		Vector2 tipOrigin = new Vector2(graphicX, graphicY);

		if (this.x < 0) {
			if (this.y < 0) {
				leftArrowTip.setX(Math.abs(leftArrowTip.getX()));
				rightArrowTip.setY(rightArrowTip.getY() * -1);
			}
			else {
				leftArrowTip.setX(leftArrowTip.getX() * -1);
				rightArrowTip.setY(Math.abs(rightArrowTip.getY()));
			}
		}

		leftArrowTip.drawToContext(ctx, tipOrigin, false);
		rightArrowTip.drawToContext(ctx, tipOrigin, false);
	}

	@Override
	public void drawToContext(GraphicsContext ctx, Vector origin, boolean drawTip) {
		ctx.setStroke(color);
		double graphicX = this.x + origin.getX();
		double graphicY = origin.getY() - this.y;
		ctx.strokeLine(origin.getX(), origin.getY(), graphicX, graphicY);
		ctx.strokeLine(graphicX, graphicY, this.x + origin.getX(), origin.getY() - this.y);
		if (drawTip) {
			double angle = this.calculateTheta();
			Vector2 leftArrowTip = Vector2.polarToCartesian(angle + 45, 10);
			Vector2 rightArrowTip = Vector2.polarToCartesian(angle - 45, 10);
			Vector2 tipOrigin = new Vector2(graphicX, graphicY);

			leftArrowTip.drawToContext(ctx, tipOrigin, false);
			rightArrowTip.drawToContext(ctx, tipOrigin, false);
		}
	}

	public static Vector2 polarToCartesian(double angle, double magnitude) {
		double x = magnitude * Math.cos(Math.toDegrees(angle));
		double y = magnitude * Math.sin(Math.toDegrees(angle));
		Vector2 v = new Vector2(x, y);
		return v;
	}
}
