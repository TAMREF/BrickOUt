import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = 2801274722167755407L;
	JTextField tfield = new JTextField();
	static int move = 0;
	Point size;

	MainFrame(String Title, Point p) {
		size = p;
		setTitle(Title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tfield.addKeyListener(this);
		tfield.setLocation(0, 0);
		tfield.setSize(1, 1);
		add(tfield);
		setLayout(null);
		setSize(size.x, size.y);
		setVisible(true);
	}

	public void keyPressed(KeyEvent a) {
		ShowInfo(a);
	}

	public void keyReleased(KeyEvent a) {
		ShowInfo(a);
	}

	public void keyTyped(KeyEvent a) {
		ShowInfo(a);
	}

	protected void ShowInfo(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 37)
			move = 1;
		else if (keyCode == 39)
			move = 2;
		else
			move = 0;
	}

	public void fill(Background b) {
		for (int i = 0; i < size.x / b.size.x; i++)
			for (int j = 0; j < size.y / b.size.y; j++) {
				JLabel label = b.add();
				label.setLocation(b.size.x * i, b.size.y * j);
				label.setSize(b.size.x, b.size.y);
				add(label);
			}
	}

	MainFrame() {
		this("Default Title", new Point(2000, 2000));
	}
}