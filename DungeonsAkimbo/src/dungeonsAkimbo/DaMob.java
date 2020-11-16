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
		setInitX(x);
		setInitY(y);
		this.setType(type);
		if(type == 0) {
			setHealth(3);
		} else {
			setHealth(5);
		}
	}

	@Override
	public boolean checkCollision(Entity player) {
		boolean didCollide = false;
		if(this.collides(player) != null) {
			this.setHealth(this.getHealth() - 1);
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public float getInitX() {
		return initX;
	}

	public void setInitX(float initX) {
		this.initX = initX;
	}

	public float getInitY() {
		return initY;
	}

	public void setInitY(float initY) {
		this.initY = initY;
	}
	
}
