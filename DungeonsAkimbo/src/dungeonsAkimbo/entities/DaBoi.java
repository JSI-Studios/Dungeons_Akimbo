package dungeonsAkimbo.entities;

import java.util.ArrayList;
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

	// Properties of the boss
	private int health;
	private Vector velocity;
	private float initX;
	private float initY;
	private int attackCooldown;
	private int rest;
	private Random rngHandler;
	private int attackPhase;
	private int movementDelay;
	
	// Tracking interaction
	private boolean playerPunish;
	
	// Normal sprite
	private Animation sprite;
	private SpriteSheet spritesheet;
	
	// Stalling sprite
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
		this.stall = new Animation(true);
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
		this.setHealth(600);
		this.setPlayerPunish(false);
		this.setAttackCooldown(0);
		this.setRest(0);
		this.setAttackPhase(1);
		this.setRngHandler(new Random());
		this.setStall(false);
		this.movementDelay = -1000;
	}
	
	@Override
	public void collisionAction(boolean isHit, boolean isPlayer) {
		int rng = this.getRngHandler().nextInt(8);
		int unitWidth = DungeonsAkimboGame.WIDTH / 6;
		int unitHeight = DungeonsAkimboGame.HEIGHT / 6;
		if(!this.isStall) {
			if(rng == 0) {
				// Move top left
				this.setX(unitWidth);
				this.setY(unitHeight);
			} else if(rng == 1) {
				// Move top middle
				this.setX(unitWidth * 2);
				this.setY(unitHeight);
			} else if(rng == 2) {
				// Move top right
				this.setX(unitWidth * 3);
				this.setY(unitHeight);
			} else if(rng == 3){
				// Move bottom left
				this.setX(unitWidth);
				this.setY(unitHeight * 5);
			} else if(rng == 4) {
				// Move bottom middle
				this.setX(unitWidth * 2);
				this.setY(unitHeight * 5);
			} else if(rng == 5){
				// Move bottom right
				this.setX(unitWidth * 3);
				this.setY(unitHeight * 5);
			} else if(rng == 6) {
				// Move middle
				this.setX(DungeonsAkimboGame.WIDTH / 3);
				this.setY(DungeonsAkimboGame.HEIGHT / 3 + 100);
			} else {
				// Begin stalling
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
		// Calculate vectors needed to locate the boss relative to the player
		Vector distance = this.getPosition().subtract(player.getPosition());
		double currentDirection = distance.negate().getRotation();
		
		// Actual attack interaction
		Projectile attacked = null;
		if(this.isStall) {
			if(this.attackCooldown <= 0) {
				attacked = new Projectile(this.getX(), this.getY(), 20);
				attacked.rotate(currentDirection);
				attacked.Set_Velocity(currentDirection);
				this.attackCooldown = 10;
			} else {
				this.attackCooldown--;
			}
		}
		return attacked;
	}
	
	public ArrayList<Projectile> multiAttack(Player player){
		ArrayList<Projectile> attack = new ArrayList<Projectile>();
		int rng;
		if(!this.isStall) {
			if(this.attackPhase == 0) {
				// Phase 0: Randomly jump across the map, may stall
				if(this.movementDelay < -100 && this.attackCooldown == 0) {
					this.attackCooldown = 200;
				} else if (this.movementDelay <= 0 && this.attackCooldown <= 0) {
					this.setAttackPhase(1);
					this.movementDelay = -1000;
					this.attackCooldown = 0;
				} else if(this.movementDelay <= 0) {
					rng = this.getRngHandler().nextInt(8);		
					int unitWidth = DungeonsAkimboGame.WIDTH / 6;
					int unitHeight = DungeonsAkimboGame.HEIGHT / 6;
					if(rng == 0) {
						// Move top left
						this.setX(unitWidth);
						this.setY(unitHeight);
					} else if(rng == 1) {
						// Move top middle
						this.setX(unitWidth * 2);
						this.setY(unitHeight);
					} else if(rng == 2) {
						// Move top right
						this.setX(unitWidth * 3);
						this.setY(unitHeight);
					} else if(rng == 3){
						// Move bottom left
						this.setX(unitWidth);
						this.setY(unitHeight * 5);
					} else if(rng == 4) {
						// Move bottom middle
						this.setX(unitWidth * 2);
						this.setY(unitHeight * 5);
					} else if(rng == 5){
						// Move bottom right
						this.setX(unitWidth * 3);
						this.setY(unitHeight * 5);
					} else if(rng == 6) {
						// Move middle
						this.setX(DungeonsAkimboGame.WIDTH / 3);
						this.setY(DungeonsAkimboGame.HEIGHT / 3 + 100);
					}
					this.movementDelay = 35;
					this.attackCooldown--;
					if (rng == 7) {
						// Begin stalling, break away 
						this.removeAnimation(this.sprite);
						if(this.getNumAnimations() == 0) {
							this.stall.restart();
							this.addAnimation(this.stall);
							this.stallCooldown = 100;
						}
						this.setAttackCooldown(0);
						this.movementDelay = -1000;
						this.setStall(true);
						this.setAttackPhase(1);
					} else {
						// Spam projectiles at the spot
						attack.add(new Projectile(this.getX(), this.getY() + 64, 50, 500, .08f, 90, 1));
						attack.add(new Projectile(this.getX() - 64, this.getY() + 64, 50, 500, .08f, 90, 1));
						attack.add(new Projectile(this.getX() + 64, this.getY() + 64, 50, 500, .08f, 90 ,1));
						attack.add(new Projectile(this.getX() - 64, this.getY(), 10, 500, .08f, -180, 1));
						attack.add(new Projectile(this.getX() + 64, this.getY(), 10, 500, .08f, 0, 1));
						attack.add(new Projectile(this.getX(), this.getY() - 64, 50, 500, .08f, -90, 1));
						attack.add(new Projectile(this.getX() - 64, this.getY() - 64, 50, 500, .08f, -90, 1));
						attack.add(new Projectile(this.getX() + 64, this.getY() - 64, 50, 500, .08f, -90 , 1));
					}
				} else {
					this.attackCooldown--;
					this.movementDelay--;
				}
			} else if(this.attackPhase == 1) {
				// Pause and rest a bit before attacking again.
				if(this.movementDelay < 0) {
					this.movementDelay = 1000;
					this.setAttackCooldown(50);
				} else if(this.attackCooldown >= 0) {
					this.attackCooldown--;
				} else {
					this.movementDelay = -1000;
					this.setAttackCooldown(0);
					// Randomly switch phases
					rng = this.getRngHandler().nextInt(3);
					while(rng == 1) {
						rng = this.getRngHandler().nextInt(2);
					}
					this.attackPhase = rng;
				}
			} else if(this.attackPhase == 2) {
				Vector distance = this.getPosition().subtract(player.getPosition());
				double currentDirection = distance.negate().getRotation();
				Projectile attacked = new Projectile(this.getX(), this.getY(), 80, 500, .7f, -90, 2);
				attacked.rotate(currentDirection);
				attacked.Set_Velocity(currentDirection);
				attack.add(attacked);
				this.attackPhase = 1;
			}
			return attack;
		}
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
