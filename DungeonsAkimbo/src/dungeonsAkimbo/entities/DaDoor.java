package dungeonsAkimbo.entities;

import jig.Entity;
import jig.Vector;

public class DaDoor extends Entity{
	
	private int doorId;
	private boolean state;
	
	public DaDoor(float px, float py, boolean state, int doorId) {
		this.doorId = doorId;
		this.state = state;
		this.setPosition(new Vector(px, py));
		
		//todo: pick sprite and setup vertical vs horizontal
	}
	
	
	public void activate() {
		if(!state) {
			//open the door
		}else {
			//close the door
		}
	}
	
	public int getId() {
		return doorId;
	}

}
