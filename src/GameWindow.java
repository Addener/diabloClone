import javax.swing.JFrame;

public class GameWindow extends JFrame  {

	public GameWindow() {
		
		setSize(1024, 768);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel p = new GamePanel();
		add(p);
		
		addKeyListener(p);
		
		revalidate();		
	}
	
	
}
