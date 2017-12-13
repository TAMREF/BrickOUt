import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Brick {
	public Point size = new Point(200, 100); // (width, height)
	public Point2D posit;
	JLabel disBrick = new JLabel(
			new ImageIcon(new ImageIcon(getClass().getResource("puzzlepack/element_grey_rectangle.png")).getImage()
					.getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH)));
	boolean alive = true;

	public void play() {
		InputStream in = getClass().getResourceAsStream("kenney_digitalaudio/Audio/twoTone2.wav");
		AudioStream audio = null;
		try {
			audio = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioPlayer.player.start(audio);
	}

	public void goDown() {
		posit.y += 20;
		disBrick.setLocation(posit.topoint());
	}

	public Brick() {
		this(new Point2D(0, 0));
	}

	public Brick(Point2D posit) {
		this(posit, false);
	}

	public Brick(Point2D posit, boolean self) {
		this.posit = posit;
		disBrick.setLocation(posit.topoint());
		disBrick.setSize(size.x, size.y);
		if (self) {
			BrickOut.frame.add(disBrick, 0);
			disBrick.repaint();
		}
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
			if ((b.velocity.x > 0) ^ (b.velocity.y > 0)) {
				double temp = b.velocity.x;
				b.velocity.x = -b.velocity.y;
				b.velocity.y = -temp;
			} else {
				double temp = b.velocity.x;
				b.velocity.x = b.velocity.y;
				b.velocity.y = temp;
			}
			this.alive = false;
			play();
			return true;
		} else if (checkXcol(b)) {
			b.velocity.x = -b.velocity.x;
			this.alive = false;
			play();
			return true;
		} else if (checkYcol(b)) {
			b.velocity.y = -b.velocity.y;
			this.alive = false;
			play();
			return true;
		}
		return false;
	}

	public boolean update() {
		if (BrickOut.cnt % 100 == 0)
			this.goDown();
		if (checkCol(BrickOut.ball)) {
			BrickOut.frame.remove(this.disBrick);
			BrickOut.frame.repaint();
		} else
			this.disBrick.repaint();
		return !this.alive;
	}
}