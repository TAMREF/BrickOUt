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

	static Brick[][] set = new Brick[5][];
	static int[] max = new int[5];

	public static void init() {
		set[0] = new Brick[10];
		max[0] = 1000;
		for (int i = 0; i < 10; i++)
			set[0][i] = new Brick(new Point2D(400 + i * 220, 200));
		set[1] = new SpinBrick[10];
		max[1] = 1000;
		for (int i = 0; i < 10; i++)
			set[1][i] = new SpinBrick(new Point2D(400 + i * 220, 200));
		set[2] = new MultipleLifeBrick[10];
		max[2] = 1000;
		for (int i = 0; i < 10; i++)
			set[2][i] = new MultipleLifeBrick(new Point2D(400 + i * 220, 200));
		set[3] = new RefractiveBrick[10];
		max[3] = 1000;
		for (int i = 0; i < 10; i++)
			set[3][i] = new RefractiveBrick(new Point2D(400 + i * 220, 200));
		set[4] = new HOSBrick[10];
		max[4] = 1000;
		for (int i = 0; i < 10; i++)
			set[4][i] = new HOSBrick(new Point2D(400 + i * 220, 200));

	}

	public static void stageInit(MainFrame frame, int stage) {
		for (Brick i : set[stage])
			if (i instanceof SpinBrick)
				frame.add(((SpinBrick) i).disBrick, 0);
			else if (i instanceof MultipleLifeBrick)
				frame.add(((MultipleLifeBrick) i).disBrick, 0);
			else if (i instanceof HOSBrick)
				frame.add(((HOSBrick) i).disBrick, 0);
			else if (i instanceof RefractiveBrick)
				frame.add(((RefractiveBrick) i).disBrick, 0);
			else
				frame.add(i.disBrick, 0);
	}

	public static void main(String args[]) {
		Point backSize = new Point(100, 100);
		Point buttonSize = new Point(1200, 100);
		MainFrame frame = new MainFrame("BrickOut", new Point(3000, 2000));
		JLabel[][] backLabel = new JLabel[MainFrame.size.x / backSize.x][MainFrame.size.y / backSize.y];
		frame.fill(backSize, backLabel);
		Button st = new Button("Start", buttonSize, new Point(900, 900));
		frame.add(st.buttonLabel, 0);
		for (JLabel[] i : backLabel)
			for (JLabel j : i)
				j.repaint();
		st.buttonLabel.repaint();
		while (!st.buttonLabel.getModel().isPressed())
			sleep(25);
		sleep(100);
		st.buttonLabel.setVisible(false);
		init();
		int maxStage = 5;
		for (int stage = 0; stage < maxStage; stage++) {
			Point2D initBallPos = new Point2D(1200, 800);
			Point2D initBallVel = new Point2D(5.0, -10.0);
			Ball ball = new Ball(initBallPos, initBallVel);
			frame.add(ball.DisBall, 0);
			stageInit(frame, stage);
			Brick[] bricks = set[stage];
			int score = 0;
			Label scoreLabel = new Label(new Point(100, 100), "Score :" + 0);
			frame.add(scoreLabel.label, 0);
			Label thetaLabel = new Label(new Point(100, 200), "Theta :" + 0);
			frame.add(thetaLabel.label, 0);
			Bar bar = new Bar(new Point(1350, 1200));
			frame.add(bar.DisBar, 0);
			Button ps = new Button("Pause", new Point(300, 100), new Point(2600, 300));
			frame.add(ps.buttonLabel, 0);
			ps.buttonLabel.repaint();
			boolean cont = true;
			for (int cnt = 0; cont; cnt++) {
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
				ball.update();
				for (Brick i : bricks)
					if (i.alive) {
						boolean flag = false;
						if (i instanceof HOSBrick) {
							flag = ((HOSBrick) i).update(ball, cnt);
							((HOSBrick) i).disBrick.repaint();
						} else if (i instanceof MultipleLifeBrick) {
							flag = ((MultipleLifeBrick) i).update(ball, cnt);
							((MultipleLifeBrick) i).disBrick.repaint();
						} else if (i instanceof RefractiveBrick) {
							flag = ((RefractiveBrick) i).update(ball, cnt);
							((RefractiveBrick) i).disBrick.repaint();
						} else if (i instanceof SpinBrick) {
							flag = ((SpinBrick) i).update(ball, cnt);
							((SpinBrick) i).disBrick.repaint();
						} else {
							flag = i.update(ball, cnt);
							i.disBrick.repaint();
						}
						if (flag)
							scoreLabel.label.setText("Score: " + (score += (i instanceof HOSBrick ? 200 : 100)));
					}
				bar.update(ball);
				thetaLabel.label.setText("Theta :" + Math.atan(ball.velocity.y / ball.velocity.x));
				sleep(20);
				if (score == max[stage])
					if (stage != maxStage - 1) {
						Label winLabel = new Label(new Point(950, 700), new Point(1100, 100),
								"You Won! Continue To Next Stage.");
						frame.add(winLabel.label, 0);
						Button contButton = new Button("Continue", new Point(500, 100), new Point(1250, 1000));
						frame.add(contButton.buttonLabel, 0);
						winLabel.label.repaint();
						contButton.buttonLabel.repaint();
						for (; cont;) {
							if (contButton.buttonLabel.getModel().isPressed()) {
								sleep(100);
								winLabel.label.setVisible(false);
								contButton.buttonLabel.setVisible(false);
								bar.DisBar.setVisible(false);
								ball.DisBall.setVisible(false);
								scoreLabel.label.setVisible(false);
								thetaLabel.label.setVisible(false);
								cont = false;
							}
						}
					} else {
						Label winLabel = new Label(new Point(950, 700), new Point(1100, 100),
								"Congratulations! You Cleared The Game!");
						frame.add(winLabel.label, 0);
						winLabel.label.repaint();
						cont = false;
					}
			}
		}
	}
}