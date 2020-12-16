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
	private int attackCooldown;
	private int rest;
	
	private boolean playerPunish;
	private int attackPhase;
	
	private Animation sprite;
	private SpriteSheet spritesheet;
	
	public DaMiniBoi(float x, float y, boolean debug) {
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
		this.sprite = new Animation(ResourceManager.getSpriteSheet(DungeonsAkimboGame.MINI_BOSS, dimensions, dimensions), 0, 3, 2, 3, true, 200, true);
		sprite.setLooping(true);
		addAnimation(sprite);
		this.setHealth(200);
		this.playerPunish = false;
		this.attackCooldown = 0;
		this.rest = 0;
		this.attackPhase = 0;
	}
	
	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {		
		if(isHit) {
			this.setHealth(this.getHealth() - 1);
			// If collide to player, stop and pause
			if(isPlayer) {
				// Trigger special attack (call multiple mobs maybe?)
				playerPunish = true;
			}
		}
	}

	@Override
	public Projectile attack(Entity player) {
		
		// Calculate vectors needed to locate the mob relative to the player
		Vector distance = this.getPosition().subtract(player.getPosition());
		double currentDirection = distance.negate().getRotation();
		
		// Actual attack interaction
		Projectile attacked = null;
		if(this.attackCooldown <= 0 && this.rest < 100) {
			attacked = new Projectile(this.getX(), this.getY(), 20, 900);
			attacked.rotate(currentDirection);
			attacked.Set_Velocity(currentDirection);
			this.attackCooldown = 10;
			this.rest++;
		} else if(this.rest >= 100 && this.rest <= 200 && this.attackPhase < 1){
			this.attackPhase = 1;
			this.rest++;
		} else if(this.rest  > 200 || this.attackPhase == 3) {
			// Reset to bullet hell
			this.attackPhase = 0;
			this.rest = 0;
		} else {
			this.rest++;
			this.attackCooldown--;
		}
		return attacked;
	}
	
	public ArrayList<Projectile> multiAttack(){
		ArrayList<Projectile> attack = new ArrayList<Projectile>();
		if(this.playerPunish) {
			// Return new attack after collision
			attack.add(new Projectile(this.getX(), this.getY() + 64, 50, 50, 1));
			attack.add(new Projectile(this.getX() - 64, this.getY() + 64, 50, 50, 1));
			attack.add(new Projectile(this.getX() + 64, this.getY() + 64, 50, 50, 1));
			attack.add(new Projectile(this.getX() - 64, this.getY(), 10, 50, 1));
			attack.add(new Projectile(this.getX() + 64, this.getY(), 10, 50, 1));
			this.playerPunish = false;
			return attack;
		} else if(this.attackPhase == 1) {
			// Begin phase 1 of attack
			attack.add(new Projectile(this.getX(), this.getY() + 64, 50, 500, .3f, 90, 2));
			attack.add(new Projectile(this.getX() - 64, this.getY() + 64, 50, 500, .3f, 90, 2));
			attack.add(new Projectile(this.getX() + 64, this.getY() + 64, 50, 500, .3f, 90 ,2));
			attack.add(new Projectile(this.getX() - 64, this.getY(), 10, 500, .3f, -180, 2));
			attack.add(new Projectile(this.getX() + 64, this.getY(), 10, 500, .3f, 0, 2));
			this.attackPhase = 2;
			return attack;
		}
		return null;
	}
	
	public boolean isActive() {
		return !sprite.isStopped();
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

}
