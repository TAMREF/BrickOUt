import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MultipleLifeBrick extends Brick {
	public int life;
	public static int defaultLife = 3;

	public MultipleLifeBrick(int life, Point2D posit) {
		super(posit);
		this.life = life;
		this.disBrick.setLocation(posit.topoint());
		this.disBrick.setSize(size.x, size.y);
		BrickOut.frame.add(this.disBrick, 0);
		this.disBrick.repaint();
	}

	public MultipleLifeBrick(Point2D posit) {
		this(defaultLife, posit);
	}

	JLabel disBrick = new JLabel(
			new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/element_green_rectangle.png")).getImage()
					.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));

	@Override
	public void goDown() {
		this.posit.y += 20;
		this.disBrick.setLocation(this.posit.topoint());
	}

	public boolean checkCol(Ball b) {
		if (checkXYcol(b)) {
			b.velocity.x = -b.velocity.x;
			b.velocity.y = -b.velocity.y;
			if (--this.life == 0) {
				play();
				this.alive = false;
			} else
				Bar.play();
			return true;
		} else if (checkXcol(b)) {
			b.velocity.x = -b.velocity.x;
			if (--this.life == 0) {
				play();
				this.alive = false;
			} else
				Bar.play();
			return true;
		} else if (checkYcol(b)) {
			b.velocity.y = -b.velocity.y;
			if (--this.life == 0) {
				play();
				this.alive = false;
			} else
				Bar.play();
			return true;
		}
		return false;
	}

	public boolean update() {
		if (BrickOut.cnt % 100 == 0)
			this.goDown();
		if (this.checkCol(BrickOut.ball) && this.life == 0) {
			BrickOut.frame.remove(this.disBrick);
			BrickOut.frame.repaint();
		} else
			this.disBrick.repaint();
		return !this.alive;
	}
}
