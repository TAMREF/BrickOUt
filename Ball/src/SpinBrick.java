import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class SpinBrick extends Brick {
	public static Random RandomAngleGenerator = new Random();
	JLabel disBrick = new JLabel(
			new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/element_red_rectangle.png")).getImage()
					.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));

	public static double spin() {
		return 2.0 * Math.PI * RandomAngleGenerator.nextDouble();
	}

	public SpinBrick(Point2D posit) {
		super(posit);
		this.disBrick.setLocation(posit.topoint());
		this.disBrick.setSize(size.x, size.y);
		BrickOut.frame.add(this.disBrick, 0);
		this.disBrick.repaint();
	}

	@Override
	public void goDown() {
		this.posit.y += 20;
		this.disBrick.setLocation(this.posit.topoint());
	}

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

	public boolean update() {
		if (BrickOut.cnt % 100 == 0)
			this.goDown();
		if (this.checkCol(BrickOut.ball)) {
			BrickOut.frame.remove(this.disBrick);
			BrickOut.frame.repaint();
		} else
			this.disBrick.repaint();
		return !this.alive;
	}
}