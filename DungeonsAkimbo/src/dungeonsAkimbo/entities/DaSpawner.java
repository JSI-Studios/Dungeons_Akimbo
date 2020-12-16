package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaSpawner extends Entity {
	private Image bottomSprite, topSprite;
	private int health;
	
	public DaSpawner(int px, int py) {
		super(px,py);
		setHealth(100);
		SpriteSheet miscSheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MISCSHEET_RSC), 32, 32);
		bottomSprite = miscSheet.getSprite(4, 116);
		topSprite = miscSheet.getSprite(4, 115);
		
		this.addImageWithBoundingBox(bottomSprite);
		this.addImage(topSprite, new Vector(0, -16f));
		
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
