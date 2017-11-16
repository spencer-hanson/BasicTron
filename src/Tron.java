import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Tron extends JFrame implements KeyListener, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6243224720364537267L;

	private final int WIDTH = 500;
	private final int HEIGHT = 500;

	private BufferedImage backbuffer;
	private Graphics2D g2d;

	private boolean done = false;
	private boolean win = false;
	private String winner;
	private ArrayList<TronBike> bikes = new ArrayList<TronBike>(5);

	public void init() {
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		setBackground(Color.white);
		setVisible(true);

	}

	public Tron() {
		super("Tron!");
		backbuffer = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		g2d = backbuffer.createGraphics();
		winner = "";
		init();

		// Player 1
		KeySet ks = new KeySet();
		bikes.add(new TronBike("Player 1", WIDTH / 2 + WIDTH / 4, HEIGHT / 2,
				Color.blue, Color.cyan, ks));

		// Player 2
		KeySet ks2 = new KeySet(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A,
				KeyEvent.VK_D, KeyEvent.VK_E);
		bikes.add(new TronBike("Player 2", WIDTH / 2 - WIDTH / 4, HEIGHT / 2,
				Color.red, Color.magenta, ks2));

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < bikes.size(); i++) {
			bikes.get(i).sendKey(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public void updateBikes() {
		for (int i = 0; i < bikes.size(); i++) {
			bikes.get(i).paint(g2d);
			bikes.get(i).update();
			for (int x = 0; x < bikes.size(); x++) {
				if (x == i) {
					continue;
				} else {
					if (bikes.get(i).getCurrentBounds()
							.intersects(bikes.get(x).getCurrentBounds())) {
						// winner = "Tie!";
						// win = true;
						kill(x);
						kill(i);
					} else if (bikes.get(i).hitOther(
							bikes.get(x).getCurrentBounds())) {
						if (bikes.size() - 1 == 1) {
							// winner = bikes.get(i).getName() + " Wins!";
							// win = true;
						}
						kill(x);

					}
				}
			}
		}
	}

	public void kill(int index) {
		if(bikes.size() < 2) {
			bikes.get(index).die();
		} else {
			bikes.get(index).die();
			bikes.remove(index);
		}
	}

	public void checkForDeadPeople() {
		for (int i = 0; i < bikes.size(); i++) {
			if (bikes.size() == 1) {
				// win = true;
			}
			if (bikes.get(i).isDead()) {
				if (bikes.size() - 1 == 1) {
					// winner = bikes.get(i).getName() + " Wins!";
					// win = true;
				}
				kill(i);

			} else if (bikes.get(i).getX() >= WIDTH - 8
					|| bikes.get(i).getX() <= 8
					|| bikes.get(i).getY() >= HEIGHT - 8
					|| bikes.get(i).getY() <= 8) {
				if (bikes.size() - 1 == 1) {
					// winner = bikes.get(i).getName() + " Wins!";
					// win = true;
				}
				kill(i);
			}
		}
	}

	private Font font = new Font("Cooper", Font.PLAIN, 24);

	@Override
	public void run() {
		while (!done) {
			try {
				Thread.sleep(17);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, WIDTH, HEIGHT);

				updateBikes();
				if (!win) {
					checkForDeadPeople();
				} else {
					g2d.setColor(Color.black);
					g2d.setFont(font);
					g2d.drawString(winner, WIDTH / 2 - 50, HEIGHT / 2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}
