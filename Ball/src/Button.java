import java.awt.Color;
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
	Point position;
	JButton button = new JButton();
	boolean pressed = false;
	String text = new String();

	Button(String s, Point p, Point q) {
		text = s;
		size = p;
		position = q;
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResource("puzzlepack/buttonDefault.png"));
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
		g1.setFont(new Font("Arial", Font.BOLD, 72));
		g1.setColor(Color.BLACK);
		g1.drawString(text, (size.x - g1.getFontMetrics().stringWidth(text)) / 2, g1.getFontMetrics().getAscent());
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, (image1.getWidth() - image2.getWidth()) / 2, 0, null);
		g1.dispose();
		g.dispose();
		button = new JButton(new ImageIcon(image3));
		try {
			temp = ImageIO.read(getClass().getResource("puzzlepack/buttonSelected.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image1 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		at = new AffineTransform();
		at.scale(size.x / (double) temp.getWidth(), size.y / (double) temp.getHeight());
		scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image1 = scaleOp.filter(temp, image1);
		image2 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		image3 = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
		g = image3.createGraphics();
		g1 = image2.createGraphics();
		g1.setFont(new Font("Arial", Font.BOLD, 72));
		g1.setColor(Color.ORANGE);
		g1.drawString(text, (size.x - g1.getFontMetrics().stringWidth(text)) / 2, g1.getFontMetrics().getAscent());
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, (image1.getWidth() - image2.getWidth()) / 2, 0, null);
		g1.dispose();
		g.dispose();
		button.setPressedIcon(new ImageIcon(image3));
		button.setLocation(position);
		button.setSize(size.x, size.y);
		BrickOut.frame.add(button, 0);
		button.repaint();
	}

}
