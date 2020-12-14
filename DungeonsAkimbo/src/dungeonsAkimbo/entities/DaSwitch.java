package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaSwitch extends Entity {
	
	private int doorId;
	private boolean state;
	private Image onSprite, offSprite;
	
	
	
	public DaSwitch(float px, float py, int doorId) {
		this.doorId = doorId;
		this.state = false;
		this.setPosition(new Vector(px, py));
		onSprite = ResourceManager.getImage(DungeonsAkimboGame.DA_SWITCH_RSC);
		offSprite = ResourceManager.getImage(DungeonsAkimboGame.DA_SWITCH_RSC).getFlippedCopy(true, false);
		this.addImageWithBoundingBox(offSprite);
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public int flick() {
		this.state = !this.state;
		if(this.state == false) {
			this.removeImage(onSprite);
			this.addImage(offSprite);
		}else {
			this.removeImage(offSprite);
			this.addImage(onSprite);
		}
		return doorId;
	}

}
