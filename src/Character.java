import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Character {
	
	double hp = 100;
	
	double damage;
	
	boolean active = true;
	
	boolean heroSearch = false;
	
	Sprite main;
	Sprite second;
	Map map;
	
	double timeToChangeOrientation = Math.random() * 4;
	double elapsedTime = 0;
	boolean npc = true;	
	private ArrayList <Integer> inventory = new ArrayList<Integer>();

	double heroSearchTime = 2.5; 
	
	
	public Rectangle getCollider() {
		
		Rectangle r = new Rectangle( (int)main.getX(),
									 (int)main.getY(),
									  32,
									  32);
		return r;		
	}
	
	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private void randomOrientation() {
		Random r = new Random();
		int rand = r.nextInt(4);
		double alpha = rand * 90;
		main.setAlpha(alpha);
	}
	
	public void AI() {
		if (main.isCollision()) {
			randomOrientation();
		}
		if (elapsedTime >= timeToChangeOrientation) {
			
			if (heroSearch && Math.random() > 0.2 ) {
				// Поиск героя
				heroOrientation();
				elapsedTime = 0;
				timeToChangeOrientation = 0.5+Math.random() * heroSearchTime ;
				
				
			}
			else {
				randomOrientation();
				elapsedTime = 0;
				timeToChangeOrientation = Math.random() * 2;
			}
		}
		
	}
	
	private void heroOrientation() {
		// Выбираем Направление движения по горизонтали или вертикали
		
		boolean horizontal = Math.random() < 0.5;
		
		if (horizontal) {
			// Сближаемся по горизонтали
			double xHero = CharacterCollection.getHero().getX();
			double xChar = getX();
			if (xHero > xChar) {
				right();
			}
			else {
				left();
			}
			
		}
		else {
			// Сближаемся по вертикали
			double yHero = CharacterCollection.getHero().getY();
			double yChar = getY();
			if (yHero > yChar) {
				down();
			}
			else {
				up();
			}
		}
	}

	public boolean inventoryContains (int object) {
		return inventory.contains(object);
	}
	
	
	public void addToInventory(int object) {
		if (! inventory.contains(object)) {
			inventory.add(object);
		}
	}
	public void removeFromInventory(int object) {
		
		
		
		inventory.remove(object);
	}

	
	public double getX() {
		return main.getX();
	}

	public void setX(double x) {
		main.setX(x);
	}

	public double getY() {
		return main.getY();
	}

	public void setY(double y) {
		main.setY(y);
	}

	public void left() {
		main.left();
	}

	public void right() {
		main.right();
	}

	public void up() {
		main.up();
	}

	public void down() {
		main.down();
	}

	public Character(Map map) {
		this.map = map;
		main = new Sprite(map);
		second = new Sprite(map);
		
		main.addToWhiteList(Map.GROUND);
	}
	
	public void interract() {
		int row, col;
		Point center = main.getCenterPoint();
		
		row = map.getRowByY( center.y );
		col = map.getColByX( center.x );
		
		// Координаты объекта для взаимодействия
		int row2 = row, col2 = col+1;
		
		double alpha = main.getAlpha();
		if (alpha == 0) {
			
		} else if (alpha == 90) {
			row2 = row+1; col2 = col;
		} else if (alpha == 180) {
			row2 = row; col2 = col - 1;
		} else {
			row2 = row-1; col2 = col;
		}

		int object = map.getBlockByRowCol(row2, col2);
		// Есть ли персонаж в этих координатах row2 col2
		Character c = CharacterCollection.getByRowCol(row2, col2, map);
		
		if (c != null) {
			if (inventoryContains(Map.SWORD)) {
				//c.setActive(false);
				c.changeHP(-50);
			}
			else {
				//setActive(false);
				c.changeHP(-30);
			}
		}
		
		interractProcessing(object, row2, col2);
		
	}
	
	public void interractProcessing(int object, int row, int col) {
		if (object == Map.KEY) {
			addToInventory(Map.KEY);
			main.addToWhiteList(Map.KEY_CLOSED_DOOR);
			map.setBlock(row, col, Map.GROUND);
			second.setSkin(Map.KEY);
		}
		else if (object == Map.SWORD) {
			addToInventory(Map.SWORD);
			map.setBlock(row, col, Map.GROUND);
			second.setSkin(Map.SWORD);
		}
		else if (object == Map.ARMOR && !inventoryContains(Map.ARMOR)) {
			addToInventory(Map.ARMOR);
			map.setBlock(row, col, Map.GROUND);
			second.setSkin(Map.ARMOR);
		}
		//map.setBlock(row2, col2, Map.GROUND);
	}
	
	public void update (double t) {
		if (!active) {
			return;
		}
		
		elapsedTime += t;
		main.update(t);
		second.setX( main.getX() );
		second.setY( main.getY()  );
		
		if (npc) {
			AI();
		}
		
		//second.update(t);
	}
	
	public void changeHP(double delta) {
		
		if ( inventoryContains( map.ARMOR  ) ) {
			delta = delta / 2;
		}
		
		
		
		hp += delta;
		if (hp > 100) { hp = 100;}
		if (hp < 0) {active = false; }
	}
	
	public void paint(Graphics g) {
		if (!active) {
			return;
		}
		
		main.paint(g);
		second.paint(g);
		
		g.setColor(Color.GREEN);
		
		int width = (int)(hp / 100.0 * 32);
		
		g.fillRect(
				Camera.getScreenX(main.getX()),
				Camera.getScreenY(main.getY() + Map.BLOCK_SIZE + 1),
				width,
				2);
	}
}
