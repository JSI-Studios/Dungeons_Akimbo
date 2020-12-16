package dungeonsAkimbo.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaBoi extends Entity implements DaEnemy {

	private int dimensions = 96;

	private int health;
	private Vector velocity;
	private float initX;
	private float initY;
	private int attackCooldown;
	private int rest;
	
	private boolean playerPunish;
	private int attackPhase;
	
	private Animation sprite;
	private SpriteSheet spritesheet;
	
	public DaBoi(float x, float y, boolean debug) {
		super(x, y);
		super.setDebug(debug);
		this.setInitX(x);
		this.setInitY(y);
		this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MINI_BOSS), 70, 70, 0, 0);
		// Add image colliding box to mob 
		Image tempSprite = spritesheet.getSprite(1, 0);
		this.addImageWithBoundingBox(tempSprite);
		this.removeImage(tempSprite);
		// Add animated sprite
		this.sprite = new Animation(ResourceManager.getSpriteSheet(DungeonsAkimboGame.MINI_BOSS, dimensions, dimensions), 0, 2, 2, 2, true, 200, true);
		sprite.setLooping(true);
		addAnimation(sprite);
		this.setHealth(200);
		this.setPlayerPunish(false);
		this.setAttackCooldown(0);
		this.setRest(0);
		this.setAttackPhase(0);
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public float getInitX() {
		return initX;
	}

	public void setInitX(float initX) {
		this.initX = initX;
	}

	public float getInitY() {
		return initY;
	}

	public void setInitY(float initY) {
		this.initY = initY;
	}

	public int getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(int attackCooldown) {
		this.attackCooldown = attackCooldown;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public boolean isPlayerPunish() {
		return playerPunish;
	}

	public void setPlayerPunish(boolean playerPunish) {
		this.playerPunish = playerPunish;
	}

	public int getAttackPhase() {
		return attackPhase;
	}

	public void setAttackPhase(int attackPhase) {
		this.attackPhase = attackPhase;
	}

}
