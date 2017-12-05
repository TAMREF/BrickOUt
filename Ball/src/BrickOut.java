import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Ball {
	int radius = 100;
	Point velocity;
	Point position;
	ImageIcon Ball = new ImageIcon(getClass().getResource("icons/Ball.png"));
	Image temp = Ball.getImage();
	Image temp2 = temp.getScaledInstance(radius, radius, Image.SCALE_SMOOTH);
	ImageIcon Ball2 = new ImageIcon(temp2);
	JLabel DisBall = new JLabel(Ball2);

	public void checkCol(Point b) {
		if (position.x <= 0)
			velocity.x = Math.abs(velocity.x);
		if (position.x + radius >= b.x)
			velocity.x = -Math.abs(velocity.x);
		if (position.y <= 0)
			velocity.y = Math.abs(velocity.y);
		if (position.y + radius >= b.y)
			velocity.y = -Math.abs(velocity.y);
	}

	public boolean checkCol(Brick b) {
		if (position.x >= b.position.x && position.y >= b.position.y)
			if (position.x <= b.position.x + b.size.x && position.y <= b.position.y + b.size.y)
				return true;
		if (position.x + radius >= b.position.x && position.y >= b.position.y)
			if (position.x + radius <= b.position.x + b.size.x && position.y <= b.position.y + b.size.y)
				return true;
		if (position.x + radius >= b.position.x && position.y + radius >= b.position.y)
			if (position.x + radius <= b.position.x + b.size.x && position.y + radius <= b.position.y + b.size.y)
				return true;
		if (position.x >= b.position.x && position.y + radius >= b.position.y)
			if (position.x <= b.position.x + b.size.x && position.y + radius <= b.position.y + b.size.y)
				return true;
		return false;
	}

	public JLabel add() {
		return DisBall;
	}

	Ball(Point pos, Point vel) {
		this.position = pos;
		this.velocity = vel;
	}

	Ball() {
		this(new Point(0, 0), new Point(0, 0));
	}

}

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
		Point frameSize = new Point(3000, 2000);
		MainFrame Frame = new MainFrame("BrickOut", frameSize);
		Point initBallPos = new Point(500, 500);
		Point initBallVel = new Point(15, 10);
		Ball B = new Ball(initBallPos, initBallVel);
		JLabel icon = B.add();
		icon.setLocation(B.position);
		icon.setSize(B.radius, B.radius);
		Frame.add(icon);
		Brick[] R = new Brick[10];
		JLabel[] temp = new JLabel[10];
		Boolean[] Ex = new Boolean[10];
		for (int i = 0; i < 10; i++) {
			Ex[i] = true;
			R[i] = new Brick(new Point(1000 + i * 120, 1000));
			temp[i] = new JLabel();
			temp[i] = R[i].add();
			temp[i].setLocation(R[i].position);
			temp[i].setSize(R[i].size.x, R[i].size.y);
			Frame.add(temp[i]);
		}
		// Frame.setVisible(true);
		// icon.setVisible(true);
		while (true) {
			B.checkCol(frameSize);
			B.position.x = B.position.x + B.velocity.x;
			B.position.y = B.position.y + B.velocity.y;
			icon.setLocation(B.position);
			B.checkCol(frameSize);
			for (int i = 0; i < 10; i++) {
				if (B.checkCol(R[i]) && Ex[i]) {
					B.velocity.x = -B.velocity.x;
					B.velocity.y = -B.velocity.y;
					Ex[i] = false;
				} else if (Ex[i]) {
					temp[i].setLocation(R[i].position);
					System.out.println("No." + (i + 1) + " is here");
				} else
					System.out.println("It isn't there");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			System.out.println(B.position);
			// System.out.println("Running...");
		}
	}
}