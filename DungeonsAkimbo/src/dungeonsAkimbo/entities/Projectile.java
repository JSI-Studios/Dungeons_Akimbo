package dungeonsAkimbo.entities;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Projectile extends Entity {
	
	private Vector velocity;
	private float speed;
	private int damage;
	
	public Projectile (final float x, final float y, int damage) {
		super(x, y);
		this.speed = 0.5f;
		this.damage = damage;
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
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

}
