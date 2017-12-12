import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
		InputStream in = null;
		try {
			in = new FileInputStream("src/kenney_digitalaudio/Audio/Trick.wav");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		AudioStream audio = null;
		try {
			audio = new AudioStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ContinuousAudioDataStream loop = null;
		try {
			loop = new ContinuousAudioDataStream(audio.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioPlayer.player.start(loop);
	}

	MainFrame() {
		this("Default Title", new Point(2000, 2000));
	}
}