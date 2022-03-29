import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {

	boolean gameMode = false;
	
	Hero player;

	Color background = new Color(194, 205, 175);
	Map map;
	
	long t1, t2 = System.currentTimeMillis();
	
	int mouseX, mouseY, mouseButton = 0;
	double speedX=0, speedY=0;
	
	int currentBlock = Map.BRICK;
	
	private void update () {
		t1 = System.currentTimeMillis();
		
		int ms = (int)(t1 - t2);
		double t = ms / 1000.0;
		
		

		CharacterCollection.update(t);
		
		Map.showActiveCell = !gameMode;
		
		Camera.setHeight( getHeight()   );
		Camera.setWidth( getWidth()   );
		
		// В режиме игры
		if (gameMode) {
			Camera.setPosition( player.getX() - getWidth() / 2 + 16  , player.getY() - getHeight() / 2 + 16, map);
		}
		else {
			Camera.move(speedX, speedY, map);
		}
		
		t2 = t1;
		
	}
	public GamePanel() {
		map = new Map();
		map.generate(100, 100);
		
		CharacterCollection.initialize(map);
		player = CharacterCollection.getHero();
		
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());	
		map.paint(g);

		
		CharacterCollection.paint(g);
		
		//g.setColor(Color.BLACK);
		//g.drawString("Мышь: "+mouseX+" "+mouseY, 100, 100);
		
		if (!gameMode) {
			map.paintPalette(g, currentBlock, getHeight() - 40);
		}
		
		
		update();
		repaint();
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (gameMode) {
			return;
		}
		
		mouseMoved(e);
	
		if (mouseButton == 3) {
			map.setBlock(0);
		}
		else {
			map.setBlock(currentBlock);
		}
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
		if (gameMode) {
			return;
		}
		
		mouseX = e.getX();
		mouseY = e.getY();
		
		speedX = 0;
		speedY = 0;
		
		if (mouseX < 100) {
			speedX = -0.5;
		}
		
		if (mouseX > getWidth() - 100) {
			speedX = 0.5;
		}
		
		if (mouseY < 100) {
			speedY = -0.5;
		}
		if (mouseY > getHeight()-100) {
			speedY = 0.5;
		}
		
		
		
		
		int row = map.getRowByY( (int)Camera.getWorldY(mouseY)  );
		int col = map.getColByX( (int)Camera.getWorldX(mouseX)  );
		
		map.setActiveCell(row, col);
		
		
				
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (gameMode) {
			return;
		}
		
		
		if (e.getButton() == 3) {
			map.setBlock(0);
			mouseButton = 3;
		}
		else {
			mouseButton = 0;
			map.setBlock(currentBlock);
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		if (gameMode) {
			return;
		}
		
			mouseButton = 0;
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if (gameMode) {
			return;
		}
		
		
		if (e.getWheelRotation() > 0) {
			currentBlock++;
			if (currentBlock > 14) {
				currentBlock = 0;
			}
		}
		else {
			currentBlock--;
			if (currentBlock < 0) {
				currentBlock = 14;
			}
			
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.right();
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left();
		}
		else if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up();
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.interract();
		}
		// Сохранение
		else if(e.getKeyCode() == KeyEvent.VK_F5) {
			Point playerPos = new Point();
			playerPos.x = (int)player.getX();
			playerPos.y = (int)player.getY();
			map.save("map.txt", playerPos);
		}
		// Загрузка
		else if(e.getKeyCode() == KeyEvent.VK_F6) {
			Point playerPos = new Point();
			map.load("map.txt", playerPos);
			player.setX(  playerPos.x    );
			player.setY(  playerPos.y    );
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_F12) {
			gameMode = !gameMode;
		}
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	
}

































