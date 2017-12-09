import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ball {
	int diameter = 25;
	Point2D velocity;
	Point2D posit;
	JLabel DisBall = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/ballGrey.png"))
			.getImage().getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH)));

	public void checkCol(Point b) {
		boolean bo = false;
		if (posit.x <= 0) {
			bo = true;
			velocity.x = Math.abs(velocity.x);
		}
		if (posit.x + diameter >= b.x) {
			bo = true;
			velocity.x = -Math.abs(velocity.x);
		}
		if (posit.y <= 0) {
			bo = true;
			velocity.y = Math.abs(velocity.y);
		}
		if (posit.y + diameter >= b.y) {
			bo = true;
			velocity.y = -Math.abs(velocity.y);
		}
		if (bo)
			Bar.play();
	}

	Ball(Point2D pos, Point2D vel) {
		this.posit = pos;
		this.velocity = vel;
		DisBall.setLocation(posit.topoint());
		DisBall.setSize(diameter, diameter);
	}

	Ball() {
		this(new Point2D(0, 0), new Point2D(0, 0));
	}

	public void update() {
		this.posit.x += this.velocity.x;
		this.posit.y += this.velocity.y;
		this.DisBall.setLocation(this.posit.topoint());
		checkCol(MainFrame.size);
	}
}