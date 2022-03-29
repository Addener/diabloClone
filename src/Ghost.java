
public class Ghost extends Character {

	public Ghost(Map map) {
		super(map);
		main.setSkin(Map.GHOST);
		second.setSkin(Map.TRANSPARENT);
		heroSearch = false;
		main.addToWhiteList(Map.KEY_CLOSED_DOOR);
		main.addToWhiteList(Map.KEY_OPENED_DOOR);
		main.addToWhiteList(Map.CLOSED_DOOR);
		main.addToWhiteList(Map.OPENED_DOOR);
		
		heroSearchTime = 5;
		damage = 10;
		
	}

	
	
}
