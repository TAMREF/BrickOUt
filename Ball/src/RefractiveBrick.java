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
		this.disBrick.setLocation(posit.topoint());
		this.disBrick.setSize(size.x, size.y);
		BrickOut.frame.add(this.disBrick, 0);
		this.disBrick.repaint();
	}

	public RefractiveBrick(Point2D posit) {
		this(defaultRefractiveIndex, posit);
	}

	@Override
	public void goDown() {
		this.posit.y += 20;
		this.disBrick.setLocation(this.posit.topoint());
	}

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