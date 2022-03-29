
public class Bat extends Character {

	public Bat(Map map) {
		super(map);
		main.setSkin(Map.BAT);
		second.setSkin(Map.TRANSPARENT);
		
		main.setSpeed(Map.BLOCK_SIZE * 3);
		
		damage = 2;
	}

}
