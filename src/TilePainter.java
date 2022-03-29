import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class TilePainter {

	static BufferedImage texture;
	static BufferedImage [] tiles = new BufferedImage[100];	
	
	public static void crop (Rectangle r, int index) {
		BufferedImage image = texture.getSubimage(r.x, r.y, r.width, r.height);
		tiles[index] = image;
	}
	public static void paint (Graphics g, int index, int x, int y) {
		g.drawImage(tiles[index] , x, y, null);
	}
	public static void paint (Graphics g) {
		g.drawImage(texture, 0, 0, null);
	}
	public static void loadTexture (String fileName) {
		File f = new File(fileName);
		try {
			texture = ImageIO.read(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ошибка чтения текстуры");
			System.exit(0);
		}
	}
}
