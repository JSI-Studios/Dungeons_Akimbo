package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;
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
	private int direction;
	
	private SpriteSheet spritesheet;
	private Image sprite;
	
	public DaMob(float x, float y, int type, boolean debug) {
		super(x,y);
		super.setDebug(debug);
		setInitX(x);
		setInitY(y);
		this.setBounceCooldown(0);
		this.setType(type);
		this.direction = 0;
		this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_ZERO), 32, 32, 0, 0);
		if(type == 0) {
			this.sprite = spritesheet.getSprite(1, this.direction);
			setHealth(20);
			addImageWithBoundingBox(this.sprite.getScaledCopy(.5f));
		} else {
			this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_ONE), 32, 32, 0, 0);
			this.sprite = spritesheet.getSprite(1, this.direction);
			setHealth(20);
			addImageWithBoundingBox(this.sprite.getScaledCopy(.5f));
		}
		this.velocity = new Vector(0, 0);
	}

	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {
		if(isHit) {
			System.out.println(this.health);
			// Lost health and gain invincibility for a bit
			this.setHealth(this.getHealth() - 1);
			this.setBounceCooldown(20);
			// If collide to player, stop and pause
			if(isPlayer) {
				this.velocity = new Vector(0, 0);
			}
		}
	}

	@Override
	public void attack(Entity player) {
		Vector distance = this.getPosition().subtract(player.getPosition());
		if(this.getBounceCooldown() > 0 && type == 0) {
			// Basic mob collided, stopped and recover
			this.setBounceCooldown(this.getBounceCooldown() - 1);
		} else if(type == 0) {
			// Just track the player and collide with them
			this.velocity = distance.unit().negate();
			this.velocity = this.velocity.unit().scale(.05f);
		}

		// Get angle, determine which direction sprite goes
		double direction = distance.negate().getRotation();
		if(this.direction != 0 && (direction >= 45 && direction < 135)) {
			// Rotate down
			this.removeImage(this.sprite);
			this.direction = 0;
			this.sprite = this.spritesheet.getSprite(1, this.direction).getScaledCopy(.5f);
			addImage(this.sprite);
		} else if (this.direction != 1 && (direction < -135 && direction < 135)) {
			// Rotate right
			this.removeImage(this.sprite);
			this.direction = 1;
			this.sprite = this.spritesheet.getSprite(1, this.direction).getScaledCopy(.5f);
			addImage(this.sprite);
		} else if (this.direction != 2 && ( -45 <= direction && direction < 45)) {
			// Rotate left
			this.removeImage(this.sprite);
			this.direction =2;
			this.sprite = this.spritesheet.getSprite(1, this.direction).getScaledCopy(.5f);
			addImage(this.sprite);
		} else if (this.direction != 3 && (direction >= -135 && direction < -45))  {
			// Rotate up
			this.removeImage(this.sprite);
			this.direction =3;
			this.sprite = this.spritesheet.getSprite(1, this.direction).getScaledCopy(.5f);
			addImage(this.sprite);
		}
	}
	
	public void update(final int delta) {
		// Move the sprite
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
