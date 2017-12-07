import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;

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
			R[i] = new Brick(new Point2D(400 + i * 220, 0));
			temp[i] = new JLabel();
			temp[i] = R[i].add();
			temp[i].setLocation(R[i].position.topoint());
			temp[i].setSize(R[i].size.x, R[i].size.y);
			Frame.add(temp[i]);
		}
		int cnt = 0;
		int score = 0;
		JLabel Score = new JLabel();
		Score.setFont(new Font("Serif", Font.PLAIN, 72));
		Score.setLocation(new Point(100, 100));
		Score.setSize(500, 100);
		Frame.add(Score);
		Score.setText("Score :" + score);
		Bar A = new Bar(new Point(1350, 1200));
		JLabel DisBar = new JLabel();
		DisBar = A.add();
		DisBar.setLocation(A.position);
		DisBar.setSize(A.size.x, A.size.y);
		Frame.add(DisBar);
		while (true) {
			B.position.x = B.position.x + B.velocity.x;
			B.position.y = B.position.y + B.velocity.y;
			icon.setLocation(B.position.topoint());
			Score.setLocation(new Point(100, 100));
			B.checkCol(frameSize);
			for (int i = 0; i < 10; i++) {
				if (R[i].alive && R[i].checkCol(B)) {
					R[i].position.x = frameSize.x;
					R[i].alive = false;
					Score.setText("Score: " + (score += 100));
				}
				temp[i].setLocation(R[i].position.topoint());
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			if (MainFrame.move == 1)
				A.moveLeft();
			else if (MainFrame.move == 2)
				A.moveRight(frameSize);
			A.position.x += cnt % 2 == 0 ? 1 : -1;
			DisBar.setLocation(A.position);
			if (A.checkCol(B))
				B.velocity.y = -B.velocity.y;
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