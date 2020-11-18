package dungeonsAkimbo.map;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.Vector;

public class DaWall extends Entity {
	
		private int tileX;
		private int tileY;
		private int type;
		private Image sprite;
		
	DaWall(int x, int y, int type, Image tileImage) {
		super(x, y);
		
		this.tileX = x;
		this.tileY = y;
		this.type = type; //vertical:208 horizontal:216 NEcorner:211 NWcorner:210 SWcorner:218 SEcorner:219
		this.addImageWithBoundingBox(tileImage);
		this.setCoarseGrainedRadius(this.getCoarseGrainedRadius()/2.15f);
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
