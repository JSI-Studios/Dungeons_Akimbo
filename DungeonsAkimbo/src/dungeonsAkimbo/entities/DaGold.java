package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaGold extends Entity {
	private int pointAmount;
	
	public DaGold(float px, float py) {
		this.pointAmount = 5;
		this.setPosition(new Vector(px,py));
		
		//todo: add Sprite
	}
	
	public int pickUp() {
		return pointAmount;
	}
}
