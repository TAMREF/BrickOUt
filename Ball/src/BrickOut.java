import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;

public class BrickOut {
	public static void main(String args[]) {
		Point frameSize = new Point(2400, 1600);
		MainFrame Frame = new MainFrame("BrickOut", frameSize);
		Point2D initBallPos = new Point2D(1200,800);
		Point2D initBallVel = new Point2D(3.0,-18.0);
		Ball B = new Ball(initBallPos, initBallVel);
		JLabel icon = B.add();
		icon.setLocation(B.posit.topoint());
		icon.setSize(B.diameter, B.diameter);
		Frame.add(icon);

		Brick[] R = new Brick[10];
		JLabel[] temp = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			R[i] = new HOSBrick(new Point2D(200 + i * 220, 0));
			temp[i] = new JLabel();
			temp[i] = R[i].add();
			temp[i].setLocation(R[i].posit.topoint());
			temp[i].setSize(R[i].size.x, R[i].size.y);
			Frame.add(temp[i]);
		}
		for(int i=0;i<5;i++) {
			HOSBrick.entangle((HOSBrick)R[i],(HOSBrick)R[9-i]);
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
		DisBar.setLocation(A.posit);
		DisBar.setSize(A.size.x, A.size.y);
		Frame.add(DisBar);
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
					if(R[i] instanceof MultipleLifeBrick) {
						flag = ((MultipleLifeBrick)R[i]).checkCol(B);
					}
					else if(R[i] instanceof HOSBrick) {
						flag = ((HOSBrick)R[i]).checkCol(B);
					}
					else if(R[i] instanceof RefractiveBrick) {
						flag = ((RefractiveBrick)R[i]).checkCol(B);
					}
					else if(R[i] instanceof SpinBrick) {
						flag = ((SpinBrick)R[i]).checkCol(B);
					}
					else {
						flag = R[i].checkCol(B);
					}
					if(flag) {
						System.out.println("Collision with #"+(i+1)+", Ball = "+B.posit+", Brick = "+R[i].posit);
						//R[i].posit.x = frameSize.x;
						//R[i].alive = false;
						if(R[i] instanceof HOSBrick) {
							Score.setText("Score: " + (score += 200));
						}else {						
							Score.setText("Score: " + (score += 100));
						}
					}else {
						//System.out.println("Ball = "+B.posit);
					}
				}
				if(!R[i].alive) {
					R[i].posit.x = 3 * frameSize.x;
				}
				temp[i].setLocation(R[i].posit.topoint());
			}
			for(int i=0;i<10;i++) {
				if(!R[i].alive) {
					R[i].posit.x = 3 * frameSize.x;
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			if (MainFrame.move == 1)
				A.moveLeft();
			else if (MainFrame.move == 2)
				A.moveRight(frameSize);
			A.posit.x += cnt % 2 == 0 ? 1 : -1;
			DisBar.setLocation(A.posit);
			A.checkCol(B);
			// System.out.println(B.posit);
			if (cnt % 100 == 0) {
				for (int i = 0; i < 10; i++) {					
					if (R[i].alive) {
						R[i].godown();
						temp[i].setLocation(R[i].posit.topoint());
					}
				}
			}
			//System.out.println(B.posit);
			//System.out.println(B.velocity);
			//System.out.println(cnt);
			cnt++;
		}
	}
}