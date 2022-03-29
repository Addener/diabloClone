import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CharacterCollection {

	private static ArrayList <Character> list = new ArrayList<Character>();
	
	private static double timeIntersection = 1;
	private static double elapsedTimeIntersection = 0;
	
	
	public static Hero getHero() {
		return (Hero)list.get(0);
	}
	
	private static void checkIntersection() {
			
		Hero player = getHero();
		
		Rectangle playerCollider =  player.getCollider();
		
		for (int i = 1; i < list.size(); i++) {
			Rectangle  enemyCollider =  list.get(i).getCollider();
			
			if ( playerCollider.intersects(enemyCollider) ) {
				player.changeHP( - list.get(i).damage  ); 
			}
		}
		
		
	}
	
	
	public static Character getByRowCol(int row, int col, Map m) {
		for ( Character obj : list  ) {
			Point p = obj.main.getCenterPoint();
			int r, c;
			r = m.getRowByY((int)p.getY());
			c = m.getColByX((int)p.getX());
			if (r == row && c == col) {
				return obj;
			}
		}
		return null;		
		
	}
	
	
	
	public static void initialize(Map m) {
		
		Hero h = new Hero(m);
		h.npc = false;
		
		h.setX(  h.getX() + 400  );
		
		
		add(h);
		
		for (int i = 0; i < 10; i++) {
			Boar b = new Boar(m);
			add(b);
		}
		
		for (int i = 0; i < 5; i++) {
			Bat b = new Bat(m);
			add(b);
		}
		
		for (int i = 0; i < 2; i++) {
			Ghost b = new Ghost(m);
			add(b);
		}
		
		
	}
	
	public static void add(Character c) {
		list.add(c);
	}
	
	public static void update (double s) {
		
		elapsedTimeIntersection += s;
		
		
		for (Character c : list ) {
			c.update(s);
		}
		
		if (elapsedTimeIntersection >= timeIntersection) {
			checkIntersection() ;
			elapsedTimeIntersection = 0;
		}
		
	}
	
	public static void paint (Graphics g) {
		for (Character c : list ) {
			c.paint(g);
		}
	}
		
		
	
	
}
