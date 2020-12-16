package dungeonsAkimbo.entities;



import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity {
	
	private ArrayList<Weapon> gunBackpack;
	
	private Ranged primaryWeapon;
	private Ranged shotty;
	private Ranged sniper;
	private Ranged pistol;
	private Ranged smg;
	private Ranged assault;
	
	
	private float speed;
	private int currentHealth;
	private int maxHealth;
	private int dodgeTimer = 1000;
	private int dodgeCD = 3000;
	private int backpackIndex; 
	
	private boolean dodging;
	private boolean rest;
	private boolean godMode;
	private boolean canDodge;

	
	private Vector velocity;
	
	private Animation sprite, walkUp, walkDown, walkLeft, walkRight, current;
	private SpriteSheet sprites;

	private int points;
	
	public Player(final float x, final float y, int type) {
		super(x, y);
		// Max health can either be set from constructor, or a be statically 
		// constant, deal with later
		godMode = false;
		canDodge = true;
		points = 0;
		gunBackpack = new ArrayList<Weapon>();
		
		this.sprite = new Animation(false);
		//this.sprites = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_PLAYER_RSC), 32, 32, 0, 0);
		
		setMax_health(500);
		setCurrent_health(getMax_health());
		speed = 0.5f;
		this.backpackIndex = 0;
		
		if (type == 1) {
			shotty = new DaShotty();
			pistol = new DaPistol();
			this.primaryWeapon = shotty;
			gunBackpack.add(shotty);
			gunBackpack.add(pistol);
			this.sprites = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_SCHOOL_GIRL1_RSC), 32, 32, 0, 0);
		} else if (type == 2) {
			sniper = new DaSniper();
			pistol = new DaPistol();
			this.primaryWeapon = sniper;
			gunBackpack.add(sniper);
			gunBackpack.add(pistol);
			this.sprites = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_SCHOOL_GIRL2_RSC), 32, 32, 0, 0);
		} else if (type == 3) {
			assault = new DaAssault();
			pistol = new DaPistol();
			this.primaryWeapon = assault;
			gunBackpack.add(assault);
			gunBackpack.add(pistol);
			this.sprites = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MALE1_RSC), 32, 32, 0, 0);
		} else if (type == 4) {
			smg = new DaSMG();
			pistol = new DaPistol();
			this.primaryWeapon = smg;
			gunBackpack.add(smg);
			gunBackpack.add(pistol);
			this.sprites = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_MALE2_RSC), 32, 32, 0, 0);
		}
		
		this.addImageWithBoundingBox(this.sprites.getSprite(1, 0));
		this.removeImage(this.sprites.getSprite(1, 0));
		
		this.sprite.addFrame(this.sprites.getSprite(1, 0), 1);		//Player face down
		this.sprite.addFrame(this.sprites.getSprite(1, 1), 1);		//Player face left
		this.sprite.addFrame(this.sprites.getSprite(1, 2), 1);		//Player face right
		this.sprite.addFrame(this.sprites.getSprite(1, 3), 1);		//Player face up
		
		this.walkDown = new Animation(sprites, 0,0, 2,0, true, 2, true);		//Player walk down
		this.walkLeft = new Animation(sprites, 0,1, 2,1, true, 2, true);	//Player walk left	
		this.walkRight = new Animation(sprites, 0,2, 2,2, true, 2, true);	//Player walk right
		this.walkUp = new Animation(sprites, 0,3, 2,3, true, 2, true);	//Player walk up
		
		this.current = walkDown;
		this.addAnimation(current);
		this.setRest(true);
	}
	
	public void gunSelect(int i) {
		this.primaryWeapon = null;
		this.backpackIndex = i;
		this.primaryWeapon = (Ranged) gunBackpack.get(i);
	}
	
	public void getNextGun() {
		if (this.gunBackpack.get(backpackIndex) == gunBackpack.get(gunBackpack.size()-1))  {
			this.backpackIndex = 0;
			this.primaryWeapon = (Ranged) gunBackpack.get(0);
		} else {
			System.out.println("gun index is " + this.backpackIndex);
			this.primaryWeapon = (Ranged) gunBackpack.get(backpackIndex+1);
			this.backpackIndex++;
		}
	}
	
	public Ranged getPrimaryWeapon() {
		return primaryWeapon;
	}
	
	public Object Shoot(double inAngle) {
		
		if (this.primaryWeapon == assault) {
			this.primaryWeapon.primaryAtk();
		}
		return this.primaryWeapon.primaryAtk(inAngle);	
	}
	
	public void swapWep(Weapon s) {
		this.primaryWeapon = (Ranged) s;
	}
	
	public void doDodge(final int delta, int scaler) {
		this.speed = scaler*this.speed;
		this.dodging = true;
	}
	
	public boolean isDodging() {
		return this.dodging;
	}
	
	public void setVelocity(final Vector v) {
		velocity = v;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public void setSpeed(float s) {
		this.speed = s;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public int getHash() {
		int hash = 17*(int)this.getX()^19 * (int)(this.getY());
		return hash;
	}
	
	public int getMax_health() {
		return maxHealth;
	}

	public void setMax_health(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getCurrent_health() {
		return currentHealth;
	}

	public void setCurrent_health(int current_health) {
		this.currentHealth = current_health;
	}
	
	public void godMode() {
		this.godMode = !this.godMode;
	}
	
	public boolean getGodMode() {
		return this.godMode;
	}
	
	public void update(final int delta) {
		
		if (this.godMode == true) {
			//System.out.println("god mode is " + this.godMode);
			this.maxHealth = 10000;
			this.currentHealth = 10000;
		} else {
			//System.out.println("god mode is " + this.godMode);
			//System.out.println("health is " + this.currentHealth);
			this.maxHealth = 500;
		}
		
		if(this.currentHealth < 0) {
			this.currentHealth = 0;
		}

		if (this.dodging == true) {
			//System.out.println("dodging now asdfasdfasdf");
			this.dodgeTimer -= delta;
			this.canDodge = false;
		}
		
		if (this.canDodge == false) {
			this.dodgeCD -= delta;
		}
		
		if (this.dodgeCD <= 0) {
			this.canDodge = true;
			this.dodgeCD = 3000;
		}
		
		if (this.dodgeTimer <= 0) {
			this.speed = 1f;
			this.dodging = false;
			this.dodgeTimer = 500;	
		}
		
		if (Math.abs(((Entity) this.primaryWeapon).getRotation()) < 45) { 		//Player facing right
			if(current.equals(walkRight) &&  velocity.length() != 0) {
				current.start();				
			}else if( current.equals(walkRight) && velocity.length() == 0) {
				current.stop();
				current.setCurrentFrame(1);
			}else if(!current.equals(walkRight)) {
				current.stop();
				this.removeAnimation(current);
				this.addAnimation(walkRight);
				current = walkRight;
			}		
			
		} else if (Math.abs(((Entity) this.primaryWeapon).getRotation()) > 135) {		//Player facing left
			if(current.equals(walkLeft) &&  velocity.length() != 0) {
				current.start();				
			}else if( current.equals(walkLeft) && velocity.length() == 0) {
				current.stop();
				current.setCurrentFrame(1);
			}else if(!current.equals(walkLeft)) {
				current.stop();
				this.removeAnimation(current);
				this.addAnimation(walkLeft);
				current = walkLeft;
			}
		} else if (((Entity) this.primaryWeapon).getRotation() > 45 && ((Entity) this.primaryWeapon).getRotation() < 135) {		//Player facing down
			if(current.equals(walkDown) &&  velocity.length() != 0) {
				current.start();				
			}else if( current.equals(walkDown) && velocity.length() == 0) {
				current.stop();
				current.setCurrentFrame(1);
			}else if(!current.equals(walkDown)) {
				current.stop();
				this.removeAnimation(current);
				this.addAnimation(walkDown);
				current = walkDown;
			}
		}else if (((Entity) this.primaryWeapon).getRotation() > -135 && ((Entity) this.primaryWeapon).getRotation() < -45 ) {		//Player facing up
			if(current.equals(walkUp) &&  velocity.length() != 0) {
				current.start();				
			} else if( current.equals(walkUp) && velocity.length() == 0) {
				current.stop();
				current.setCurrentFrame(1);
			} else if(!current.equals(walkUp)) {
				current.stop();
				this.removeAnimation(current);
				this.addAnimation(walkUp);
				current = walkUp;
			}
		}
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
			System.out.println("caught exception when trying to translate player velocity" + e);
		}
		
		((Entity) this.primaryWeapon).setPosition(this.getPosition());
	}

	public boolean isRest() {
		return rest;
	}
	
	public void setRest(boolean rest) {
		this.rest = rest;
}

	public int getPoints() {
		// TODO Auto-generated method stub
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
}
