import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Brick {
	public Point size = new Point(200, 100); // (width, height)
	public Point2D posit;
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
		posit.y += 5.0;
	}

	public Brick() {
		this(new Point2D(0, 0));
	}

	public Brick(Point2D posit) {
		this.posit = posit;
	}
	protected boolean checkYcol(Ball b) { //checks whether velocity.y should be flipped
		return ( b.posit.y + b.diameter >= this.posit.y && b.posit.y <= this.posit.y || b.posit.y >= this.posit.y + this.size.y && b.posit.y <= this.posit.y + this.size.y + b.diameter) &&
				(b.posit.x >= this.posit.x && b.posit.x + b.diameter <= this.posit.x + this.size.x);
	}
	
	protected boolean checkXcol(Ball b) { //checks whether velocity.x should be flipped
		return ( b.posit.x + b.diameter >= this.posit.x && b.posit.x <= this.posit.x || b.posit.x >= this.posit.x + this.size.x && b.posit.x <= this.posit.x + this.size.x + b.diameter) &&
				(b.posit.y >= this.posit.y && b.posit.y + b.diameter <= this.posit.y + this.size.y);
	}
	protected boolean checkXYcol(Ball b) { //checks whether both vel.x and vel.y should be flipped
		Point2D tp;
		int cnt = 0;
		tp = new Point2D(b.posit.x, b.posit.y); //LU
		if( tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y && tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; //RU
		if( tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y && tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x -= b.diameter;
		tp.y += b.diameter; //LD
		if( tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y && tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; //RD
		if( tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y && tp.y <= this.posit.y + this.size.y)
			++cnt;
		
		if(cnt == 1) return true;
		return false;
	}
	public boolean checkCol(Ball b) {
		
		if (checkXYcol(b)) {
			System.out.println("XYcol");
			 b.velocity.x = -b.velocity.x;
			 b.velocity.y = -b.velocity.y;
			 this.alive = false;
			 return true;
		}
		else if(checkXcol(b)) {
			System.out.println("Xcol");
			b.velocity.x = -b.velocity.x;
			this.alive = false;
			return true;
		}
		else if(checkYcol(b)) {
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

	@Override
	public boolean checkCol(Ball b) {
		
		if (checkXYcol(b)) {
			 b.velocity.x = -b.velocity.x;
			 b.velocity.y = -b.velocity.y;
			 --this.life;
			 if(this.life == 0) this.alive = false;
			 return true;
		}
		else if(checkXcol(b)) {
			b.velocity.x = -b.velocity.x;
			--this.life;
			if(this.life == 0) this.alive = false;
			return true;
		}
		else if(checkYcol(b)) {
			b.velocity.y = -b.velocity.y;
			--this.life;
			if(this.life == 0) this.alive = false;
			return true;
		}
		return false;

	}
}

class HOSBrick extends Brick {
	public HOSBrick pair;

	public HOSBrick(Point2D posit) {
		super(posit);
		this.pair = null;
	}

	public static void entangle(HOSBrick B1, HOSBrick B2) {
		B1.pair = B2;
		B2.pair = B1;
	}
	public boolean checkCol(Ball b) {
		if(checkXYcol(b) || checkXcol(b) || checkYcol(b)) {
			this.alive = false;
			this.pair.alive = false;
			b.posit.x = this.pair.posit.x + 0.5 * this.pair.size.x;
			b.posit.y = this.pair.posit.y + 0.5 * this.pair.size.y;
			return true;
		}
		return false;
	}
}

class RefractiveBrick extends Brick {
	public static double defaultRefractiveIndex = 1.5;
	public double RefractiveIndex;

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
		}
		else if(checkXcol(b)) {
			speedi = b.velocity.distance();
			b.velocity.x = b.velocity.x / this.RefractiveIndex;
			speedf = b.velocity.distance();
			b.velocity.x *= speedi / speedf;
			b.velocity.y *= speedi / speedf;
			this.alive = false;
			return true;
		}
		else if(checkYcol(b)) {
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

	public static double spin() {
		return 2.0 * Math.PI * RandomAngleGenerator.nextDouble();
	}

	public SpinBrick(Point2D posit) {
		super(posit);
	}

	public boolean checkCol(Ball b) {
		double speed = b.velocity.distance(), angle = spin();
		if(checkXYcol(b) || checkXcol(b) || checkYcol(b)) {
			this.alive = false;
			b.velocity = new Point2D( speed * Math.cos(angle), speed * Math.sin(angle) );
			return true;
		}
		return false;
	}
}