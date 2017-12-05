import javax.swing.*;
import java.awt.*;

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
		if (position.x < radius)
			velocity.x = Math.abs(velocity.x);
		if (position.x + radius > b.x)
			velocity.x = -Math.abs(velocity.x);
		if (position.y < radius)
			velocity.y = Math.abs(velocity.y);
		if (position.y + radius > b.y)
			velocity.y = -Math.abs(velocity.y);
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
		Point frameSize = new Point(2000, 1500);
		MainFrame Frame = new MainFrame("BrickOut", frameSize);
		Point initBallPos = new Point(1500, 1000);
		Point initBallVel = new Point(30, -20);
		Ball B = new Ball(initBallPos, initBallVel);
		JLabel icon = B.add();
		icon.setLocation(B.position);
		icon.setSize(B.radius, B.radius);
		Frame.add(icon);
		// Frame.setVisible(true);
		// icon.setVisible(true);
		while (true) {
			B.checkCol(frameSize);
			B.position.x = B.position.x + B.velocity.x;
			B.position.y = B.position.y + B.velocity.y;
			icon.setLocation(B.position);
			B.checkCol(frameSize);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			System.out.println(B.position);
			// System.out.println("Running...");
		}
	}
}