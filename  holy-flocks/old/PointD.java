package old;

import java.awt.geom.Point2D;

public class PointD extends Point2D {
	private double x;
	private double y;

	public PointD(double xVal, double yVal) {
		setX(xVal);
		setY(yVal);
	}

	public PointD(PointD point) {
		setX(point.getX());
		setY(point.getY());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setLocation(double xVal, double yVal) {
		setX(xVal);
		setY(yVal);
	}

	public void translate(PointD point) {
		setX(getX() + point.getX());
		setY(getY() + point.getY());
	}

	public void translate(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
