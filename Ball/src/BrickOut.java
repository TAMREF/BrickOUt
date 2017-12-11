import java.awt.Point;

public class BrickOut {
	public static void sleep(int cnt) {
		try {
			Thread.sleep(cnt);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	static Brick[] bricks = new Brick[10];
	static int[] max = { 1000, 1000, 1000, 1000, 2000 };
	static int cnt = 0;
	static MainFrame frame = new MainFrame("BrickOut", new Point(3000, 2000));
	static Ball ball = null;
	static Bar bar = null;
	static int stage = 0;
	static boolean cont = true;
	static Label scoreLabel;
	static Label thetaLabel;

	public static void init() {
		if (stage == 0) {
			for (int i = 0; i < 10; i++)
				bricks[i] = new Brick(new Point2D(410 + i * 220, 200), true);
		} else if (stage == 1) {
			bricks[0] = new SpinBrick(new Point2D(410, 200));
			bricks[1] = new MultipleLifeBrick(new Point2D(630, 200));
			bricks[2] = new HOSBrick(new Point2D(850, 200));
			bricks[3] = new RefractiveBrick(new Point2D(1070, 200));
			bricks[4] = new Brick(new Point2D(1290, 200), true);
			bricks[5] = new SpinBrick(new Point2D(1510, 200));
			bricks[6] = new MultipleLifeBrick(new Point2D(1730, 200));
			bricks[7] = new HOSBrick(new Point2D(1950, 200));
			bricks[8] = new RefractiveBrick(new Point2D(2170, 200));
			bricks[9] = new Brick(new Point2D(2390, 200), true);
			((HOSBrick) bricks[2]).entangle((HOSBrick) bricks[2], (HOSBrick) bricks[7]);
		} else if (stage == 2) {
			bricks[0] = new Brick(new Point2D(410, 200), true);
			bricks[1] = new Brick(new Point2D(630, 440), true);
			bricks[2] = new HOSBrick(new Point2D(850, 200));
			bricks[3] = new MultipleLifeBrick(new Point2D(1070, 440));
			bricks[4] = new SpinBrick(new Point2D(1290, 200));
			bricks[5] = new HOSBrick(new Point2D(1510, 440));
			bricks[6] = new MultipleLifeBrick(new Point2D(1730, 200));
			bricks[7] = new Brick(new Point2D(1950, 440), true);
			bricks[8] = new Brick(new Point2D(2170, 200), true);
			((HOSBrick) bricks[2]).entangle((HOSBrick) bricks[2], (HOSBrick) bricks[5]);
		} else if (stage == 3) {
			bricks[0] = new Brick(new Point2D(2170, 440), true);
			bricks[1] = new HOSBrick(new Point2D(410, 440));
			bricks[2] = new HOSBrick(new Point2D(630, 320));
			bricks[3] = new HOSBrick(new Point2D(850, 200));
			bricks[4] = new HOSBrick(new Point2D(1070, 320));
			bricks[6] = new HOSBrick(new Point2D(1290, 440));
			bricks[7] = new HOSBrick(new Point2D(1510, 320));
			bricks[8] = new HOSBrick(new Point2D(1730, 200));
			bricks[9] = new HOSBrick(new Point2D(1950, 320));
			((HOSBrick) bricks[1]).entangle((HOSBrick) bricks[1], (HOSBrick) bricks[6]);
			((HOSBrick) bricks[2]).entangle((HOSBrick) bricks[2], (HOSBrick) bricks[7]);
			((HOSBrick) bricks[3]).entangle((HOSBrick) bricks[3], (HOSBrick) bricks[8]);
			((HOSBrick) bricks[4]).entangle((HOSBrick) bricks[4], (HOSBrick) bricks[9]);
		} else if (stage == 4) {
			bricks = new Brick[20];
			bricks[0] = new Brick(new Point2D(410, 200), true);
			bricks[1] = new Brick(new Point2D(410, 320), true);
			bricks[2] = new Brick(new Point2D(410, 440), true);
			bricks[3] = new MultipleLifeBrick(new Point2D(630, 320));
			bricks[4] = new Brick(new Point2D(850, 200), true);
			bricks[5] = new Brick(new Point2D(850, 320), true);
			bricks[6] = new Brick(new Point2D(850, 440), true);
			bricks[7] = new Brick(new Point2D(1050, 200), true);
			bricks[8] = new MultipleLifeBrick(new Point2D(1050, 320));
			bricks[9] = new Brick(new Point2D(1050, 440), true);
			bricks[10] = new Brick(new Point2D(1370, 200), true);
			bricks[11] = new Brick(new Point2D(1370, 440), true);
			bricks[12] = new MultipleLifeBrick(new Point2D(1670, 200));
			bricks[13] = new Brick(new Point2D(1670, 320), true);
			bricks[14] = new Brick(new Point2D(1670, 440), true);
			bricks[15] = new MultipleLifeBrick(new Point2D(1890, 440));
			bricks[16] = new MultipleLifeBrick(new Point2D(2170, 200));
			bricks[17] = new Brick(new Point2D(2170, 320), true);
			bricks[18] = new Brick(new Point2D(2170, 440), true);
			bricks[19] = new MultipleLifeBrick(new Point2D(2390, 440));
		}
	}

	public static void die() {
		Label dead = new Label(new Point(950, 700), new Point(1100, 100), "Oh No! You Died! Restart Or Quit!");
		Button quit = new Button("Quit", new Point(500, 100), new Point(1250, 1200));
		Button restart = new Button("Restart", new Point(500, 100), new Point(1250, 1000));
		while (!quit.button.getModel().isPressed() && !restart.button.getModel().isPressed())
			sleep(25);
		if (quit.button.getModel().isPressed()) {
			sleep(100);
			System.exit(0);
		}
		frame.remove(dead.label);
		frame.remove(quit.button);
		frame.remove(restart.button);
		frame.remove(scoreLabel.label);
		frame.remove(thetaLabel.label);
		frame.remove(bar.disBar);
		frame.remove(ball.disBall);
		for (Brick i : bricks)
			if (i instanceof HOSBrick)
				frame.remove(((HOSBrick) i).disBrick);
			else if (i instanceof MultipleLifeBrick)
				frame.remove(((MultipleLifeBrick) i).disBrick);
			else if (i instanceof RefractiveBrick)
				frame.remove(((RefractiveBrick) i).disBrick);
			else if (i instanceof SpinBrick)
				frame.remove(((SpinBrick) i).disBrick);
			else
				frame.remove(i.disBrick);
		frame.repaint();
		stage = -1;
	}

	public static boolean brickUpdate(Brick i) {
		boolean flag = false;
		if (i instanceof HOSBrick)
			flag = ((HOSBrick) i).update();
		else if (i instanceof MultipleLifeBrick)
			flag = ((MultipleLifeBrick) i).update();
		else if (i instanceof RefractiveBrick)
			flag = ((RefractiveBrick) i).update();
		else if (i instanceof SpinBrick)
			flag = ((SpinBrick) i).update();
		else
			flag = i.update();
		return flag;
	}

	public static void main(String args[]) {
		Point backSize = new Point(100, 100);
		Point buttonSize = new Point(1200, 100);
		frame.fill(backSize, 87);
		Button st = new Button("Start", buttonSize, new Point(900, 900));
		while (!st.button.getModel().isPressed())
			sleep(25);
		sleep(100);
		st.button.setVisible(false);
		int maxStage = 5;
		for (stage = 0; stage < maxStage; stage++) {
			// frame.fill(backSize, 171);
			Point2D initBallPos = new Point2D(1200, 800);
			Point2D initBallVel = new Point2D(10.0, -20.0);
			ball = new Ball(initBallPos, initBallVel);
			int score = 0;
			scoreLabel = new Label(new Point(100, 100), "Score :" + 0);
			thetaLabel = new Label(new Point(100, 200), "Theta :" + 0);
			bar = new Bar(new Point(1350, 1200));
			Button ps = new Button("Pause", new Point(300, 100), new Point(2600, 300));
			cont = true;
			init();
			for (cnt = 0; cont; cnt++) {
				if (ps.button.getModel().isPressed()) {
					Button rs = new Button("Resume", buttonSize, new Point(900, 900));
					while (!rs.button.getModel().isPressed())
						sleep(25);
					sleep(100);
					frame.remove(rs.button);
				}
				ball.update();
				for (Brick i : bricks)
					if (stage < 0)
						break;
					else if (i.alive && brickUpdate(i))
						scoreLabel.label.setText("Score: " + (score += (i instanceof HOSBrick ? 200 : 100)));
				if (stage < 0)
					break;
				bar.update(ball);
				thetaLabel.label.setText("Theta :" + Math.atan(ball.velocity.y / ball.velocity.x));
				sleep(20);
				if (score == max[stage])
					if (stage != maxStage - 1) {
						Label winLabel = new Label(new Point(950, 700), new Point(1100, 100),
								"You Won! Continue To Next Stage.");
						Button contButton = new Button("Continue", new Point(500, 100), new Point(1250, 1000));
						while (!contButton.button.getModel().isPressed())
							sleep(25);
						sleep(100);
						frame.remove(winLabel.label);
						frame.remove(contButton.button);
						frame.remove(bar.disBar);
						frame.remove(ball.disBall);
						frame.remove(scoreLabel.label);
						frame.remove(thetaLabel.label);
						for (Brick i : bricks)
							if (i instanceof HOSBrick)
								frame.remove(((HOSBrick) i).disBrick);
							else if (i instanceof MultipleLifeBrick)
								frame.remove(((MultipleLifeBrick) i).disBrick);
							else if (i instanceof RefractiveBrick)
								frame.remove(((RefractiveBrick) i).disBrick);
							else if (i instanceof SpinBrick)
								frame.remove(((SpinBrick) i).disBrick);
							else
								frame.remove(i.disBrick);
						frame.repaint();
						cont = false;
					} else {
						Label winLabel = new Label(new Point(850, 700), new Point(1300, 100),
								"Congratulations! You Cleared The Game! Restart?");
						Button restart = new Button("Restart", new Point(500, 100), new Point(1250, 1000));
						while (!restart.button.getModel().isPressed())
							sleep(25);
						stage = -1;
						frame.remove(winLabel.label);
						frame.remove(winLabel.label);
						frame.remove(restart.button);
						frame.remove(bar.disBar);
						frame.remove(ball.disBall);
						frame.remove(scoreLabel.label);
						frame.remove(thetaLabel.label);
						frame.repaint();
						cont = false;
					}
			}
		}
	}
}