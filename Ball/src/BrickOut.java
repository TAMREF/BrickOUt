import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;

public class BrickOut {
	public static void main(String args[]) {
		Point backSize = new Point(100, 100);
		Point buttonSize = new Point(1200, 100);
		MainFrame frame = new MainFrame("BrickOut", new Point(3000, 2000));
		JLabel[][] backLabel = new JLabel[MainFrame.size.x / backSize.x][MainFrame.size.y / backSize.y];
		frame.fill(backSize, backLabel);
		Button st = new Button("Start", buttonSize, new Point(900, 900));
		frame.add(st.buttonLabel, 0);
		for (int tcnt = 0;;) {
			if (tcnt++ == 0) {
				for (JLabel[] i : backLabel)
					for (JLabel j : i)
						j.repaint();
				st.buttonLabel.repaint();
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			if (st.buttonLabel.getModel().isPressed()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
				st.buttonLabel.setVisible(false);
				Point2D initBallPos = new Point2D(1200, 800);
				Point2D initBallVel = new Point2D(5.0, -10.0);
				Ball b = new Ball(initBallPos, initBallVel);
				frame.add(b.DisBall, 0);
				Brick[] bricks = new SpinBrick[10];
				for (int i = 0; i < 10; i++) {
					bricks[i] = new SpinBrick(new Point2D(400 + i * 220, 200));
					frame.add(((SpinBrick) bricks[i]).disBrick, 0);
				}
				int score = 0;
				double theta = Math.atan(b.velocity.y / b.velocity.x);
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
				Bar A = new Bar(new Point(1350, 1200));
				frame.add(A.DisBar, 0);
				Button ps = new Button("Pause", new Point(300, 100), new Point(2600, 300));
				frame.add(ps.buttonLabel, 0);
				for (int cnt = 0;;) {
					if (cnt++ == 0)
						ps.buttonLabel.repaint();
					if (ps.buttonLabel.getModel().isPressed()) {
						Button rs = new Button("Resume", buttonSize, new Point(900, 900));
						frame.add(rs.buttonLabel, 0);
						for (int i = 0;; i++) {
							if (i == 0)
								rs.buttonLabel.repaint();
							if (rs.buttonLabel.getModel().isPressed()) {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									Thread.currentThread().interrupt();
									e.printStackTrace();
								}
								rs.buttonLabel.setVisible(false);
								break;
							}
						}
					}
					b.update();
					for (Brick i : bricks)
						if (i.alive) {
							boolean flag = false;
							if (i instanceof HOSBrick)
							{
								flag = ((HOSBrick) i).update(b, cnt);
								((HOSBrick) i).disBrick.repaint();
							}
							else if (i instanceof MultipleLifeBrick)
							{
								flag = ((MultipleLifeBrick) i).update(b, cnt);
								((MultipleLifeBrick) i).disBrick.repaint();
							}
							else if (i instanceof RefractiveBrick)
							{
								flag = ((RefractiveBrick) i).update(b, cnt);
								((RefractiveBrick) i).disBrick.repaint();
							}
							else if (i instanceof SpinBrick)
							{
								flag = ((SpinBrick) i).update(b, cnt);
								((SpinBrick) i).disBrick.repaint();
							}
							if (flag)
								Score.setText("Score: " + (score += (i instanceof HOSBrick ? 200 : 100)));
						}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
					A.update(b);
					theta = Math.atan(b.velocity.y / b.velocity.x);
					Theta.setText("Theta :" + theta);
				}
			}
		}
	}
}