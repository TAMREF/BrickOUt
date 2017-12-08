import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Background {
	Point size = new Point(50, 50);

	public JLabel add() {
		return new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("mappack/PNG/mapTile_171.png")).getImage().getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));
	}

	Background(Point s) {
		this.size = s;
	}

	Background() {
		this(new Point(50, 50));
	}
}
