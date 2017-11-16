import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class TronBike implements Paintable, Moveable {
	
	private int x;
	private int y;
	
	private int velx;
	private int vely;
	
	private int currentDirection;
	private int speed;
	
	private Tail tail;
	private KeySet ks;
	private String name;
	
	private Rectangle current;
	private Color color;
	private Color color_powered;
	
	private boolean death;
	private boolean cooldown;
	
	public TronBike(String name, int x, int y, Color color, Color color_powered, KeySet ks) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.color = color;
		this.color_powered = color_powered;
		this.ks = ks;
		this.death = false;
		this.cooldown = false;
		this.tail = new Tail(x, y, color);
		this.speed = 3;
		this.currentDirection = -1;
		this.current = new Rectangle(x, y, 8, 8);
	}
	
	public void checkCollisions() {
		if(currentDirection == ks.getLeft()) { 
			current = new Rectangle(x-4, y, 8, 8);
		} else if(currentDirection == ks.getRight()) { 
			current = new Rectangle(x+4, y, 8, 8);
		} else if(currentDirection == ks.getUp()) {
			current = new Rectangle(x, y-4, 8, 8);
		} else if(currentDirection == ks.getDown()) {
			current = new Rectangle(x, y+4, 8, 8);
		}
		
		if(tail.intersects(current) && currentDirection != -1) {
			die();
		}
	}
	
	public Rectangle getCurrentBounds() {
		return current;
	}
	
	public boolean hitOther(Rectangle rect) {
		if(tail.intersects(rect)) {
			return true;
		}
		return false;
	}
	
	public void die() {
		death = true;
		velx = 0;
		vely = 0;
	}
	
	public boolean isDead() {
		return death;
	}
	
	public void update() {
		x += velx;
		y += vely;
		checkCollisions();
		tail.addPoint(new Point(x, y));
	}
	
	public void paint(Graphics2D g) {
		if(!cooldown) {
			g.setColor(color);
		} else {
			g.setColor(color_powered);
		}
		tail.paint(g);
		g.fillRect(x, y, 8, 8);
	}
	
	public void turn(int direction) {
		if(currentDirection != direction && currentDirection != -1) {
		  tail.updatePoint(new Point(x, y));
		}
	}
	
	public void sendKey(KeyEvent e) {
		if(!death) {
			if(e.getKeyCode() == ks.getLeft() && currentDirection != ks.getRight()) {
				turn(ks.getLeft());
				currentDirection = ks.getLeft();
				setVelx(-speed);
				setVely(0);
			} else if(e.getKeyCode() == ks.getRight() && currentDirection != ks.getLeft()) {
				turn(ks.getRight());
				currentDirection = ks.getRight();
				setVelx(speed);
				setVely(0);
			} else if(e.getKeyCode() == ks.getUp() && currentDirection != ks.getDown()) {
				turn(ks.getUp());
				currentDirection = ks.getUp();
				setVelx(0);
				setVely(-speed);
			} else if(e.getKeyCode() == ks.getDown() && currentDirection != ks.getUp()) {
				turn(ks.getDown());
				currentDirection = ks.getDown();
				setVelx(0);
				setVely(speed);
				turn(currentDirection);
			} else if(e.getKeyCode() == ks.getSpeed()) {
				if(!cooldown) {
					new Timer(3, true).start();
					cooldown = true;
					new Timer(5, false).start();
				}
			}
		} else {
			setVelx(0);
			setVely(0);
		}
	}
	
	public void sendKey(int e) {
		if(!death) {
			if(e == ks.getLeft() && currentDirection != ks.getRight()) {
				turn(ks.getLeft());
				currentDirection = ks.getLeft();
				setVelx(-speed);
				setVely(0);
			} else if(e == ks.getRight() && currentDirection != ks.getLeft()) {
				turn(ks.getRight());
				currentDirection = ks.getRight();
				setVelx(speed);
				setVely(0);
			} else if(e == ks.getUp() && currentDirection != ks.getDown()) {
				turn(ks.getUp());
				currentDirection = ks.getUp();
				setVelx(0);
				setVely(-speed);
			} else if(e == ks.getDown() && currentDirection != ks.getUp()) {
				turn(ks.getDown());
				currentDirection = ks.getDown();
				setVelx(0);
				setVely(speed);
				turn(currentDirection);
			} else if(e == ks.getSpeed()) {
				if(!cooldown) {
					new Timer(3, true).start();
					cooldown = true;
					new Timer(5, false).start();
				}
			}
		}
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}
	
	public String getName() {
		return name;
	}
	
	class Timer extends Thread {
		int time;
		boolean s;
		public Timer(int time, boolean speed) {
			this.time = time;
			this.s = speed;
		}
		
		@Override 
		public void run() {
			if(s) { speed = 5; sendKey(currentDirection); }
			try {
				Thread.sleep(time * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(s) { speed = 3; sendKey(currentDirection); cooldown = false;}
			if(!s) { cooldown = false; }
		}
	}
}