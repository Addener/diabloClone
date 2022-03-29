
public class Camera {
	private static double x = 500, y = 500;
	private static int width, height;
	

	public static int getWidth() {
		return width;
	}
	public static void setWidth(int width) {
		Camera.width = width;
	}
	public static int getHeight() {
		return height;
	}
	public static void setHeight(int height) {
		Camera.height = height;
	}
	public static double getWorldX(int screenX) {
		return screenX + x;
	}
	public static double getWorldY(int screenY) {
		return screenY + y;
	}
	
	public static int getScreenX (double X) {
		return (int)(X - x);
	}
	public static int getScreenY (double Y) {
		return (int)(Y - y);
	}
	
	public static void move(double deltaX, double deltaY, Map map) {
		setPosition(x + deltaX, y + deltaY, map);
	}
	
	
	public static void setPosition(double x, double y, Map map) {
		
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		
		if (x > map.getWidth() - width) {
			x = map.getWidth() - width;
		}
		if (y > map.getHeight() - height) {
			y = map.getHeight() - height;
		}
		
		Camera.x = x;
		Camera.y = y;
		
		
	}
	
}
