package dungeonsAkimbo.entities;

import java.util.Random;

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
	private Random rngHandler;
	
	private boolean playerPunish;
	private int attackPhase;
	
	private Animation sprite;
	private SpriteSheet spritesheet;
	
	private Animation stall;
	private boolean isStall;
	private Animation reverseStall;
	private int stallCooldown;
	
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
		// Set up stall sprite
		Image[] frames = {
				ResourceManager.getImage(DungeonsAkimboGame.STALL_ONE),
				ResourceManager.getImage(DungeonsAkimboGame.STALL_TWO),
				ResourceManager.getImage(DungeonsAkimboGame.STALL_THREE),
				ResourceManager.getImage(DungeonsAkimboGame.STALL_FOUR),
				ResourceManager.getImage(DungeonsAkimboGame.STALL_FIVE)};
		this.stall = new Animation(true);;
		for(int i = 0; i < frames.length; i++) {
			stall.addFrame(frames[i], 80);
		}
		this.stall.setLooping(false);
		this.reverseStall = new Animation(true);
		for(int i = frames.length - 1; i >= 0; i--) {
			reverseStall.addFrame(frames[i], 80);
		}
		this.reverseStall.setLooping(false);
		// Set properties 
		this.setHealth(500);
		this.setPlayerPunish(false);
		this.setAttackCooldown(0);
		this.setRest(0);
		this.setAttackPhase(0);
		this.setRngHandler(new Random());
		this.setStall(false);
	}
	
	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {
		int rng = this.getRngHandler().nextInt(8);
		int unitWidth = DungeonsAkimboGame.WIDTH / 6;
		int unitHeight = DungeonsAkimboGame.HEIGHT / 6;
		if(!this.isStall) {
			if(rng == 0) {
				this.setX(unitWidth);
				this.setY(unitHeight);
			} else if(rng == 1) {
				this.setX(unitWidth * 2);
				this.setY(unitHeight);
			} else if(rng == 2) {
				this.setX(unitWidth * 3);
				this.setY(unitHeight);
			} else if(rng == 3){
				this.setX(unitWidth);
				this.setY(unitHeight * 5);
			} else if(rng == 4) {
				this.setX(unitWidth * 2);
				this.setY(unitHeight * 5);
			} else if(rng == 5){
				this.setX(unitWidth * 3);
				this.setY(unitHeight * 5);
			} else if(rng == 6) {
				this.setX(DungeonsAkimboGame.WIDTH / 3);
				this.setY(DungeonsAkimboGame.HEIGHT / 3 + 100);
			} else {
				this.removeAnimation(this.sprite);
				if(this.getNumAnimations() == 0) {
					this.stall.restart();
					this.addAnimation(this.stall);
					this.stallCooldown = 200;
				}
				this.setStall(true);
			}
		}
	}

	@Override
	public Projectile attack(Entity player) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(final int delta) {
		if(this.isStall && this.stallCooldown >= 0) {
			this.stallCooldown--;
		}
		if(this.stallCooldown < 0) {
			// Animate reverting stall if cooldown is less than 0
			this.removeAnimation(this.stall);
			if(this.getNumAnimations() == 0) {
				this.reverseStall.restart();
				this.addAnimation(this.reverseStall);
			}
			if(this.reverseStall.isStopped()) {
				// Reset to normal sprite
				this.removeAnimation(this.reverseStall);
				this.addAnimation(this.sprite);
				this.stallCooldown = 0;
				this.isStall = false;
			}
		}
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

	public Random getRngHandler() {
		return rngHandler;
	}

	public void setRngHandler(Random rngHandler) {
		this.rngHandler = rngHandler;
	}

	public boolean isStall() {
		return isStall;
	}

	public void setStall(boolean isStall) {
		this.isStall = isStall;
	}

}
