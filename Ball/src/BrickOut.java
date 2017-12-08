import java.awt.Font;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

public class BrickOut {
	public static void main(String args[]) {
		int tcnt = 0;
		Point backSize = new Point(100, 100);
		Background back = new Background(backSize);
		Point frameSize = new Point(3000, 2000);
		Point buttonSize = new Point(1200, 100);
		MainFrame frame = new MainFrame("BrickOut", frameSize);
		JLabel[][] backLabel = new JLabel[frame.size.x / back.size.x][frame.size.y / back.size.y];
		for (int i = 0; i < frameSize.x / backSize.x; i++)
			for (int j = 0; j < frameSize.y / backSize.y; j++) {
				backLabel[i][j] = back.add();
				backLabel[i][j].setLocation(backSize.x * i + 1, backSize.y * j);
				backLabel[i][j].setSize(backSize.x, backSize.y);
				frame.add(backLabel[i][j], 0);
			}
		Boolean startPressed = false;
		Button button = null;
		try {
			button = new Button("Start", buttonSize);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JButton but = null;
		try {
			but = button.add();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		but.setLocation(new Point(899, 900));
		but.setSize(buttonSize.x, buttonSize.y);
		frame.add(but, 0);
		while (true) {
			if (tcnt == 0) {
				for (int i = 0; i < frameSize.x / backSize.x; i++)
					for (int j = 0; j < frameSize.y / backSize.y; j++)
						backLabel[i][j].setLocation(backSize.x * i, backSize.y * j);
				but.setLocation(new Point(900, 900));
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			startPressed = but.getModel().isPressed();
			if (startPressed) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
				but.setVisible(false);
				Point2D initBallPos = new Point2D(1200, 800);
				Point2D initBallVel = new Point2D(3.0, -18.0);
				Ball B = new Ball(initBallPos, initBallVel);
				JLabel icon = B.add();
				icon.setLocation(B.posit.topoint());
				icon.setSize(B.diameter, B.diameter);
				frame.add(icon, 0);
				Brick[] R = new Brick[10];
				JLabel[] temp = new JLabel[10];
				for (int i = 0; i < 10; i++)
					R[i] = new Brick(new Point2D(200 + i * 220, 200));
				/*
				 * for (int i = 0; i < 5; i++) try { ((HOSBrick) R[i]).entangle((HOSBrick) R[i],
				 * (HOSBrick) R[9 - i]); } catch (Exception e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); }
				 */
				for (int i = 0; i < 10; i++) {
					temp[i] = R[i].add();
					temp[i].setLocation(R[i].posit.topoint());
					temp[i].setSize(R[i].size.x, R[i].size.y);
					frame.add(temp[i], 0);
				}
				int cnt = 0;
				int score = 0;
				double theta = Math.atan(B.velocity.y/B.velocity.x);
				JLabel Score = new JLabel();
				Score.setFont(new Font("Serif", Font.PLAIN, 72));
				Score.setLocation(new Point(100, 100));
				Score.setSize(500, 100);
				frame.add(Score, 0);
				Score.setText("Score :" + score);
				JLabel Theta = new JLabel();
				Theta.setFont(new Font("Serif", Font.PLAIN, 72));
				Theta.setLocation(new Point(100, 200));
				Theta.setSize(500, 100);
				frame.add(Theta, 0);
				Theta.setText("Theta :" + theta);
				Bar A = new Bar(new Point(1350, 1200));
				JLabel DisBar = new JLabel();
				DisBar = A.add();
				DisBar.setLocation(A.posit);
				DisBar.setSize(A.size.x, A.size.y);
				frame.add(DisBar, 0);
				while (true) {
					B.checkCol(frameSize);
					B.posit.x = B.posit.x + B.velocity.x;
					B.posit.y = B.posit.y + B.velocity.y;
					icon.setLocation(B.posit.topoint());
					Score.setLocation(new Point(100, 100));
					B.checkCol(frameSize);
					for (int i = 0; i < 10; i++) {
						if (R[i].alive) {
							boolean flag = false;
							if (R[i] instanceof MultipleLifeBrick) {
								flag = ((MultipleLifeBrick) R[i]).checkCol(B);
							} else if (R[i] instanceof HOSBrick) {
								flag = ((HOSBrick) R[i]).checkCol(B);
							} else if (R[i] instanceof RefractiveBrick) {
								flag = ((RefractiveBrick) R[i]).checkCol(B);
							} else if (R[i] instanceof SpinBrick) {
								flag = ((SpinBrick) R[i]).checkCol(B);
							} else {
								flag = R[i].checkCol(B);
							}
							if (flag) {
								System.out.println("Collision with #" + (i + 1) + ", Ball = " + B.posit + ", Brick = "
										+ R[i].posit);
								if (R[i] instanceof HOSBrick)
									Score.setText("Score: " + (score += 200));
								else
									Score.setText("Score: " + (score += 100));
							}
						}
						if (!R[i].alive)
							R[i].posit.x = frameSize.x;
						temp[i].setLocation(R[i].posit.topoint());
					}
					for (int i = 0; i < 10; i++) {
						if (!R[i].alive) {
							R[i].posit.x = 3 * frameSize.x;
						}
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
					if (cnt == 0)
						A.moveLeft();
					if (MainFrame.move == 1)
						A.moveLeft();
					else if (MainFrame.move == 2)
						A.moveRight(frameSize);
					DisBar.setLocation(A.posit);
					A.checkCol(B);
					if (cnt % 100 == 0) {
						for (int i = 0; i < 10; i++) {
							if (R[i].alive) {
								R[i].godown();
								temp[i].setLocation(R[i].posit.topoint());
							}
						}
					}
					theta = Math.atan(B.velocity.y/B.velocity.x);
					Theta.setText("Theta :"+theta);
					cnt++;
				}
			}
		}
	}
}