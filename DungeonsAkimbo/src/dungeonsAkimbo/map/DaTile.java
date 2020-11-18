package dungeonsAkimbo.map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaTile extends Entity {
	
	private boolean blocked; // pathing blocked context
	private int type;	// type. ie. sprite.
	private int cost;	// tile pathing cost context
	private int tileX;	// tile map x
	private int tileY;	// tile map y
	
	DaTile(int x, int y, int type, Image tileImage){
		super(x, y);
		this.tileX = x;
		this.tileY = y;
		this.type = type;
		addImage(tileImage, new Vector(15,15));
	}
	
	
	public boolean getBlocked() {
		return blocked;
	}
	
	public int getType() {
		return type;
	}

	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	
	public int getHash() {
		int hash = 17 * (int)(this.getX()) ^ 19 * (int)(this.getY());
		return hash;
	}
}
