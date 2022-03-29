
public class Boar extends Character {

	public Boar(Map map) {
		super(map);
		main.setSkin(Map.BOAR);
		second.setSkin(Map.TRANSPARENT);
		heroSearch = true;
		
		damage = 30;
	}

}
