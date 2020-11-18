package dungeonsAkimbo;


import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity {
	
	
	public float speed;
	public int current_health;
	public int max_health;
	public int dodge_timer = 500;
	
	boolean dodging;
	
	private Vector velocity;
	
	public Player(final float x, final float y) {
		super(x, y);
		current_health = this.max_health;
		speed = 2f;
		this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_PLAYER));
	}
	
	public Projectile Shoot(double inAngle) {
		
		Projectile bullet = new Projectile(this.getX(), this.getY());
		bullet.rotate(inAngle);
		bullet.Set_Velocity(inAngle);
		return bullet;
		
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
	
	public Vector Get_Position() {
		return this.getPosition();
	}
	
	public int Get_Hash() {
		int hash = 17*(int)this.getX()^19 * (int)(this.getY());
		return hash;
	}
	
	public void update(final int delta) {
		
		if (this.dodging == true) {
			dodge_timer -= delta;
			//System.out.println("Player is dodge? " + this.dodging);
		}
		
		if (dodge_timer <= 0) {
			this.speed = 1f;
			this.dodging = false;
			dodge_timer = 500;	
		}
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
			System.out.println("caught exception when trying to translate player velocity" + e);
		} 
	}
}
