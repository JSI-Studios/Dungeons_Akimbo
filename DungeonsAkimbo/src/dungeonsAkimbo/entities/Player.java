package dungeonsAkimbo.entities;


import org.newdawn.slick.state.StateBasedGame;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity {
	
	
	public float speed;
	private int current_health;
	private int max_health;
	public int dodge_timer = 3000;
	
	boolean dodging;
	
	private Vector velocity;
	
	public Player(final float x, final float y) {
		super(x, y);
		// Max health can either be set from constructor, or a be statically 
		// constant, deal with later
		setMax_health(100);
		setCurrent_health(getMax_health());
		speed = 2f;
		this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_PLAYER));
	}
	
	public void Shoot(StateBasedGame game) {
		
		DungeonsAkimboGame dag = (DungeonsAkimboGame) game;
		
		Projectile bullet = new Projectile(this.getX(), this.getY());
		bullet.Set_Velocity();
		dag.getPlayer_bullets().add(bullet);
		
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
	
	public int Get_Hash() {
		int hash = 17*(int)this.getX()^19 * (int)(this.getY());
		return hash;
	}
	
	public void update(final int delta) {
		
		if (this.dodging == true) {
			dodge_timer -= delta;
		}
		
		if (dodge_timer <= 0) {
			this.speed = 1f;
			this.dodging = false;
			dodge_timer = 3000;	
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
		return current_health;
	}

	public void setCurrent_health(int current_health) {
		this.current_health = current_health;
	}
}
