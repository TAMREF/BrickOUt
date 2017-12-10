import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RefractiveBrick extends Brick {
	public static double defaultRefractiveIndex = 1.5;
	public double RefractiveIndex;

	JLabel disBrick = new JLabel(
			new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/element_purple_rectangle.png")).getImage()
					.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));

	public RefractiveBrick(double RefractiveIndex, Point2D posit) {
		super(posit);
		this.RefractiveIndex = RefractiveIndex;
		disBrick.setLocation(posit.topoint());
		disBrick.setSize(size.x, size.y);
	}

	public RefractiveBrick(Point2D posit) {
		this(defaultRefractiveIndex, posit);
	}

	@Override
	public boolean checkCol(Ball b) {
		double speedi, speedf;
		if (checkXYcol(b)) {
			this.alive = false;
			play();
			return true;
		} else if (checkXcol(b)) {
			speedi = b.velocity.distance();
			b.velocity.x = b.velocity.x / this.RefractiveIndex;
			speedf = b.velocity.distance();
			b.velocity.x *= speedi / speedf;
			b.velocity.y *= speedi / speedf;
			play();
			this.alive = false;
			return true;
		} else if (checkYcol(b)) {
			speedi = b.velocity.distance();
			b.velocity.x = b.velocity.x / this.RefractiveIndex;
			speedf = b.velocity.distance();
			b.velocity.x *= speedi / speedf;
			b.velocity.y *= speedi / speedf;
			this.alive = false;
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
			this.disBrick.setVisible(false);
		}
		return flag;
	}
}