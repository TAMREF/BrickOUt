import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Brick {
	public Point size = new Point(200, 100); // (width, height)
	public Point2D posit;
	ImageIcon Brick = new ImageIcon(getClass().getResource("puzzlepack/element_grey_rectangle.png"));
	Image temp = Brick.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Brick2 = new ImageIcon(temp2);
	JLabel DisBrick = new JLabel(Brick2);
	boolean alive = true;

	public JLabel add() {
		return DisBrick;
	}

	public void godown() {
		posit.y += 20;
	}

	public Brick() {
		this(new Point2D(0, 0));
	}

	public Brick(Point2D posit) {
		this.posit = posit;
	}

	protected boolean checkYcol(Ball b) { // checks whether velocity.y should be flipped
		return (b.posit.y + b.diameter >= this.posit.y && b.posit.y <= this.posit.y
				|| b.posit.y >= this.posit.y + this.size.y && b.posit.y <= this.posit.y + this.size.y + b.diameter)
				&& (b.posit.x >= this.posit.x && b.posit.x + b.diameter <= this.posit.x + this.size.x);
	}

	protected boolean checkXcol(Ball b) { // checks whether velocity.x should be flipped
		return (b.posit.x + b.diameter >= this.posit.x && b.posit.x <= this.posit.x
				|| b.posit.x >= this.posit.x + this.size.x && b.posit.x <= this.posit.x + this.size.x + b.diameter)
				&& (b.posit.y >= this.posit.y && b.posit.y + b.diameter <= this.posit.y + this.size.y);
	}

	protected boolean checkXYcol(Ball b) { // checks whether both vel.x and vel.y should be flipped
		Point2D tp;
		int cnt = 0;
		tp = new Point2D(b.posit.x, b.posit.y); // LU
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; // RU
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x -= b.diameter;
		tp.y += b.diameter; // LD
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; // RD
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;

		if (cnt == 1)
			return true;
		return false;
	}

	public boolean checkCol(Ball b) {

		if (checkXYcol(b)) {
			System.out.println("XYcol");
			if ((b.velocity.x > 0) ^ (b.velocity.y > 0)) {
				double temp = b.velocity.x;
				b.velocity.x = -b.velocity.y;
				b.velocity.y = -temp;
			} else {
				double temp = b.velocity.x;
				b.velocity.x = b.velocity.y;
				b.velocity.y = temp;
			}
			this.alive = false;
			return true;
		} else if (checkXcol(b)) {
			System.out.println("Xcol");
			b.velocity.x = -b.velocity.x;
			this.alive = false;
			return true;
		} else if (checkYcol(b)) {
			System.out.println("Ycol");
			b.velocity.y = -b.velocity.y;
			this.alive = false;
			return true;
		}
		return false;

	}
}

class MultipleLifeBrick extends Brick {
	public int life;
	public static int defaultLife = 3;

	public MultipleLifeBrick(int life, Point2D posit) {
		super(posit);
		this.life = life;
	}

	public MultipleLifeBrick(Point2D posit) {
		this(defaultLife, posit);
	}

	ImageIcon Brick = new ImageIcon(getClass().getResource("puzzlepack/element_green_rectangle.png"));
	Image temp = Brick.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Brick2 = new ImageIcon(temp2);
	JLabel DisBrick = new JLabel(Brick2);

	@Override
	public boolean checkCol(Ball b) {

		if (checkXYcol(b)) {
			b.velocity.x = -b.velocity.x;
			b.velocity.y = -b.velocity.y;
			--this.life;
			if (this.life == 0)
				this.alive = false;
			return true;
		} else if (checkXcol(b)) {
			b.velocity.x = -b.velocity.x;
			--this.life;
			if (this.life == 0)
				this.alive = false;
			return true;
		} else if (checkYcol(b)) {
			b.velocity.y = -b.velocity.y;
			--this.life;
			if (this.life == 0)
				this.alive = false;
			return true;
		}
		return false;

	}
}

