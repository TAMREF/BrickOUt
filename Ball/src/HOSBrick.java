import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HOSBrick extends Brick {
	public HOSBrick pair;
	public static int Pairnum = 0;
	public JLabel disBrick = null;
	int id;

	public HOSBrick(Point2D posit) {
		super(posit);
		this.pair = null;
	}

	public void entangle(HOSBrick B1, HOSBrick B2) {
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResource("puzzlepack/element_blue_rectangle.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage image1 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(size.x / (double) temp.getWidth(), size.y / (double) temp.getHeight());
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image1 = scaleOp.filter(temp, image1);
		BufferedImage image2 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		BufferedImage image3 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image3.createGraphics();
		Graphics2D g1 = image2.createGraphics();
		g1.setFont(new Font("Arial", Font.PLAIN, 72));
		g1.drawString(Integer.toString(Pairnum),
				(size.x - g1.getFontMetrics().stringWidth(Integer.toString(Pairnum))) / 2,
				g1.getFontMetrics().getAscent());
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, (image1.getWidth() - image2.getWidth()) / 2, 0, null);
		g1.dispose();
		g.dispose();
		B1.disBrick = new JLabel(new ImageIcon(image3));
		B1.disBrick.setLocation(B1.posit.topoint());
		B1.disBrick.setSize(B1.size.x, B1.size.y);
		BrickOut.frame.add(B1.disBrick, 0);
		B1.disBrick.repaint();
		B2.disBrick = new JLabel(new ImageIcon(image3));
		B2.disBrick.setLocation(B2.posit.topoint());
		B2.disBrick.setSize(B2.size.x, B2.size.y);
		BrickOut.frame.add(B2.disBrick, 0);
		B2.disBrick.repaint();
		B1.pair = B2;
		B2.pair = B1;
		++Pairnum;
	}

	@Override
	public void goDown() {
		this.posit.y += 20;
		this.disBrick.setLocation(this.posit.topoint());
	}

	public boolean checkCol(Ball b) {
		if (checkXYcol(b) || checkXcol(b) || checkYcol(b)) {
			this.alive = false;
			this.pair.alive = false;
			b.posit.x = this.pair.posit.x + 0.5 * this.size.x;
			b.posit.y = this.pair.posit.y + 0.5 * this.size.y;
			play();
			return true;
		}
		return false;
	}

	public boolean update() {
		if (BrickOut.cnt % 100 == 0)
			this.goDown();
		if (this.checkCol(BrickOut.ball)) {
			BrickOut.frame.remove(this.disBrick);
			BrickOut.frame.remove(this.pair.disBrick);
		} else
			this.disBrick.repaint();
		return !this.alive;
	}
}