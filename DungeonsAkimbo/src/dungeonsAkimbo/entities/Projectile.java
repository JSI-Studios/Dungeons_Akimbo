package dungeonsAkimbo.entities;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Projectile extends Entity {
	
	private Vector velocity;
	private float speed;
	private int damage;
	private int timer;
	private boolean isMelee;
	
	
	public Projectile (final float x, final float y, int damage) {
		super(x, y);
		this.speed = 0.5f;
		this.damage = damage;
		this.setTimer(90000);
		this.isMelee = false;
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	public Projectile(final float x, final float y, int damage, int timer) {
		super(x, y);
		this.speed = 0.5f;
		this.damage = damage;
		this.setTimer(timer);
		this.isMelee = false;
		// Deal with changing sprite later for different melee sprites
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	// Most descriptive constructor
	public Projectile (final float x, final float y, int damage, int speed, int timer, boolean isMelee) {
		super(x, y);
		this.damage = damage;
		this.speed = speed;
		this.setTimer(timer);
		this.isMelee = isMelee;
		// Deal with changing sprite later for different melee sprites
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	public void decreaseTimer() {
		// Dedicated function that decrements the duration of the projectile
		this.setTimer(this.getTimer() - 1);
	}
	
	public void Set_Velocity(double inAngle) {
		velocity = Vector.getVector(inAngle, this.speed);
		
	}
	
	public int Get_Damage() {
		return this.damage;
	}
	
	public void update(final int delta) {
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
			System.out.println("caught exception when trying to translate player velocity" + e);
		} 
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

}
