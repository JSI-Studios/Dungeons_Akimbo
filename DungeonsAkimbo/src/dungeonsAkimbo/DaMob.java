package dungeonsAkimbo;

import jig.Entity;
import jig.Vector;

public class DaMob extends Entity implements DaEnemy {


	
	private int health;
	private int type;
	private Vector velocity;
	private float initX;
	private float initY;
	
	public DaMob(float x, float y, int type, boolean debug) {
		super(x,y);
		super.setDebug(debug);
		initX = x;
		initY = y;
		this.type = type;
		if(type == 0) {
			health = 3;
		} else {
			health = 5;
		}
	}

	@Override
	public boolean checkCollision(Entity player) {
		boolean didCollide = false;
		if(this.collides(player) != null) {
			this.health = this.health - 1;
			didCollide = true;
		}
		return didCollide;
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(final int delta) {
		
	}
	
}
