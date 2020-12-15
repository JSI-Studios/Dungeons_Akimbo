package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaGoldPouch extends Entity {
	private int pointAmount;
	
	public DaGoldPouch(float px, float py) {
		this.pointAmount = 25;
		this.setPosition(new Vector(px, py));
		//todo: add sprite
	}
	
	public int pickUp() {
		return pointAmount;
	}
}
