package dungeonsAkimbo.entities;

import java.util.ArrayDeque;
import java.util.stream.IntStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaMob extends Entity implements DaEnemy, Mover {

	private int health;
	private int type;
	private Vector velocity;
	private float initX;
	private float initY;
	private float bounceCooldown;
	private int direction;
	private ArrayDeque<Path.Step> path;
	private int tileSize = 32;
	private int tileCenter = tileSize / 2;
	
	private SpriteSheet spritesheet;
	private Animation sprite;
	
	public DaMob(float x, float y, int type, boolean debug) {
		super(x,y);
		super.setDebug(debug);
		setInitX(x);
		setInitY(y);
		this.setBounceCooldown(0);
		this.setType(type);
		this.direction = 0;
		this.setSprite(new Animation(false));
		this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_ZERO), 32, 32, 0, 0);
		/* Begin handling creation of sprite for the mob */
		// Add image colliding box to mob before adding actual sprites
		Image tempSprite = spritesheet.getSprite(1, this.direction).getScaledCopy(.5f);
		this.addImageWithBoundingBox(tempSprite);
		this.removeImage(tempSprite);
		if(type == 0) {
			// Mob Zero: Spoopy Sprite
			this.setHealth(35);
			IntStream.range(0, 4).forEachOrdered(n -> {
				this.getSprite().addFrame(this.spritesheet.getSprite(1, n).getScaledCopy(.5f), 1);
			});
		} else if(type == 1){
			// Mob One: Mommy Sprite
			this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_ONE), 32, 32, 0, 0);
			setHealth(20);
			IntStream.range(0, 4).forEachOrdered(n -> {
				this.getSprite().addFrame(this.spritesheet.getSprite(1, n).getScaledCopy(.5f), 1);
			});
		} else {
			// Mob One: Skully Sprite
			this.spritesheet = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_TWO), 32, 32, 0, 0);
			setHealth(20);
			IntStream.range(0, 4).forEachOrdered(n -> {
				this.getSprite().addFrame(this.spritesheet.getSprite(1, n).getScaledCopy(.5f), 1);
			});
		}
		this.addAnimation(getSprite());
		this.velocity = new Vector(0, 0);
		this.setPath(null);
	}

	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {
		if(isHit) {
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
	public Projectile attack(Entity player) {
		
		// Calculate vectors needed to locate the mob relative to the player
		Vector distance = this.getPosition().subtract(player.getPosition());
		double currentDirection = distance.negate().getRotation();		// Remove if not used later
		
		// Face the player
		this.facePlayer(distance);
		
		// Actual attack interaction
		Projectile attacked = null;
		if(this.getBounceCooldown() > 0) {
			// Basic mob collided, stopped and recover
			this.setBounceCooldown(this.getBounceCooldown() - 1);
		} else if(type == 0) {
			// Just track the player and collide with them
			this.velocity = distance.unit().negate();
			this.velocity = this.velocity.unit().scale(.05f);
		} else if(type == 1) {
			// Mob One will shoot at the player
			attacked = new Projectile(this.getX(), this.getY(), 20);
			attacked.rotate(currentDirection);
			attacked.Set_Velocity(currentDirection);
			this.setBounceCooldown(30);
		} else if(type == 2) {
			// Mob will melee attack the player, use Projectile/Hitbox to deal with collision
			if(this.path != null && this.path.isEmpty()) {
				// Reset path to null for DaLogic to give new path
				this.path = null;
			}
			if(distance.length() <= 100) {
				if(this.direction == 0) {
					// Face down, attack down
					attacked = new Projectile(this.getX(), this.getY() + 16, 0, 20, 30, true);
				} else if(this.direction == 1) {
					// Face left, attack left
					attacked = new Projectile(this.getX() - 16, this.getY(), 0, 20, 30, true);
				} else if(this.direction == 2) {
					// Face right, attack right
					attacked = new Projectile(this.getX() + 16, this.getY(), 0, 20, 30, true);
				} else  {
					// Face up, attack up
					attacked = new Projectile(this.getX(), this.getY() - 16, 0, 20, 30, true);
				}
				this.setBounceCooldown(30);
			}
			
		}
		return attacked;
	}
	
	// Given the distance between the player and the mob with respect to the mob, adjust the
	// direction of the mob to face the player
	public void facePlayer(Vector distance) {
		// Current direction is the angle that the player is relative to the current mob
		double currentDirection = distance.negate().getRotation();
		if(this.direction != 0 && (currentDirection >= 45 && currentDirection < 135)) {
			// Rotate down
			this.direction = 0;
			this.getSprite().setCurrentFrame(this.direction);
		} else if (this.direction != 1 && (currentDirection < -135 && currentDirection < 135)) {
			// Rotate left
			this.direction = 1;
			this.getSprite().setCurrentFrame(this.direction);
		} else if (this.direction != 2 && ( -45 <= currentDirection && currentDirection < 45)) {
			// Rotate right
			this.direction = 2;
			this.getSprite().setCurrentFrame(this.direction);
		} else if (this.direction != 3 && (currentDirection >= -135 && currentDirection < -45))  {
			// Rotate up
			this.direction = 3;
			this.getSprite().setCurrentFrame(this.direction);
		}
		
	}
	
	private Vector followPath() {
		if(this.path != null) {
			// Peek at the top of the path stack and get positions
			Step nextStep = this.path.peek();
			//System.out.print("(" + nextStep.getX() + ", " + nextStep.getY() + ") " + "\n");
			Vector currentPosition = new Vector(this.getX(), this.getY());
			Vector targetPosition =  new Vector((nextStep.getX() * tileSize) + tileCenter, (nextStep.getY() * tileSize) + tileCenter);
			// Return vector to next position, update the pathing if a tile has been reached
			final double angleToStepTo = currentPosition.angleTo(targetPosition);
			if(currentPosition.epsilonEquals(targetPosition, 10f)) {
				this.path.pop();
			}
			Vector nextPosition = Vector.getVector(angleToStepTo, .1f);
			return nextPosition;
		} else {
			// Return a still Vector (don't move)
			return new Vector(0, 0);
		}
	}
	
	public void update(final int delta) {
		// Move the sprite
		if(this.path != null && !this.path.isEmpty()) {
			// Update using pathing rules
			translate(this.followPath().scale(delta));
		} else {
			// Move the sprite
			translate(this.velocity.scale(delta));
		}
	}
	
	public boolean isDead() {
		return this.health <= 0;
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

	public Animation getSprite() {
		return sprite;
	}

	public void setSprite(Animation sprite) {
		this.sprite = sprite;
	}

	public ArrayDeque<Path.Step> getPath() {
		return path;
	}

	public void setPath(ArrayDeque<Path.Step> path) {
		this.path = path;
	}
	
}