class HOSBrick extends Brick {
	public HOSBrick pair;
	public static int Pairnum = 0;
	int id;

	public HOSBrick(Point2D posit) {
		super(posit);
		this.pair = null;
	}

	public void entangle(HOSBrick B1, HOSBrick B2) throws Exception {
		BufferedImage temp = ImageIO.read(getClass().getResource("puzzlepack/element_blue_rectangle.png"));
		BufferedImage image1 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(size.x / (double) temp.getWidth(), size.y / (double) temp.getHeight());
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image1 = scaleOp.filter(temp, image1);
		BufferedImage image2 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		BufferedImage image3 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image3.createGraphics();
		Graphics2D g1 = image2.createGraphics();
		g1.setFont(new Font("Arial", Font.PLAIN, 72));
		g1.drawString(Integer.toString(Pairnum),
				(size.x - g1.getFontMetrics().stringWidth(Integer.toString(Pairnum))) / 2,
				g1.getFontMetrics().getAscent());
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, (image1.getWidth() - image2.getWidth()) / 2, 0, null);
		g1.dispose();
		g.dispose();
		B1.DisBrick = new JLabel(new ImageIcon(image3));
		B2.DisBrick = new JLabel(new ImageIcon(image3));
		B1.pair = B2;
		B2.pair = B1;
		++Pairnum;
	}

	public boolean checkCol(Ball b) {
		if (checkXYcol(b) || checkXcol(b) || checkYcol(b)) {
			this.alive = false;
			this.pair.alive = false;
			b.posit.x = this.pair.posit.x + 0.5 * this.size.x;
			b.posit.y = this.pair.posit.y + 0.5 * this.size.y;
			return true;
		}
		return false;
	}
}

class RefractiveBrick extends Brick {
	public static double defaultRefractiveIndex = 1.5;
	public double RefractiveIndex;

	ImageIcon Brick = new ImageIcon(getClass().getResource("puzzlepack/element_purple_rectangle.png"));
	Image temp = Brick.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Brick2 = new ImageIcon(temp2);
	JLabel DisBrick = new JLabel(Brick2);

	public RefractiveBrick(double RefractiveIndex, Point2D posit) {
		super(posit);
		this.RefractiveIndex = RefractiveIndex;
	}

	public RefractiveBrick(Point2D posit) {
		this(defaultRefractiveIndex, posit);
	}

	public boolean checkCol(Ball b) {
		double speedi, speedf;
		if (checkXYcol(b)) {
			this.alive = false;
			return true;
		} else if (checkXcol(b)) {
			speedi = b.velocity.distance();
			b.velocity.x = b.velocity.x / this.RefractiveIndex;
			speedf = b.velocity.distance();
			b.velocity.x *= speedi / speedf;
			b.velocity.y *= speedi / speedf;
			this.alive = false;
			return true;
		} else if (checkYcol(b)) {
			speedi = b.velocity.distance();
			b.velocity.x = b.velocity.x / this.RefractiveIndex;
			speedf = b.velocity.distance();
			b.velocity.x *= speedi / speedf;
			b.velocity.y *= speedi / speedf;
			this.alive = false;
			return true;
		}
		return false;
	}
}

class SpinBrick extends Brick {
	public static Random RandomAngleGenerator = new Random();

	ImageIcon Brick = new ImageIcon(getClass().getResource("puzzlepack/element_red_rectangle.png"));
	Image temp = Brick.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Brick2 = new ImageIcon(temp2);
	JLabel DisBrick = new JLabel(Brick2);

	public static double spin() {
		return 2.0 * Math.PI * RandomAngleGenerator.nextDouble();
	}

	public SpinBrick(Point2D posit) {
		super(posit);
	}

	public boolean checkCol(Ball b) {
		double speed = b.velocity.distance(), angle = spin();
		if (checkXYcol(b) || checkXcol(b) || checkYcol(b)) {
			this.alive = false;
			b.velocity = new Point2D(speed * Math.cos(angle), speed * Math.sin(angle));
			return true;
		}
		return false;
	}
}