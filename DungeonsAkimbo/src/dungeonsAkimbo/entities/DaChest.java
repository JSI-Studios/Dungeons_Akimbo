package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaChest extends Entity {
	private int contents;
	private boolean state;
	
	public DaChest(float px, float py, int contents) {
		this.setPosition(new Vector(px, py));
		this.contents = contents;
		this.state = false;
		
		//todo: rig up open/close sprites
	}
	
	
	public int[] open() {
		int[] contents = null;
		
		if (!this.state) {
			contents = getContents(this.contents);
			this.state = true;
			
			}
		
		return contents;
	}


	private int[] getContents(int contents2) {
		// TODO return an array with ints representing objects ie. 1=health 2=ammo 3=powerup 4=gold bag 5=gold pieces
		return null;
	}
}
