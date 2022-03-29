import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Sprite {

	private double x=600, y = 600;
	private int    padding = 3;
	private double speed=64, alpha = 90;
	private Map map;
	private int skin = 10;
	
	private ArrayList <Integer> whiteList = new ArrayList<Integer>();
	private boolean collision; 
	
	
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public boolean isCollision() {
		return collision;
	}



	public int getSkin() {
		return skin;
	}
	
	
	
	public void setSkin(int skin) {
		this.skin = skin;
	}
	public void addToWhiteList(int object) {
		if (! whiteList.contains(object)) {
			whiteList.add(object);
		}
	}
	public void removeFromWhiteList(int object) {
		whiteList.remove(object);
	}

	public double getX() {
		return x;
	}
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public void left() {
		alpha = 180;
	}
	public void right() {
		alpha = 0;
	}
	public void up() {
		alpha = 270;
	}
	public void down() {
		alpha = 90;
	}
	
	public Sprite(Map m) {
		map = m;
	}
	
	public Point getCenterPoint() {
		Point p = new Point(
				(int)x + Map.BLOCK_SIZE/2,
				(int)y + Map.BLOCK_SIZE/2
				);
		return p;		
	}
	
	
	public Point[] getCornerPoints() {
		Point array[] = new Point[4];

		Point p = new Point((int)x+padding, (int)y+padding);
		array[0] = p;
		
		Point p2 = new Point((int)x+Map.BLOCK_SIZE-padding, (int)y+padding);
		array[1] = p2;

		
		Point p3 = new Point((int)x+Map.BLOCK_SIZE-padding, (int)y+ Map.BLOCK_SIZE-padding);
		array[2] = p3;
		
		Point p4 = new Point((int)x+padding, (int)y+ Map.BLOCK_SIZE-padding);
		array[3] = p4;
		
		return array;
	}
	
	public void paint (Graphics g) {
		TilePainter.paint(
				g,
				skin,
				Camera.getScreenX(x), 
				Camera.getScreenY(y));
	}
	
	public void update (double t) {
		double oldX = x, oldY = y;
		collision = false;
		
		double sx = speed * Math.cos(  Math.toRadians(alpha) )  * t;
		double sy = speed * Math.sin(  Math.toRadians(alpha) )  * t;
		x = x + sx;
		y = y + sy;
		
		// Проверка столкновений
		Point [] points = getCornerPoints();
		for (Point p : points) {
			int block = map.getBlockByXY(p.x, p.y);
			if (!whiteList.contains(block)) {
				x = oldX;
				y = oldY;
				
				collision = true;
				
				break;
			}
		}
		
		
	}
	
	
}














