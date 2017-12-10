import java.awt.Font;
import java.awt.Point;

import javax.swing.JLabel;

public class Label {
	JLabel label = new JLabel();
	Point size = new Point(500, 100);

	Label(Point position, Point size, String text) {
		this.size = size;
		label.setFont(new Font("Serif", Font.PLAIN, 72));
		label.setLocation(position);
		label.setText(text);
		label.setSize(size.x, size.y);
		BrickOut.frame.add(label, 0);
		label.repaint();
	}

	Label(Point position, String text) {
		this(position, new Point(500, 100), text);
	}

	Label() {
		this(new Point(0, 0), "");
	}
}
