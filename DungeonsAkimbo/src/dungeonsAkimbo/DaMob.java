package dungeonsAkimbo;

import org.newdawn.slick.SpriteSheet;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaMob extends Entity implements DaEnemy {

	private int health;
	private int type;
	private Vector velocity;
	private float initX;
	private float initY;
	
	private SpriteSheet sprite;
	
	public DaMob(float x, float y, int type, boolean debug) {
		super(x,y);
		super.setDebug(debug);
		setInitX(x);
		setInitY(y);
		this.setType(type);
		this.sprite = new SpriteSheet(ResourceManager.getImage(DungeonsAkimboGame.MOB_ONE), 32, 32, 4, 0);
		if(type == 0) {
			setHealth(3);
			addImageWithBoundingBox(sprite.getSprite(0, 0).getScaledCopy(.5f));
		} else {
			setHealth(5);
			addImageWithBoundingBox(sprite.getSprite(0, 0).getScaledCopy(.5f));
		}
	}

	@Override
	public boolean checkCollision(Entity player) {
		boolean didCollide = false;
		if(this.collides(player) != null) {
			this.setHealth(this.getHealth() - 1);
			didCollide = true;
			System.out.println("Collision!");
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
