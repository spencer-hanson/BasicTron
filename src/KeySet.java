import java.awt.*;
import java.awt.event.KeyEvent;
public class KeySet {
	int up;
	int down;
	int left;
	int right;
	int speed;
	
	public KeySet() {
		this.up = KeyEvent.VK_UP;
		this.down = KeyEvent.VK_DOWN;
		this.left = KeyEvent.VK_LEFT;
		this.right = KeyEvent.VK_RIGHT;
		this.speed = KeyEvent.VK_SPACE;
	}
	
	public KeySet(int up, int down, int left, int right, int speed) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.speed = speed;
	}
	
	public void setUp(int up) {
		this.up = up;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getUp() {
		return up;
	}
	public int getDown() {
		return down;
	}
	public int getLeft() {
		return left;
	}
	public int getRight() {
		return right;
	}
	public int getSpeed() {
		return speed;
	}
	
}
