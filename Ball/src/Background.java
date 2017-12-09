import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Background {
	Point size = new Point(50, 50);
	String s1 = "mappack/PNG/mapTile_", s2 = ".png";

	public JLabel add(int a) {
		return new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(s1+Integer.toString(a)+s2)).getImage().getScaledInstance(size.x,
				size.y, Image.SCALE_SMOOTH)));
	}

	Background(Point s) {
		this.size = s;
	}

	Background() {
		this(new Point(50, 50));
	}
}
