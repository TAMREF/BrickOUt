import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HOSBrick extends Brick {
	public HOSBrick pair;
	public static int Pairnum = 0;
	int id;

	public HOSBrick(Point2D posit) {
		super(posit);
		this.pair = null;
	}

	public void entangle(HOSBrick B1, HOSBrick B2) throws Exception {
		BufferedImage temp = ImageIO.read(getClass().getResource("puzzlepack/element_blue_rectangle.png"));
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
		B2.disBrick = new JLabel(new ImageIcon(image3));
		B1.pair = B2;
		B2.pair = B1;
		++Pairnum;
	}

	@Override
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

	public boolean update(Ball b, int cnt) {
		boolean flag = checkCol(b);
		if (cnt % 100 == 0)
			this.goDown();
		if (flag) {
			this.alive = false;
			this.disBrick.setVisible(false);
		}
		return flag;
	}
}