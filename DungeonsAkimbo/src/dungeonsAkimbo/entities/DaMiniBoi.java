package dungeonsAkimbo.entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaMiniBoi extends Entity implements DaEnemy {
	
	private int dimensions = 96;

	private int health;
	private Vector velocity;
	private float initX;
	private float initY;
	
	private boolean playerPunish;
	
	private Animation sprite;
	private SpriteSheet spritesheet;
	
	public DaMiniBoi(float x, float y, boolean debug) {
		super(x, y);
		super.setDebug(debug);
		this.initX = x;
		this.initY = y;
		this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MINI_BOSS), 70, 70, 0, 0);
		// Add image colliding box to mob 
		Image tempSprite = spritesheet.getSprite(1, 0);
		this.addImageWithBoundingBox(tempSprite);
		this.removeImage(tempSprite);
		// Add animated sprite
		this.sprite = new Animation(ResourceManager.getSpriteSheet(DungeonsAkimboGame.MINI_BOSS, dimensions, dimensions), 0, 3, 2, 3, true, 200, true);
		sprite.setLooping(true);
		addAnimation(sprite);
		this.health = 200;
		this.playerPunish = false;
	}
	
	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {		
		if(isHit) {
			this.health -= 1;
			// If collide to player, stop and pause
			if(isPlayer) {
				// Trigger special attack (call multiple mobs maybe?)
				playerPunish = true;
			}
		}
	}

	@Override
	public Projectile attack(Entity player) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Projectile> multiAttack(){
		if(this.playerPunish) {
			// Return new attack after collision
			ArrayList<Projectile> attack = new ArrayList<Projectile>();
			attack.add(new Projectile(this.getX(), this.getY() + 80, 10, 50, 1));
			this.playerPunish = false;
			return attack;
		}
		return null;
	}
	
	public boolean isActive() {
		return !sprite.isStopped();
	}

}
