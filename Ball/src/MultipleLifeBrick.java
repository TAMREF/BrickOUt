import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MultipleLifeBrick extends Brick {
	public int life;
	public static int defaultLife = 3;

	public MultipleLifeBrick(int life, Point2D posit) {
		super(posit);
		this.life = life;
	}

	public MultipleLifeBrick(Point2D posit) {
		this(defaultLife, posit);
	}

	JLabel DisBrick = new JLabel(
			new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/element_green_rectangle.png")).getImage()
					.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));

	@Override
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
