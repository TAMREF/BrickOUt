import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Brick {
	public Point size = new Point(100, 50); // (width, height)
	public Point position;
	ImageIcon Brick = new ImageIcon(getClass().getResource("icons/Brick.png"));
	Image temp = Brick.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Brick2 = new ImageIcon(temp2);
	JLabel DisBrick = new JLabel(Brick2);

	public JLabel add() {
		return DisBrick;
	}

	public Brick(Point position) {
		this.position = position;
	}

	public Brick() {
		this(new Point(0, 0));
	}

	public void godown() {
		position.y = position.y + 50;
	}
}

class MultipleLifeBrick extends Brick {
	public int life;
	public static int defaultLife = 3;

	public MultipleLifeBrick(int life, Point position) {
		super(position);
		this.life = life;
	}

	public MultipleLifeBrick(Point position) {
		this(defaultLife, position);
	}
}

class HOSBrick extends Brick {
	public HOSBrick pair;

	public HOSBrick(Point position) {
		super(position);
		this.pair = null;
	}

	public static void entangle(HOSBrick B1, HOSBrick B2) {
		B1.pair = B2;
		B2.pair = B1;
	}
}

class RefractiveBrick extends Brick {
	public static double defaultRefractiveIndex = 1.5;
	public double RefractiveIndex;

	public RefractiveBrick(double RefractiveIndex, Point position) {
		super(position);
		this.RefractiveIndex = RefractiveIndex;
	}

	public RefractiveBrick(Point position) {
		this(defaultRefractiveIndex, position);
	}
}

class SpinBrick extends Brick {
	public static Random RandomAngleGenerator = new Random();

	public static double spin() {
		return 360.0 * RandomAngleGenerator.nextDouble();
	}

	public SpinBrick(Point position) {
		super(position);
	}
}