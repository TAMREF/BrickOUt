import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.List;
import java.awt.Panel;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

class MyFrame implements KeyListener
{
    JFrame frm = new JFrame();
    JTextField tfield = new JTextField();
    public MyFrame()
    {
        frm.setBounds(300, 300, 300, 300);
        frm.setTitle("keyTest");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tfield.addKeyListener(this);
        frm.add(tfield);
        frm.setVisible(true);
    }
    public void keyPressed(java.awt.event.KeyEvent a) 
    {
        ShowInfo(a);
    }
    public void keyReleased(java.awt.event.KeyEvent a) 
    {
        ShowInfo(a);        
    }
    public void keyTyped(java.awt.event.KeyEvent a) 
    {
        ShowInfo(a);
    }
    
    protected void ShowInfo(java.awt.event.KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if(keyCode == 37) {
        	System.out.println("left");
        }
        else if(keyCode==39) {
        	System.out.println("right");
        }
    }
}
public class BrickOut {
	public static void main(String args[]) {
		MyFrame my = new MyFrame();
	}
}


