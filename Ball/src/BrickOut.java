import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		Point initBallPos = new Point(1500, 1000);
		Point initBallVel = new Point(15, -10);
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
			R[i] = new Brick(new Point(900 + i * 120, 0));
			temp[i] = new JLabel();
			temp[i] = R[i].add();
			temp[i].setLocation(R[i].position);
			temp[i].setSize(R[i].size.x, R[i].size.y);
			Frame.add(temp[i]);
		}
		int cnt = 0;
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
			icon.setLocation(B.position);
			B.checkCol(frameSize);
			for (int i = 0; i < 10; i++) {
				if (B.checkCol(R[i]) && Ex[i]) {
					B.velocity.x = -B.velocity.x;
					B.velocity.y = -B.velocity.y;
					R[i].position.x += frameSize.x;
					Ex[i] = false;
				}
				temp[i].setLocation(R[i].position);
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
			if (A.checkCol(B))
				B.velocity.y = -B.velocity.y;
			// System.out.println(B.position);
			if (cnt % 100 == 0)
				for (int i = 0; i < 10; i++)
					if (Ex[i]) {
						R[i].godown();
						temp[i].setLocation(R[i].position);
					}
			cnt++;
		}
	}
}