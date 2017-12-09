import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class SpinBrick extends Brick {
	public static Random RandomAngleGenerator = new Random();
	JLabel DisBrick = new JLabel(
			new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/element_red_rectangle.png")).getImage()
					.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));

	public static double spin() {
		return 2.0 * Math.PI * RandomAngleGenerator.nextDouble();
	}

	public SpinBrick(Point2D posit) {
		super(posit);
	}
	@Override
	public boolean checkCol(Ball b) {
		double speed = b.velocity.distance(), angle = spin();
		if (checkXYcol(b) || checkXcol(b) || checkYcol(b)) {
			this.alive = false;
			b.velocity = new Point2D(speed * Math.cos(angle), speed * Math.sin(angle));
			play();
			return true;
		}
		return false;
	}

	public boolean update(Ball b, int cnt) {
		boolean flag = checkCol(b);
		if (cnt % 100 == 0)
			this.goDown();
		if (flag) {
			this.alive = false;
			this.DisBrick.setVisible(false);
		}
		return flag;
	}
}