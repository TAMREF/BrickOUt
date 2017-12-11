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

	static Brick[] bricks = null;
	static int[] max = new int[5];
	static int cnt = 0;
	static MainFrame frame = new MainFrame("BrickOut", new Point(3000, 2000));
	static Ball ball = null;
	static Bar bar = null;
	static int stage = 0;
	static boolean cont = true;

	public static void init() {
		if (stage == 0) {
			bricks = new Brick[10];
			max[0] = 1000;
			for (int i = 0; i < 10; i++)
				bricks[i] = new Brick(new Point2D(400 + i * 220, 200),true);
		} else if (stage == 1) {
			bricks = new SpinBrick[10];
			max[1] = 1000;
			for (int i = 0; i < 10; i++)
				bricks[i] = new SpinBrick(new Point2D(400 + i * 220, 200));
		} else if (stage == 2) {
			bricks = new MultipleLifeBrick[10];
			max[2] = 1000;
			for (int i = 0; i < 10; i++)
				bricks[i] = new MultipleLifeBrick(new Point2D(400 + i * 220, 200));
		} else if (stage == 3) {
			bricks = new RefractiveBrick[10];
			max[3] = 1000;
			for (int i = 0; i < 10; i++)
				bricks[i] = new RefractiveBrick(new Point2D(400 + i * 220, 200));
		} else if (stage == 4) {
			bricks = new HOSBrick[10];
			max[4] = 1000;
			for (int i = 0; i < 10; i++)
				bricks[i] = new HOSBrick(new Point2D(400 + i * 220, 200));
			for (int i = 0; i < 5; i++)
				((HOSBrick) bricks[i]).entangle((HOSBrick) bricks[i], (HOSBrick) bricks[9 - i]);
		}
	}

	public static void die() {
		new Label(new Point(1075, 700), new Point(950, 100), "Oh No! You Died! Please Quit!");
		Button quit = new Button("Quit", new Point(500, 100), new Point(1250, 1200));
		while (!quit.button.getModel().isPressed())
			sleep(25);
		sleep(100);
		System.exit(0);
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
			Point2D initBallVel = new Point2D(6.0, -12.0);
			ball = new Ball(initBallPos, initBallVel);
			int score = 0;
			Label scoreLabel = new Label(new Point(100, 100), "Score :" + 0);
			Label thetaLabel = new Label(new Point(100, 200), "Theta :" + 0);
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
				if (stage < 0) {
					stage = 0;
					break;
				}
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
<<<<<<< HEAD
						for(Brick i:bricks)
=======
						for (Brick i : bricks)
>>>>>>> c05bf9b1c09a4caffdda7552396c28b51a1131cb
							frame.remove(i.disBrick);
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
						cont = false;
					}
			}
		}
	}
}