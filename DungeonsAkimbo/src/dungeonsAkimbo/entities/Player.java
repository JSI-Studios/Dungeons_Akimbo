package dungeonsAkimbo.entities;



import java.util.ArrayList;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity {
	
	private ArrayList<Weapon> gunBackpack;
	
	private Weapon primaryWeapon;
	
	
	private float speed;
	private int currentHealth;
	private int max_health;
	private int dodgeTimer = 500;
	
	private boolean dodging;
	
	private Vector velocity;
	
	public Player(final float x, final float y, int type) {
		super(x, y);
		// Max health can either be set from constructor, or a be statically 
		// constant, deal with later
		
		//class 1
		if (type==1) {
			setMax_health(100);
			setCurrent_health(getMax_health());
			speed = 2f;		
			primaryWeapon = new DaSniper();		
			gunBackpack = new ArrayList<Weapon>();		
			gunBackpack.add(primaryWeapon);
			this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_PLAYER));
		} else if (type==2) {
			setMax_health(50);
			setCurrent_health(getMax_health());
			speed = 4f;		
			primaryWeapon = new DaSniper();		
			gunBackpack = new ArrayList<Weapon>();		
			gunBackpack.add(primaryWeapon);
		}
	}
	
	public void Gun_Select(int i) {
		this.primaryWeapon = gunBackpack.get(i);
	}
	
	public Projectile Shoot(double inAngle) {	
		return this.primaryWeapon.Attack(inAngle);	
	}
	
	public Weapon Swap_Wep(Weapon s) {
		Weapon temp = this.primaryWeapon;
		this.primaryWeapon = s;
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
	
	public Vector Get_Position() {
		return this.getPosition();
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
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
			System.out.println("caught exception when trying to translate player velocity" + e);
		} 
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
