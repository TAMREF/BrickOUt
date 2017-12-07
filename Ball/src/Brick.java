import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Brick {
	public Point size = new Point(100, 50); // (width, height)
	public Point2D position;
	ImageIcon Brick = new ImageIcon(getClass().getResource("icons/Brick.png"));
	Image temp = Brick.getImage();
	Image temp2 = temp.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
	ImageIcon Brick2 = new ImageIcon(temp2);
	JLabel DisBrick = new JLabel(Brick2);
	boolean alive = true;
	
	public JLabel add() {
		return DisBrick;
	}
	public void godown() {

	}
	public Brick() {
		this(new Point2D(0,0));
	}
	public Brick(Point2D position) {
		this.position = position;
	}
	public boolean checkCol(Ball b) {
		if (b.position.y <= this.position.y + this.size.y && b.position.y + b.diameter >= this.position.y) {
			if ((b.position.x >= this.position.x && b.position.x <= this.position.x + this.size.x)
					|| (b.position.x + b.diameter >= this.position.x && b.position.x + b.diameter <= this.position.x + this.size.x)) {
				b.velocity.x = -b.velocity.x;
				System.out.println("xcol");
				this.alive = false;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.y = -b.velocity.y;
				System.out.println("ycol");
				this.alive = false;
			}
		}
		return !this.alive;
	}
}

class MultipleLifeBrick extends Brick {
	public int life;
	public static int defaultLife = 3;

	public MultipleLifeBrick(int life, Point2D position) {
		super(position);
		this.life = life;
	}

	public MultipleLifeBrick(Point2D position) {
		this(defaultLife, position);
	}
	@Override
	public boolean checkCol(Ball b) {
		boolean flag = false;
		if (b.position.y <= this.position.y + this.size.y && b.position.y + b.diameter >= this.position.y) {
			if ((b.position.x >= this.position.x && b.position.x <= this.position.x + this.size.x)
					|| (b.position.x + b.diameter >= this.position.x && b.position.x + b.diameter <= this.position.x + this.size.x)) {
				b.velocity.x = -b.velocity.x;
				flag = true;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.y = -b.velocity.y;
				flag = true;
			}
		}
		if(flag) --this.life;
		if(this.life == 0) this.alive = false;
		return flag;
	}
}

class HOSBrick extends Brick {
	public HOSBrick pair;

	public HOSBrick(Point2D position) {
		super(position);
		this.pair = null;
	}

	public static void entangle(HOSBrick B1, HOSBrick B2) {
		B1.pair = B2;
		B2.pair = B1;
	}
	public boolean checkCol(Ball b) {
		boolean flag = false;
		if (b.position.y <= this.position.y + this.size.y && b.position.y + b.diameter >= this.position.y) {
			if ((b.position.x >= this.position.x && b.position.x <= this.position.x + this.size.x)
					|| (b.position.x + b.diameter >= this.position.x && b.position.x + b.diameter <= this.position.x + this.size.x)) {
				flag = true;
				this.alive = false;
				this.pair.alive = false;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.y = -b.velocity.y;
				flag = true;
				this.alive = false;
				this.pair.alive = false;
			}
		}
		if(flag) {
			b.position.x = this.pair.position.x + this.pair.size.x/2;
			b.position.y = this.pair.position.y + this.pair.size.y/2;
		}
		return !this.alive;
	}
}

class RefractiveBrick extends Brick {
	public static double defaultRefractiveIndex = 1.5;
	public double RefractiveIndex;

	public RefractiveBrick(double RefractiveIndex, Point2D position) {
		super(position);
		this.RefractiveIndex = RefractiveIndex;
	}

	public RefractiveBrick(Point2D position) {
		this(defaultRefractiveIndex, position);
	}
	public boolean checkCol(Ball b) {
		double speedi, speedf;
		if (b.position.y <= this.position.y + this.size.y && b.position.y + b.diameter >= this.position.y) {
			if ((b.position.x >= this.position.x && b.position.x <= this.position.x + this.size.x)
					|| (b.position.x + b.diameter >= this.position.x && b.position.x + b.diameter <= this.position.x + this.size.x)) {
				
				speedi = b.velocity.distance();
				b.velocity.x = b.velocity.x / this.RefractiveIndex;
				speedf = b.velocity.distance();
				b.velocity.x *= speedf / speedi;
				b.velocity.y *= speedf / speedi;
				this.alive = false;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.y = b.velocity.y;
				this.alive = false;
			}
		}
		return !this.alive;
	}
}

class SpinBrick extends Brick {
	public static Random RandomAngleGenerator = new Random();

	public static double spin() {
		return 2.0 * Math.PI * RandomAngleGenerator.nextDouble();
	}

	public SpinBrick(Point2D position) {
		super(position);
	}
	public boolean checkCol(Ball b) {
		double speed = b.velocity.distance(), angle = spin();
		if (b.position.y <= this.position.y + this.size.y && b.position.y + b.diameter >= this.position.y) {
			if ((b.position.x >= this.position.x && b.position.x <= this.position.x + this.size.x)
					|| (b.position.x + b.diameter >= this.position.x && b.position.x + b.diameter <= this.position.x + this.size.x)) {
				b.velocity.x = speed * Math.cos(angle);
				b.velocity.y = speed * Math.sin(angle);
				this.alive = false;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.x = speed * Math.cos(angle);
				b.velocity.y = speed * Math.sin(angle);
				this.alive = false;
			}
		}
		return !this.alive;
	}
}