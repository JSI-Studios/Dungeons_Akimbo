package dungeonsAkimbo;

import org.newdawn.slick.Graphics;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class DaTile extends Entity {
	
	private boolean blocked;
	private int type;
	private int cost;
	private int tileX;
	private int tileY;
	
	DaTile(int x, int y, int type){
		super(x, y);
		this.tileX = x;
		this.tileY = y;
		this.type = type;
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
