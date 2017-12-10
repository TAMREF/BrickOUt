import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Background {
	Point size = new Point(50, 50);
	Point position;
	String s1 = "mappack/PNG/mapTile_", s2 = ".png";
	JLabel back = null;

	Background(Point s, Point p, int a) {
		this.size = s;
		this.position = p;
		back = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(s1 + Integer.toString(a) + s2)).getImage()
				.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));
		back.setLocation(this.position);
		back.setSize(this.size.x, this.size.y);
		BrickOut.frame.add(back, 0);
	}

	Background() {
		this(new Point(50, 50), new Point(0, 0), 171);
	}
}
