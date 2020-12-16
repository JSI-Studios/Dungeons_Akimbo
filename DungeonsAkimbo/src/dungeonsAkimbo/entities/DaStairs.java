package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaStairs extends Entity {
	private int direction;
	private boolean status;
	private Image up1, up2, dwn;
	
	public DaStairs(float px, float py, int type) {
		super(px,py);
		setStatus(false);
		SpriteSheet miscSheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MISCSHEET_RSC), 32, 32);
		up1 = miscSheet.getSprite(7, 40);
		up2 = miscSheet.getSprite(7, 39);
		dwn = miscSheet.getSprite(7, 38);
		if(type == 328) {
			this.addImageWithBoundingBox(up1);
			this.addImage(up2, new Vector(0, -32f));
			direction = 1;
			
		}else {
			this.addImageWithBoundingBox(dwn);
			direction = 0;
		}
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getDirection() {
		return direction;
	}
	
	
}
