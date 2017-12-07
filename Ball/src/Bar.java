import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Bar {
	Point size = new Point(300, 50);
	Point posit;
	Point move = new Point(20, 0);
	ImageIcon Bar = new ImageIcon(getClass().getResource("puzzlepack/paddleBlu.png"));
	Image temp = Bar.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Bar2 = new ImageIcon(temp2);
	JLabel DisBar = new JLabel(Bar2);

	public JLabel add() {
		return DisBar;
	}

	Bar(Point b) {
		this.posit = b;
	}

	Bar() {
		this(new Point(0, 0));
	}

	public void moveLeft() {
		posit.x = posit.x > move.x ? posit.x - move.x : 0;
	}

	public void moveRight(Point s) {
		posit.x = posit.x + move.x + size.x > s.x ? s.x - size.x : posit.x + move.x;
	}

	protected boolean checkYcol(Ball b) { // checks whether velocity.y should be flipped
		return (b.posit.y + b.diameter >= this.posit.y && b.posit.y <= this.posit.y
				|| b.posit.y >= this.posit.y + this.size.y && b.posit.y <= this.posit.y + this.size.y + b.diameter)
				&& (b.posit.x >= this.posit.x && b.posit.x + b.diameter <= this.posit.x + this.size.x);
	}

	protected boolean checkXcol(Ball b) { // checks whether velocity.x should be flipped
		return (b.posit.x + b.diameter >= this.posit.x && b.posit.x <= this.posit.x
				|| b.posit.x >= this.posit.x + this.size.x && b.posit.x <= this.posit.x + this.size.x + b.diameter)
				&& (b.posit.y >= this.posit.y && b.posit.y + b.diameter <= this.posit.y + this.size.y);
	}

	protected boolean checkXYcol(Ball b) { // checks whether both vel.x and vel.y should be flipped
		Point2D tp;
		int cnt = 0;
		tp = new Point2D(b.posit.x, b.posit.y); // LU
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; // RU
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x -= b.diameter;
		tp.y += b.diameter; // LD
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; // RD
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;

		if (cnt == 1)
			return true;
		return false;
	}

	public boolean checkCol(Ball b) {

		if (checkXYcol(b)) {
			System.out.println("XYcol - Bar");
			if ((b.velocity.x > 0) ^ (b.velocity.y > 0)) {
				double temp = b.velocity.x;
				b.velocity.x = -b.velocity.y;
				b.velocity.y = -temp;
			} else {
				double temp = -b.velocity.x;
				b.velocity.x = -b.velocity.y;
				b.velocity.y = temp;
			}
			return true;
		} else if (checkXcol(b)) {
			System.out.println("Xcol - Bar");
			b.velocity.x = -b.velocity.x;
			return true;
		} else if (checkYcol(b)) {
			System.out.println("Ycol - Bar");
			double temp = b.velocity.distance(), theta = Math.atan(b.velocity.y / b.velocity.x);
			if (MainFrame.move != 0)
				if ((b.velocity.x > 0) ^ (MainFrame.move == 1))
					theta = Math.atan(Math.tan(theta) * 2);
				else if ((b.velocity.x < 0) ^ (MainFrame.move == 2))
					theta = Math.atan(Math.tan(theta) / 2);
			b.velocity.x = Math.cos(theta) * temp;
			b.velocity.y = -Math.sin(theta) * temp;
			return true;
		}
		return false;

	}
}