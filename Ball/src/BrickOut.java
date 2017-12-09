import java.awt.Point;
import javax.swing.JLabel;

public class BrickOut {
	public static void sleep(int cnt) {
		try {
			Thread.sleep(cnt);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

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
			sleep(25);
			if (st.buttonLabel.getModel().isPressed()) {
				sleep(100);
				st.buttonLabel.setVisible(false);
				Point2D initBallPos = new Point2D(1200, 800);
				Point2D initBallVel = new Point2D(5.0, -10.0);
				Ball b = new Ball(initBallPos, initBallVel);
				frame.add(b.DisBall, 0);
				Brick[] bricks = new Brick[10];
				for (int i = 0; i < 10; i++) {
					bricks[i] = new Brick(new Point2D(400 + i * 220, 200));
					frame.add(((Brick) bricks[i]).disBrick, 0);
				}
				int score = 0;
				Label scoreLabel = new Label(new Point(100, 100), "Score :" + 0);
				frame.add(scoreLabel.label, 0);
				Label thetaLabel = new Label(new Point(100, 200), "Theta :" + 0);
				frame.add(thetaLabel.label, 0);
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
								sleep(100);
								rs.buttonLabel.setVisible(false);
								break;
							}
						}
					}
					b.update();
					for (Brick i : bricks)
						if (i.alive) {
							boolean flag = false;
							if (i instanceof HOSBrick) {
								flag = ((HOSBrick) i).update(b, cnt);
								((HOSBrick) i).disBrick.repaint();
							} else if (i instanceof MultipleLifeBrick) {
								flag = ((MultipleLifeBrick) i).update(b, cnt);
								((MultipleLifeBrick) i).disBrick.repaint();
							} else if (i instanceof RefractiveBrick) {
								flag = ((RefractiveBrick) i).update(b, cnt);
								((RefractiveBrick) i).disBrick.repaint();
							} else if (i instanceof SpinBrick) {
								flag = ((SpinBrick) i).update(b, cnt);
								((SpinBrick) i).disBrick.repaint();
							} else {
								flag = i.update(b, cnt);
								i.disBrick.repaint();
							}
							if (flag)
								scoreLabel.label.setText("Score: " + (score += (i instanceof HOSBrick ? 200 : 100)));
						}
					A.update(b);
					thetaLabel.label.setText("Theta :" + Math.atan(b.velocity.y / b.velocity.x));
					sleep(20);
				}
			}
		}
	}
}