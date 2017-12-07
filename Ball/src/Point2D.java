import java.awt.Point;

class Point2D {
	public double x;
	public double y;

	Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	Point2D() {
		this(0.0, 0.0);
	}

	double distance() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	Point topoint() {
		return new Point((int) (this.x), (int) (this.y));
	}
	
	public String toString() {
		return "("+Double.toString(x)+", "+Double.toString(y)+")";
	}
}