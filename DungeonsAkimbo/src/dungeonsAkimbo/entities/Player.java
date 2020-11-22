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
	
	

	private float speed;
	private int currentHealth;
	private int max_health;
	private int dodgeTimer = 500;
	
	private boolean dodging;
	
	private Vector velocity;
	
	private Animation sprite;
	private SpriteSheet sprites;
	
	public Player(final float x, final float y, int type) {
		super(x, y);
		// Max health can either be set from constructor, or a be statically 
		// constant, deal with later
		
		this.sprite = new Animation(false);
		this.sprites = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.DA_PLAYER_RSC), 32, 32, 0, 0);
		
		
		//class 1
		if (type==1) {
			setMax_health(100);
			setCurrent_health(getMax_health());
			speed = 0.5f;		
			primaryWeapon = new DaSniper();		
			gunBackpack = new ArrayList<Weapon>();		
			gunBackpack.add(primaryWeapon);
			this.addImageWithBoundingBox(this.sprites.getSprite(1, 0));
			this.removeImage(this.sprites.getSprite(1, 0));
			this.sprite.addFrame(this.sprites.getSprite(1, 0), 1);		//Player facing down
			this.sprite.addFrame(this.sprites.getSprite(1, 1), 1);		//Player face left
			this.sprite.addFrame(this.sprites.getSprite(1, 2), 1);		//Player facing right
			this.sprite.addFrame(this.sprites.getSprite(1, 3), 1);		//Player facing up
			
			this.addAnimation(sprite);
		} 
	}
	
	public void Gun_Select(int i) {
		this.primaryWeapon = (Ranged) gunBackpack.get(i);
	}
	
	public Ranged getPrimaryWeapon() {
		return primaryWeapon;
	}
	
	public Projectile Shoot(double inAngle) {
		
		return (Projectile) this.primaryWeapon.primaryAtk(inAngle);	
	}
	
	public Weapon Swap_Wep(Weapon s) {
		Weapon temp = this.primaryWeapon;
		this.primaryWeapon = (Ranged) s;
		return temp;
	}
	
	public void Do_Dodge(final int delta, int scaler) {
		this.speed = scaler*this.speed;
		this.dodging = true;
	}
	
	public void Set_Velocity(final Vector v) {
		velocity = v;
	}
	
	public Vector Get_Velocity() {
		return velocity;
	}
	
	public void Set_Speed(float s) {
		this.speed = s;
	}
	
	public float Get_Speed() {
		return this.speed;
	}
	
	
	
	public int Get_Hash() {
		int hash = 17*(int)this.getX()^19 * (int)(this.getY());
		return hash;
	}
	
	public void update(final int delta) {
		
		if (this.dodging == true) {
			dodgeTimer -= delta;
			//System.out.println("Player is dodge? " + this.dodging);
		}
		
		if (dodgeTimer <= 0) {
			this.speed = 1f;
			this.dodging = false;
			dodgeTimer = 500;	
		}
		
		if (Math.abs(((Entity) this.primaryWeapon).getRotation()) < 45) { 		//Player facing right
			this.sprite.setCurrentFrame(2);
		} else if (Math.abs(((Entity) this.primaryWeapon).getRotation()) > 135) {		//Player facing left
			this.sprite.setCurrentFrame(1);
		} else if (((Entity) this.primaryWeapon).getRotation() > 45 && ((Entity) this.primaryWeapon).getRotation() < 135) {		//Player facing down
			this.sprite.setCurrentFrame(0);
		}else if (((Entity) this.primaryWeapon).getRotation() > -135 && ((Entity) this.primaryWeapon).getRotation() < -45 ) {		//Player facing up
			this.sprite.setCurrentFrame(3);
		}
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
			System.out.println("caught exception when trying to translate player velocity" + e);
		}
		
		((Entity) this.primaryWeapon).setPosition(this.getPosition());
	}

	public int getMax_health() {
		return max_health;
	}

	public void setMax_health(int max_health) {
		this.max_health = max_health;
	}

	public int getCurrent_health() {
		return currentHealth;
	}

	public void setCurrent_health(int current_health) {
		this.currentHealth = current_health;
	}
}
