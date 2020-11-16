package dungeonsAkimbo;


import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity {
	
	
	public float speed;
	public int current_health;
	public int max_health;
	public int dodge_timer = 3000;
	
	boolean dodging;
	
	private Vector velocity;
	
	public Player(final float x, final float y) {
		super(x, y);
		current_health = this.max_health;
		speed = 2f;
		this.addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_PLAYER));
	}
	
	public void Shoot(float inAngle) {
		
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
}
