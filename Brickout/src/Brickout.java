import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class Ball{
	public final double radius = 1.0;
	public Point position;
	public Point velocity;
	public Ball(Point position, Point velocity){
		this.position = position;
		this.velocity = velocity;
	}
	public Ball(){
		this(new Point(0,0), new Point(1,0));
	}
}

class Brick{
	public final Point size = new Point(20,10); // (width, height)
	public Point position;
	public Brick(Point position){
		this.position = position;
	}
	public void godown() {
		
	}
}

class MultipleLifeBrick extends Brick{
	public int life;
	public static int defaultLife = 3;
	public MultipleLifeBrick(int life, Point position){
		super(position);
		this.life = life;
	}
	public MultipleLifeBrick(Point position){
		this(defaultLife,position);
	}
}

class HOSBrick extends Brick{
	public HOSBrick pair;
	public HOSBrick(Point position){
		super(position);
		this.pair = null;
	}
	public static void entangle(HOSBrick B1, HOSBrick B2) {
		B1.pair = B2;
		B2.pair = B1;
	}
}

class RefractiveBrick extends Brick{
	public static double defaultRefractiveIndex = 1.5;
	public double RefractiveIndex;
	public RefractiveBrick(double RefractiveIndex, Point position){
		super(position);
		this.RefractiveIndex = RefractiveIndex;
	}
	public RefractiveBrick(Point position){
		this(defaultRefractiveIndex,position);
	}
}

class SpinBrick extends Brick{
	public static Random RandomAngleGenerator = new Random();
	public static double spin() {
		return 360.0 * RandomAngleGenerator.nextDouble();
	}
	public SpinBrick(Point position) {
		super(position);
	}
}

public class Brickout {
	public static void main(String[] arg) {
		System.out.println("Coymen and Slaves!");
	}
}
