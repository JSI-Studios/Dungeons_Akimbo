package dungeonsAkimbo.entities;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaSpawner extends Entity {
	private Image bottomSprite, topSprite;
	private int health;
	private int cooldown;
	private Random rngGenerator;
	
	public DaSpawner(int px, int py) {
		super(px,py);
		setHealth(300);
		SpriteSheet miscSheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MISCSHEET_RSC), 32, 32);
		bottomSprite = miscSheet.getSprite(4, 116);
		topSprite = miscSheet.getSprite(4, 115);
		
		this.addImageWithBoundingBox(bottomSprite);
		this.addImage(topSprite, new Vector(0, -16f));
		
		this.cooldown = 0;
		this.rngGenerator = new Random();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int generateMob() {
		if(this.cooldown <= 0) {
			int rng = this.rngGenerator.nextInt(4);
			while(rng == 1) {
				rng = this.rngGenerator.nextInt(4);
			}
			this.cooldown = 300;
			return rng;
		} else {
			this.cooldown--;
		}
		return -1;
	}

}
