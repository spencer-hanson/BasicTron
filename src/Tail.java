import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class Tail implements Paintable {
	private GeneralPath tailPolygon;
	private Color color;
	
	public Tail(int x, int y, Color color) {
		this.color = color;
		tailPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 2500);
		tailPolygon.moveTo(x+4, y+4);
	}
	
	public void addPoint(Point p) {
		tailPolygon.lineTo(p.getX()+4, p.getY()+4);
	}
	
	public void updatePoint(Point p) {
		tailPolygon.moveTo(p.getX()+4, p.getY()+4);
	}
	
	public Shape getPolygon() {
		return tailPolygon;
	}

	public boolean intersects(Rectangle rect) {
	
		return tailPolygon.intersects(rect);
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		//g2d.setColor(color);
		g2d.setStroke(new BasicStroke(8));
		g2d.draw(tailPolygon);
	}
}
