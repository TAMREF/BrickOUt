import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Ball {
	int diameter = 50;
	Point2D velocity;
	Point2D position;
	ImageIcon Ball = new ImageIcon(getClass().getResource("icons/Ball.png"));
	Image temp = Ball.getImage();
	Image temp2 = temp.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
	ImageIcon Ball2 = new ImageIcon(temp2);
	JLabel DisBall = new JLabel(Ball2);

	public void checkCol(Point b) {
		if (position.x <= 0)
			velocity.x = Math.abs(velocity.x);
		if (position.x + diameter >= b.x)
			velocity.x = -Math.abs(velocity.x);
		if (position.y <= 0)
			velocity.y = Math.abs(velocity.y);
		if (position.y + diameter >= b.y)
			velocity.y = -Math.abs(velocity.y);
	}

	public JLabel add() {
		return DisBall;
	}

	Ball(Point2D pos, Point2D vel) {
		this.position = pos;
		this.velocity = vel;
	}

	Ball() {
		this(new Point2D(0, 0), new Point2D(0, 0));
	}

}