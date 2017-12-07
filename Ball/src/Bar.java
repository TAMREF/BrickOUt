import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Bar {
	Point size = new Point(300, 100);
	Point position;
	Point move = new Point(20, 0);
	ImageIcon Bar = new ImageIcon(getClass().getResource("icons/Bar.png"));
	Image temp = Bar.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Bar2 = new ImageIcon(temp2);
	JLabel DisBar = new JLabel(Bar2);

	public JLabel add() {
		return DisBar;
	}

	Bar(Point b) {
		this.position = b;
	}

	Bar() {
		this(new Point(0, 0));
	}

	public void moveLeft() {
		position.x = position.x > move.x ? position.x - move.x : 0;
	}

	public void moveRight(Point s) {
		position.x = position.x + move.x + size.x > s.x ? s.x - size.x : position.x + move.x;
	}

	public boolean checkCol(Ball b) {
		if (b.position.x >= position.x && b.position.y >= position.y)
			if (b.position.x <= position.x + size.x && b.position.y <= position.y + size.y)
				return true;
		if (b.position.x + b.diameter >= position.x && b.position.y >= position.y)
			if (b.position.x + b.diameter <= position.x + size.x && b.position.y <= position.y + size.y)
				return true;
		if (b.position.x + b.diameter >= position.x && b.position.y + b.diameter >= position.y)
			if (b.position.x + b.diameter <= position.x + size.x && b.position.y + b.diameter <= position.y + size.y)
				return true;
		if (b.position.x >= position.x && b.position.y + b.diameter >= position.y)
			if (b.position.x <= position.x + size.x && b.position.y + b.diameter <= position.y + size.y)
				return true;
		return false;
	}
}