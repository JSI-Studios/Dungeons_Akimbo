package dungeonsAkimbo.entities;

import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaMob extends Entity implements DaEnemy {

	private int health;
	private int type;
	private Vector velocity;
	private float initX;
	private float initY;
	private float bounceCooldown;
	
	private SpriteSheet sprite;
	
	public DaMob(float x, float y, int type, boolean debug) {
		super(x,y);
		super.setDebug(debug);
		setInitX(x);
		setInitY(y);
		this.setBounceCooldown(0);
		this.setType(type);
		this.sprite = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_ZERO), 32, 32, 4, 0);
		if(type == 0) {
			setHealth(20);
			addImageWithBoundingBox(sprite.getSprite(0, 0).getScaledCopy(.5f));
		} else {
			setHealth(5);
			addImageWithBoundingBox(sprite.getSprite(0, 0).getScaledCopy(.5f));
		}
	}

	@Override
	public boolean checkCollision(Entity object, boolean isPlayer) {
		boolean didCollide = false;
		// Check if mob collided with an entity
		if(this.collides(object) != null && this.getBounceCooldown() == 0) {
			System.out.println("Collide!");
			// Lost health and gain invincibility for a bit
			this.setHealth(this.getHealth() - 1);
			this.setBounceCooldown(20);
			// If collide to player, stop and pause
			if(isPlayer) {
				this.velocity = new Vector(0, 0);
			}
			didCollide = true;
		}
		return didCollide;
	}

	@Override
	public void attack(Entity player) {
		if(this.getBounceCooldown() > 0 && type == 0) {
			// Basic mob collided, stopped and recover
			this.setBounceCooldown(this.getBounceCooldown() - 1);
		} else if(type == 0) {
			// Just track the player and collide with them
			Vector distance = this.getPosition().subtract(player.getPosition());
			this.velocity = distance.unit().negate();
			this.velocity = this.velocity.unit().scale(.05f);
		}
		
	}
	
	public void update(final int delta) {
		translate(this.velocity.scale(delta));
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public float getBounceCooldown() {
		return bounceCooldown;
	}

	public void setBounceCooldown(float bounceCooldown) {
		this.bounceCooldown = bounceCooldown;
	}
	
}
