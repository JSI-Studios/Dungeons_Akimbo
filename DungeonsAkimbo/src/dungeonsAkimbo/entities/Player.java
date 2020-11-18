package dungeonsAkimbo.entities;



import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity {
	
	
	public float speed;
	private int current_health;
	private int max_health;
	public int dodge_timer = 500;
	
	boolean dodging;
	
	private Vector velocity;
	
	public Player(final float x, final float y) {
		super(x, y);
		// Max health can either be set from constructor, or a be statically 
		// constant, deal with later
		setMax_health(100);
		setCurrent_health(getMax_health());
		speed = 2f;
		speed = 1f;
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
