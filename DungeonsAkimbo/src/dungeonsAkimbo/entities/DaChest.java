package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaChest extends Entity {
	private int contentsGroup;
	private boolean state;
	
	public DaChest(float px, float py, int contents) {
		this.setPosition(new Vector(px, py));
		this.contentsGroup = contents;
		this.state = false;
		
		//todo: rig up open/close sprites
	}
	
	
	public int[] open() {
		int[] contents = null;
		
		if (!this.state) {
			contents = getContents(this.contentsGroup);
			this.state = true;
			
			}
		
		return contents;
	}


	private int[] getContents(int contentsGroup) {
		// TODO return an array with ints representing objects ie. 1=health 2=ammo 3=powerup 4=gold bag 5=gold pieces
		if(contentsGroup == 0) {
			return new int[] {1,1,2};
		}else if (contentsGroup == 1) {
			return new int[] {1,2,2};
		}else if (contentsGroup == 2) {
			return new int[] {2,2};
		}else if (contentsGroup == 3) {
			return new int[] {3,5,5};
		}else if (contentsGroup == 4) {
			return new int[] {5,4,4};
		}
		
		return null;
	}
}
