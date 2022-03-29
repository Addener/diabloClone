import java.awt.Rectangle;

public class Game {

	public static void main(String[] args) {
		
		TilePainter.loadTexture("sprites.png");
		
		Rectangle r = new Rectangle(
								5*Map.BLOCK_SIZE,
								18*Map.BLOCK_SIZE,
								Map.BLOCK_SIZE,
								Map.BLOCK_SIZE
								);
		TilePainter.crop(r, 10);
		
		
		GameWindow w = new GameWindow();
		

	}

}
