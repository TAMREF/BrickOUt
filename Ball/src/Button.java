import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button {
	Point size;
	JButton buttonLabel = new JButton();
	boolean pressed = false;
	String text = new String();

	Button(String s, Point p) throws IOException {
		text = s;
		size = p;
	}

	public JButton add() throws Exception {
		BufferedImage temp = ImageIO.read(getClass().getResource("puzzlepack/buttonDefault.png"));
		BufferedImage image1 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(size.x / temp.getWidth(), size.y / temp.getHeight());
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image1 = scaleOp.filter(temp, image1);
		BufferedImage image2 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		BufferedImage image3 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image3.createGraphics();
		Graphics2D g1 = image2.createGraphics();
		g1.setFont(new Font("Arial", Font.PLAIN, 24));
		g1.drawString(text, (size.x - g1.getFontMetrics().stringWidth(text)) / 2, g1.getFontMetrics().getAscent());
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, (image1.getWidth() - image2.getWidth()) / 2, 0, null);
		g1.dispose();
		g.dispose();
		buttonLabel = new JButton(new ImageIcon(image3));
		temp = ImageIO.read(getClass().getResource("puzzlepack/buttonSelected.png"));
		image1 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		at = new AffineTransform();
		at.scale(size.x / temp.getWidth(), size.y / temp.getHeight());
		scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image1 = scaleOp.filter(temp, image1);
		image2 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		image3 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		g = image3.createGraphics();
		g1 = image2.createGraphics();
		g1.setFont(new Font("Arial", Font.PLAIN, 24));
		g1.drawString(text, (size.x - g1.getFontMetrics().stringWidth(text)) / 2, g1.getFontMetrics().getAscent());
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, (image1.getWidth() - image2.getWidth()) / 2, 0, null);
		g1.dispose();
		g.dispose();
		buttonLabel.setPressedIcon(new ImageIcon(image3));
		return buttonLabel;
	}

}
