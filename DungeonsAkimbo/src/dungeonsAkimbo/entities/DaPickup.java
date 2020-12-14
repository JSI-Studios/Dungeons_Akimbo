package dungeonsAkimbo.entities;

import jig.Entity;

public class DaPickup extends Entity{
	
	private int type;  // 1 = health 2 = ammo 3 = no reload
	
	
	public DaPickup(float px, float py, int type) {
		this.type = type;
		
		//todo: sprites
		
	}
	
	
	
	public int pickedUp() {
		return this.type;		
	}

}
