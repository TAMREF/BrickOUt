import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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