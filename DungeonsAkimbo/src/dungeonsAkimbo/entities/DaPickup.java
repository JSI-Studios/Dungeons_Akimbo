package dungeonsAkimbo.entities;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaPickup extends Entity{
	
	private int type;  // 1 = health 2 = ammo 3 = no reload
	
	
	public DaPickup(float px, float py, int type) {
		this.type = type;
		this.setPosition(new Vector(px, py));
		//todo: sprites
		if(type == 1) {
			this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.DA_HEALTH_RSC));
		}else if (type == 2) {
			this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.DA_HEALTH_RSC));
		}else if (type == 3) {
			this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.DA_HEALTH_RSC));
		}
	}
	
	
	
	public int pickedUp() {
		return this.type;		
	}

}
