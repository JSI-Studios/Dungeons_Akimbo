package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaGold extends Entity {
	private int pointAmount;
	private Image sprite;
	
	
	
	public DaGold(float px, float py) {
		this.pointAmount = 5;
		this.setPosition(new Vector(px,py));
		SpriteSheet miscSheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MISCSHEET_RSC), 32, 32);
		sprite = miscSheet.getSprite(2, 124);
		this.addImageWithBoundingBox(sprite);
		//todo: add Sprite
	}
	
	public int pickUp() {
		return pointAmount;
	}
}
