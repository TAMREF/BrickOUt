import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Bar {
	Point size = new Point(300, 50);
	Point posit;
	Point move = new Point(25, 0);
	JLabel disBar = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/paddleBlu.png"))
			.getImage().getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));

	public static void play() {
		InputStream in = Bar.class.getResourceAsStream("kenney_digitalaudio/Audio/tone1.wav");
		AudioStream audio = null;
		try {
			audio = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioPlayer.player.start(audio);
	}

	Bar(Point b) {
		this.posit = b;
		disBar.setLocation(posit);
		disBar.setSize(size.x, size.y);
		BrickOut.frame.add(disBar, 0);
		disBar.repaint();
	}

	Bar() {
		this(new Point(0, 0));
	}

	public void update(Ball b) {
		if (MainFrame.move == 1)
			moveLeft();
		else if (MainFrame.move == 2)
			moveRight(MainFrame.size);
		disBar.setLocation(posit);
		disBar.repaint();
		checkCol(b);
	}

	public void moveLeft() {
		posit.x = posit.x > move.x ? posit.x - move.x : 0;
	}

	public void moveRight(Point s) {
		posit.x = posit.x + move.x + size.x > s.x ? s.x - size.x : posit.x + move.x;
	}

	protected boolean checkYcol(Ball b) { // checks whether velocity.y should be flipped
		return (b.posit.y + b.diameter >= this.posit.y && b.posit.y <= this.posit.y
				|| b.posit.y >= this.posit.y + this.size.y && b.posit.y <= this.posit.y + this.size.y + b.diameter)
				&& (b.posit.x >= this.posit.x && b.posit.x + b.diameter <= this.posit.x + this.size.x);
	}

	protected boolean checkXcol(Ball b) { // checks whether velocity.x should be flipped
		return (b.posit.x + b.diameter >= this.posit.x && b.posit.x <= this.posit.x
				|| b.posit.x >= this.posit.x + this.size.x && b.posit.x <= this.posit.x + this.size.x + b.diameter)
				&& (b.posit.y >= this.posit.y && b.posit.y + b.diameter <= this.posit.y + this.size.y);
	}

	protected boolean checkXYcol(Ball b) { // checks whether both vel.x and vel.y should be flipped
		Point2D tp;
		int cnt = 0;
		tp = new Point2D(b.posit.x, b.posit.y); // LU
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; // RU
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x -= b.diameter;
		tp.y += b.diameter; // LD
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;
		tp.x += b.diameter; // RD
		if (tp.x >= this.posit.x && tp.x <= this.posit.x + this.size.x && tp.y >= this.posit.y
				&& tp.y <= this.posit.y + this.size.y)
			++cnt;

		if (cnt == 1)
			return true;
		return false;
	}

	public boolean checkCol(Ball b) {

		if (checkXYcol(b)) {
			double temp = b.velocity.x;
			b.velocity.x = b.velocity.x / Math.abs(b.velocity.x) * b.velocity.y;
			b.velocity.y = -Math.abs(temp);
			play();
			return true;
		} else if (checkXcol(b)) {
			b.velocity.x = -b.velocity.x;
			play();
			return true;
		} else if (checkYcol(b)) {
			double temp = b.velocity.distance(), theta = 0, rand = new Random().nextDouble();
			if (MainFrame.move > 0) {
				if ((b.velocity.x > 0) != (MainFrame.move == 1))
					theta = Math.atan(b.velocity.y / b.velocity.x * (rand + 1));
				else
					theta = Math.atan(b.velocity.y / b.velocity.x / (rand + 1));
			} else
				theta = Math.atan(b.velocity.y / b.velocity.x);
			double a = Math.abs(theta);
			a = Math.min(a, 1.4);
			a = Math.max(a, 0.3);
			theta = theta / Math.abs(theta) * a;
			b.velocity.x = b.velocity.x / Math.abs(b.velocity.x) * Math.abs(Math.cos(theta) * temp);
			b.velocity.y = -Math.abs(Math.sin(theta) * temp);
			play();
			return true;
		}
		return false;
	}
}