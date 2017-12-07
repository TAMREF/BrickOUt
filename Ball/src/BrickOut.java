import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Point2D{
	public double x;
	public double y;
	Point2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	Point2D(){
		this(0.0, 0.0);
	}
	double distance() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	Point topoint() {
		return new Point((int)(this.x), (int)(this.y));
	}
}
class Ball {
	int diameter = 40;
	Point2D velocity;
	Point2D position;
	ImageIcon Ball = new ImageIcon(getClass().getResource("icons/Ball.png"));
	Image temp = Ball.getImage();
	Image temp2 = temp.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
	ImageIcon Ball2 = new ImageIcon(temp2);
	JLabel DisBall = new JLabel(Ball2);
	
	public void checkCol(Point b) {
		if (position.x <= 0)
			velocity.x = Math.abs(velocity.x);
		if (position.x + diameter >= b.x)
			velocity.x = -Math.abs(velocity.x);
		if (position.y <= 0)
			velocity.y = Math.abs(velocity.y);
		if (position.y + diameter >= b.y)
			velocity.y = -Math.abs(velocity.y);
	}

	public boolean checkCol(Brick b) {
		if (position.x >= b.position.x && position.y >= b.position.y)
			if (position.x <= b.position.x + b.size.x && position.y <= b.position.y + b.size.y)
				return true;
		if (position.x + diameter >= b.position.x && position.y >= b.position.y)
			if (position.x + diameter <= b.position.x + b.size.x && position.y <= b.position.y + b.size.y)
				return true;
		if (position.x + diameter >= b.position.x && position.y + diameter >= b.position.y)
			if (position.x + diameter <= b.position.x + b.size.x && position.y + diameter <= b.position.y + b.size.y)
				return true;
		if (position.x >= b.position.x && position.y + diameter >= b.position.y)
			if (position.x <= b.position.x + b.size.x && position.y + diameter <= b.position.y + b.size.y)
				return true;
		return false;
	}

	public Boolean checkCol(Bar b) {
		if (position.x >= b.position.x && position.y >= b.position.y)
			if (position.x <= b.position.x + b.size.x && position.y <= b.position.y + b.size.y)
				return true;
		if (position.x + diameter >= b.position.x && position.y >= b.position.y)
			if (position.x + diameter <= b.position.x + b.size.x && position.y <= b.position.y + b.size.y)
				return true;
		if (position.x + diameter >= b.position.x && position.y + diameter >= b.position.y)
			if (position.x + diameter <= b.position.x + b.size.x && position.y + diameter <= b.position.y + b.size.y)
				return true;
		if (position.x >= b.position.x && position.y + diameter >= b.position.y)
			if (position.x <= b.position.x + b.size.x && position.y + diameter <= b.position.y + b.size.y)
				return true;
		return false;
	}

	public JLabel add() {
		return DisBall;
	}

	Ball(Point2D pos, Point2D vel) {
		this.position = pos;
		this.velocity = vel;
	}

}

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
				this.alive = false;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.y = -b.velocity.y;
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
		if (b.position.y <= this.position.y + this.size.y && b.position.y + b.diameter >= this.position.y) {
			if ((b.position.x >= this.position.x && b.position.x <= this.position.x + this.size.x)
					|| (b.position.x + b.diameter >= this.position.x && b.position.x + b.diameter <= this.position.x + this.size.x)) {
				b.position.x = this.pair.position.x + this.pair.size.x/2;
				b.position.y = this.pair.position.y + this.pair.size.y/2;
				
				this.alive = false;
				this.pair.alive = false;
			}
		}
		if (b.position.x <= this.position.x + this.size.x && b.position.x + b.diameter >= this.position.x) {
			if ((b.position.y >= this.position.y && b.position.y <= this.position.y + this.size.y)
					|| (b.position.y + b.diameter >= this.position.y && b.position.y + b.diameter <= this.position.y + this.size.y)) {
				b.velocity.y = -b.velocity.y;
				b.position.x = this.pair.position.x + this.pair.size.x/2;
				b.position.y = this.pair.position.y + this.pair.size.y/2;
				this.alive = false;
				this.pair.alive = false;
			}
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

class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2801274722167755407L;

	MainFrame(String Title, Point p) {
		setTitle(Title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(p.x, p.y);
		setVisible(true);
	}

	MainFrame() {
		this("Default Title", new Point(2000, 2000));
	}
}

public class BrickOut {
	public static void main(String args[]) {
		Point frameSize = new Point(2400, 1600);
		/*KeyListener klis = new KeyListener() {
			public void keyTyped(KeyEvent KE) {
				System.out.println("Typed");
			}

			public void keyReleased(KeyEvent KE) {
				System.out.println("Released");
			}

			public void keyPressed(KeyEvent KE) {
				System.out.println("Pressed");
			}
		};*/
		MainFrame Frame = new MainFrame("BrickOut", frameSize);
		Point2D initBallPos = new Point2D(500.0, 500.0);
		Point2D initBallVel = new Point2D(30, -20);
		Ball B = new Ball(initBallPos, initBallVel);
		JLabel icon = B.add();
		icon.setLocation(B.position.topoint());
		icon.setSize(B.diameter, B.diameter);
		Frame.add(icon);
		Brick[] R = new Brick[10];
		JLabel[] temp = new JLabel[10];
		//Boolean[] Ex = new Boolean[10];
		for (int i = 0; i < 10; i++) {
			//Ex[i] = true;
			R[i] = new Brick(new Point2D(1000.0 + i * 120, 1000.0));
			temp[i] = new JLabel();
			temp[i] = R[i].add();
			temp[i].setLocation(R[i].position.topoint());
			temp[i].setSize(R[i].size.x, R[i].size.y);
			Frame.add(temp[i]);
		}
		/*Bar A = new Bar(new Point(1500, 1500));
		JLabel DisBar = new JLabel();
		DisBar = A.add();
		DisBar.setLocation(A.position);
		DisBar.setSize(A.size.x, A.size.y);
		Frame.add(DisBar);*/
		// Frame.setVisible(true);
		// icon.setVisible(true);
		while (true) {
			B.checkCol(frameSize);
			B.position.x = B.position.x + B.velocity.x;
			B.position.y = B.position.y + B.velocity.y;
			icon.setLocation(B.position.topoint());
			B.checkCol(frameSize);
			for(int i=0;i<10;i++) {
				if( R[i].alive ) {
					if( R[i].checkCol(B) ) {
						System.out.println("Collision with No. "+(i+1)+" occurred");
					}else {
						temp[i].setLocation(R[i].position.topoint());
						//System.out.println("No. "+(i+1)+" is here.");
					}
				}else {
					//System.out.println("No. "+(i+1)+" isn't here.");
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			/*if (event.getKeyCode() == KeyEvent.VK_LEFT)
				A.moveLeft();
			else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
				A.moveRight();
			if (B.checkCol(A))
				B.velocity.y = Math.abs(B.velocity.y);
			DisBar.setLocation(A.position);*/
			//System.out.println(B.position);
			// System.out.println("Running...");
		}
	}
}