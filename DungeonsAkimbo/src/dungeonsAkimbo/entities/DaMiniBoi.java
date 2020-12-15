package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaMiniBoi extends Entity implements DaEnemy {

	private int health;
	private Vector velocity;
	private float initX;
	private float initY;
	

	private SpriteSheet spritesheet;
	private Animation sprite;
	
	public DaMiniBoi(float x, float y, boolean debug) {
		super(x, y);
		super.setDebug(debug);
		this.initX = x;
		this.initY = y;
		this.sprite = new Animation(ResourceManager.getSpriteSheet(DungeonsAkimboGame.MINI_BOSS, 96, 96), 0, 3, 2, 3, true, 200, true);
		sprite.setLooping(true);
		addAnimation(sprite);
	}
	
	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Projectile attack(Entity player) {
		// TODO Auto-generated method stub
		return null;
	}

}
