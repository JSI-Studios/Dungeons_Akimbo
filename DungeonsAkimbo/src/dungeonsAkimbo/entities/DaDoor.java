package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaDoor extends Entity{
	
	private int doorId, direction;
	private boolean state;
	
	public DaDoor(float px, float py, boolean state, int doorId, int direction) {
		this.doorId = doorId;
		this.state = state;
		this.direction = direction; //0=N 1=W 2=S 3=E
		this.setPosition(new Vector(px, py));
		
		//todo: pick sprite and setup vertical vs horizontal
		
	}
	
	
	public void activate() {
		if(!state) {
			if(direction == 1 || direction == 3) {
				this.translate(new Vector(0,32));
			}else {
				this.translate(new Vector(32,0));
			}
			state = !state;
		}else {
			if(direction == 1 || direction == 3) {
				this.translate(new Vector(0,-32));
			}else {
				this.translate(new Vector(-32,0));
			}
			state = !state;
		}
	}
	
	public int getId() {
		return doorId;
	}

}
