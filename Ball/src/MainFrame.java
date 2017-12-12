import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class MainFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = 2801274722167755407L;
	JTextField tfield = new JTextField();
	static int move = 0;
	static Point size;
	static Clip clip = null;
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

	public void play() {
		AudioInputStream in = null;
		try {
			in = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(
					"C:\\Users\\slugg\\Desktop\\2017\\2017 노예 프로젝트\\휴텍\\BrickOUt\\Ball\\src\\kenney_digitalaudio\\Audio\\Trick.wav")));
			clip = AudioSystem.getClip();
			clip.open(in);
			clip.loop(300000);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	MainFrame() {
		this("Default Title", new Point(2000, 2000));
	}
}