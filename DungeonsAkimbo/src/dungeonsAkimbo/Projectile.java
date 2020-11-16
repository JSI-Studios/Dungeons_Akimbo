package dungeonsAkimbo;

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

}
