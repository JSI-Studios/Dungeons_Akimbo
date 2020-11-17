package dungeonsAkimbo.entities;

import dungeonsAkimbo.DungeonsAkimboGame;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Projectile extends Entity {
	
	private Vector velocity;
	private float speed;
	
	public Projectile (final float x, final float y) {
		super(x, y);
		this.speed = 2f;
		addImageWithBoundingBox(ResourceManager.getImage(DungeonsAkimboGame.TEMP_BULLET));
	}
	
	public void Set_Velocity() {
		velocity = new Vector(0f, -0.3f);
	}
	
	public void update(final int delta) {
		
		try {
			translate(velocity.scale(delta));
		} catch (Exception e) {
			System.out.println("caught exception when trying to translate player velocity" + e);
		} 
	}

}
