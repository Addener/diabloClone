import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Map {

	int activeRow = 10, activeCol = 5;
	Color activeColor = Color.RED; 
	
	int [][] matrix;
	static boolean showActiveCell = false;
	
	public static final int GROUND 			= 0;
	public static final int WALL 				= 1; 
	public static final int OPENED_DOOR 		= 2;  // открытая дверь (ключ не нужен)
	public static final int CLOSED_DOOR   	    = 3;  // закрытая дверь (ключ не нужен) 
	public static final int KEY_OPENED_DOOR 	= 4;  // открытая дверь (ключ нужен)
	public static final int KEY_CLOSED_DOOR    = 5;  // закрытая дверь (ключ нужен) 
	public static final int BRICK  			= 6;  // кирпичная стена
	public static final int KEY  			= 7;  // ключ
	public static final int SWORD  			= 8;  // меч
	public static final int BOAR  			= 9;  // кабан
	public static final int BAT  			= 12;  // летучая мышь
	public static final int GHOST           = 13;  // призрак
	public static final int ARMOR           = 14;  // броня
	
	public static final int TRANSPARENT		= 11;  // Прозрачное изображение
	
	public static final int BLOCK_SIZE        = 32;
	
	public int getBlockByRowCol (int row, int col) {
		return matrix[row][col];
	}
	
	public int getBlockByXY (int x, int y) {
		int row = getRowByY(y);
		int col = getColByX(x);
		return matrix[row][col];
	}
	
	public int getCols() {
		return matrix[0].length;
	}
	public int getRows() {
		return matrix.length;
	}
	public int getWidth() {
		return getCols() * Map.BLOCK_SIZE;
	}
	public int getHeight() {
		return getRows() * Map.BLOCK_SIZE;
	}
	
	public boolean load (String fileName, Point playerPosition) {
		
		try {
			File f = new File(fileName);
			Scanner s = new Scanner(f);
			int version = s.nextInt();
			int rows = s.nextInt();
			int cols = s.nextInt();
			
			matrix = new int[rows][cols];
			
			for (int i = 0; i < getRows(); i++) {
				for (int j = 0; j < getCols(); j++) {
					matrix[i][j] = s.nextInt();
				}
			}
			
			playerPosition.x = s.nextInt();
			playerPosition.y = s.nextInt();
			
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
		
		
	}
	
	
	
	public boolean save(String fileName, Point playerPosition) {
		try {
			File f = new File(fileName);
			PrintWriter writer = new PrintWriter( f );
			// Версия файла уровней
			writer.println(1); // версия карты уровней №1
			writer.println(getRows());
			writer.println(getCols());
			
			for (int i = 0; i < getRows(); i++) {
				for (int j = 0; j < getCols(); j++) {
					writer.println(matrix[i][j]);
				}
			}
			
			writer.println( playerPosition.x );
			writer.println( playerPosition.y );
			
			writer.close();			
			
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
	}
	
	public int getRowByY (int y) {
		return y / BLOCK_SIZE;
	}
	public int getColByX (int x) {
		return x / BLOCK_SIZE;
	}
	
	public void setActiveCell (int row, int col) {
		activeCol = col;
		activeRow = row;
	}
	
	public void paintPalette (Graphics g, int currentBlock, int Y) {
		int current = currentBlock;
		int y = Y;
		for (int i = 0; i<= 14; i++) {
			TilePainter.paint(g, i, i*BLOCK_SIZE, y);
		}
		g.setColor(Color.RED);
		g.drawRect(current * Map.BLOCK_SIZE, y, Map.BLOCK_SIZE, Map.BLOCK_SIZE);
		
	}
	
	public void setBlock (int block) {
		matrix[activeRow][activeCol] = block;
	}
	
	public void setBlock (int row, int col, int block) {
		matrix[row][col] = block;
	}
	

	public void generate(int rows, int cols) {
		
		
		//painter.crop(  new Rectangle(BLOCK_SIZE * 4, BLOCK_SIZE * 3, BLOCK_SIZE, BLOCK_SIZE)   , GROUND);	
		//painter.crop(  new Rectangle(BLOCK_SIZE * 0, BLOCK_SIZE * 9, BLOCK_SIZE, BLOCK_SIZE)   , GROUND);	
		
		
		TilePainter.crop(new Rectangle(BLOCK_SIZE * 3, BLOCK_SIZE * 11, BLOCK_SIZE, BLOCK_SIZE), ARMOR);
		
		
		TilePainter.crop(new Rectangle(BLOCK_SIZE * 3, BLOCK_SIZE * 20, BLOCK_SIZE, BLOCK_SIZE), BAT);
		
		TilePainter.crop(new Rectangle(BLOCK_SIZE * 6, BLOCK_SIZE * 21, BLOCK_SIZE, BLOCK_SIZE), GHOST);
		
		
		
		TilePainter.crop(new Rectangle(BLOCK_SIZE * 6, BLOCK_SIZE * 20, BLOCK_SIZE, BLOCK_SIZE), BOAR);
		TilePainter.crop(new Rectangle(BLOCK_SIZE * 5, BLOCK_SIZE * 4, BLOCK_SIZE, BLOCK_SIZE), KEY);
		TilePainter.crop(new Rectangle(BLOCK_SIZE * 1, BLOCK_SIZE * 7, BLOCK_SIZE, BLOCK_SIZE), SWORD);
		
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 2, BLOCK_SIZE * 2, BLOCK_SIZE, BLOCK_SIZE)   , WALL);
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 2, BLOCK_SIZE * 1, BLOCK_SIZE, BLOCK_SIZE)   , OPENED_DOOR);
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 6, BLOCK_SIZE * 2, BLOCK_SIZE, BLOCK_SIZE)   , CLOSED_DOOR);
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 2, BLOCK_SIZE * 1, BLOCK_SIZE, BLOCK_SIZE)   , KEY_OPENED_DOOR);
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 7, BLOCK_SIZE * 2, BLOCK_SIZE, BLOCK_SIZE)   , KEY_CLOSED_DOOR);
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 3, BLOCK_SIZE * 2, BLOCK_SIZE, BLOCK_SIZE)   , BRICK);
		
		
		TilePainter.crop(  new Rectangle(BLOCK_SIZE * 0, BLOCK_SIZE * 5, 1, 1)   , TRANSPARENT);
		
		
		matrix = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			matrix[i][0] = WALL;
			matrix[i][cols-1] = WALL;
		}
		
		for (int j = 0; j < cols; j++) {
			matrix[0][j] = WALL;
			matrix[rows-1][j] = WALL;
		}
		
		for (int i = 0; i <= 6; i++) {
			matrix[1][1+i] = i;
		}
	}
	
	public void paint(Graphics g) {
		//painter.paint(g, GROUND, 100, 50);
		int block;
		int rows = matrix.length, cols = matrix[0].length;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				block = matrix[i][j];
				TilePainter.paint(g, block, Camera.getScreenX(BLOCK_SIZE * j), Camera.getScreenY(BLOCK_SIZE * i));
				if (activeCol == j && activeRow == i && showActiveCell  ) {
					g.setColor(activeColor);
					g.drawRect(Camera.getScreenX(BLOCK_SIZE * j), Camera.getScreenY(BLOCK_SIZE * i), BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
		
	}

}






















