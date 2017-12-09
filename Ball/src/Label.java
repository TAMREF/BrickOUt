import java.awt.Font;
import java.awt.Point;

import javax.swing.JLabel;

public class Label {
	JLabel label = new JLabel();
	Point size = new Point(500, 100);

	Label(Point position, String text) {
		label.setFont(new Font("Serif", Font.PLAIN, 72));
		label.setLocation(position);
		label.setText(text);
		label.setSize(size.x, size.y);
	}
}
