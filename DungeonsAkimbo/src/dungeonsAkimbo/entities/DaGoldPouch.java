package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaGoldPouch extends Entity {
	private int pointAmount;
	private Image sprite;
	
	public DaGoldPouch(float px, float py) {
		this.pointAmount = 25;
		this.setPosition(new Vector(px, py));
		//todo: add sprite
		SpriteSheet miscSheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MISCSHEET_RSC), 32, 32);
		sprite = miscSheet.getSprite(2, 123);
		this.addImageWithBoundingBox(sprite);
	}
	
	public int pickUp() {
		return pointAmount;
	}
}
