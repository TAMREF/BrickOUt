import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = 2801274722167755407L;
	JTextField tfield = new JTextField();
	static int move = 0;
	static Point size;

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

	public void fill(Point backSize, int a) {
		JLabel[][] background = new JLabel[size.x / backSize.x][size.y / backSize.y];
		for (int i = 0; i < size.x / backSize.x; i++)
			for (int j = 0; j < size.y / backSize.y; j++)
				background[i][j] = new Background(backSize, new Point(backSize.x * i, backSize.y * j), a).back;
		for (JLabel[] i : background)
			for (JLabel j : i)
				j.repaint();
	}

	public void play(int a) {
		if (BrickOut.seq.isRunning())
			BrickOut.seq.stop();
		try {
			BrickOut.seq.open();
		} catch (MidiUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStream is = null;
		String s1 = "src/midis th678/Imperishable Night/th08_0", s2 = ".mid";
		try {
			is = new BufferedInputStream(new FileInputStream(new File(s1 + a + s2)));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			BrickOut.seq.setSequence(is);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BrickOut.seq.setLoopCount(255);
		BrickOut.seq.start();
	}

	MainFrame() {
		this("Default Title", new Point(2000, 2000));
	}
}