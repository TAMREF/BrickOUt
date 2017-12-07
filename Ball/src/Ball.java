import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Ball {
	int diameter = 25;
	Point2D velocity;
	Point2D posit;
	ImageIcon Ball = new ImageIcon(getClass().getResource("puzzlepack/ballGrey.png"));
	Image temp = Ball.getImage();
	Image temp2 = temp.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
	ImageIcon Ball2 = new ImageIcon(temp2);
	JLabel DisBall = new JLabel(Ball2);

	public void checkCol(Point b) {
		if (posit.x <= 0)
			velocity.x = Math.abs(velocity.x);
		if (posit.x + diameter >= b.x)
			velocity.x = -Math.abs(velocity.x);
		if (posit.y <= 0)
			velocity.y = Math.abs(velocity.y);
		if (posit.y + diameter >= b.y)
			velocity.y = -Math.abs(velocity.y);
	}

	public JLabel add() {
		return DisBall;
	}

	Ball(Point2D pos, Point2D vel) {
		this.posit = pos;
		this.velocity = vel;
	}

	Ball() {
		this(new Point2D(0, 0), new Point2D(0, 0));
	}

}