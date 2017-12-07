import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Point2D {
	public double x;
	public double y;

	Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	Point2D() {
		this(0.0, 0.0);
	}

	double distance() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	Point topoint() {
		return new Point((int) (this.x), (int) (this.y));
	}
}

class MainFrame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2801274722167755407L;
	JTextField tfield = new JTextField();

	MainFrame(String Title, Point p) {
		setTitle(Title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// tfield.addKeyListener(this);
		setLayout(null);
		setSize(p.x, p.y);
		setVisible(true);
	}

	public void keyPressed(KeyEvent a) {
		ShowInfo(a);
	}

	public void keyReleased(KeyEvent a) {
		ShowInfo(a);
	}

	public void keyTyped(KeyEvent a) {
		ShowInfo(a);
	}

	protected void ShowInfo(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 37) {
			System.out.println("left");
		} else if (keyCode == 39) {
			System.out.println("right");
		}
	}

	MainFrame() {
		this("Default Title", new Point(2000, 2000));
	}
}

public class BrickOut {
	public static void main(String args[]) {
		Point frameSize = new Point(3000, 2000);
		MainFrame Frame = new MainFrame("BrickOut", frameSize);
		Point2D initBallPos = new Point2D(1500, 1000);
		Point2D initBallVel = new Point2D(15, -10);
		Ball B = new Ball(initBallPos, initBallVel);
		JLabel icon = B.add();
		icon.setLocation(B.position.topoint());
		icon.setSize(B.diameter, B.diameter);
		Frame.add(icon);
		Brick[] R = new Brick[10];
		JLabel[] temp = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			R[i] = new Brick(new Point2D(900 + i * 120, 0));
			temp[i] = new JLabel();
			temp[i] = R[i].add();
			temp[i].setLocation(R[i].position.topoint());
			temp[i].setSize(R[i].size.x, R[i].size.y);
			Frame.add(temp[i]);
		}
		int cnt = 0;
		int score = 0;
		JLabel Score = new JLabel("Score: 0");
		Score.setFont(new Font("Serif", Font.PLAIN, 72));
		Score.setLocation(new Point(0, 0));
		Score.setSize(300, 100);
		Frame.add(Score);
		Bar A = new Bar(new Point(1350, 1200));
		JLabel DisBar = new JLabel();
		DisBar = A.add();
		DisBar.setLocation(A.position);
		DisBar.setSize(A.size.x, A.size.y);
		Frame.add(DisBar);
		while (true) {
			B.checkCol(frameSize);
			B.position.x = B.position.x + B.velocity.x;
			B.position.y = B.position.y + B.velocity.y;
			icon.setLocation(B.position.topoint());
			Score.setLocation(new Point(0, 0));
			B.checkCol(frameSize);
			for (int i = 0; i < 10; i++) {
				if (R[i].alive && R[i].checkCol(B)) {
					R[i].position.x = frameSize.x;
					R[i].alive = false;
					score += 100;
					Score.setText("Score: " + Integer.toString(score));
				}
				temp[i].setLocation(R[i].position.topoint());
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			if (cnt % 2 == 0)
				A.moveLeft();
			else
				A.moveRight();
			DisBar.setLocation(A.position);
			if (A.checkCol(B)) {
				B.velocity.y = -B.velocity.y;
				B.velocity.x = -B.velocity.x;
			}
			// System.out.println(B.position);
			if (cnt % 100 == 0)
				for (int i = 0; i < 10; i++)
					if (R[i].alive) {
						R[i].godown();
						temp[i].setLocation(R[i].position.topoint());
					}
			cnt++;
		}
	}
}