import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Bar {
	Point size = new Point(300, 50);
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
		position.x = position.x - move.x;
	}

	public void moveRight() {
		position.x = position.x + move.x;
	}
}