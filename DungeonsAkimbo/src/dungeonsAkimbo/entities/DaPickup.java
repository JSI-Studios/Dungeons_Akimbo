package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaPickup extends Entity{
	
	private int type;  // 1 = health 2 = ammo 3 = no reload
	private Image ammo, health, reload;
	
	
	public DaPickup(float px, float py, int type) {
		super(px,py);
		this.type = type;
		ammo = ResourceManager.getImage(DungeonsAkimboGame.DA_AMMO_RSC);
		health = ResourceManager.getImage(DungeonsAkimboGame.DA_HEALTH_RSC);
		reload = ResourceManager.getImage(DungeonsAkimboGame.DA_CANDYRELOAD_RSC);
		//todo: sprites;
		if(type == 0) {
			this.addImageWithBoundingBox(health.getScaledCopy(1.25f));
		}else if (type == 1) {
			this.addImageWithBoundingBox(ammo.getScaledCopy(.175f));
		}else if (type == 2) {
			this.addImageWithBoundingBox(reload.getScaledCopy(1.25f));
		}
	}
	
	
	
	public int pickedUp() {
		return this.type;		
	}

}
