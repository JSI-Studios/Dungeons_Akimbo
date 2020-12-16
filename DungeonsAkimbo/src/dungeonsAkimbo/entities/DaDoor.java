package dungeonsAkimbo.entities;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.Vector;

public class DaDoor extends Entity{
	
	private int doorId, direction;
	private boolean state;
	private Image sprite;
	
	public DaDoor(float px, float py, boolean state, int doorId, int direction, Image sprite) {
		this.doorId = doorId;
		this.state = state;
		this.direction = direction; //0=N 1=W 2=S 3=E
		this.sprite = sprite;
		this.setPosition(new Vector(px, py));
		if(direction == 1 || direction == 3) {
			if(direction == 1) {
				this.addImageWithBoundingBox(sprite, new Vector(10,0));
			}
			if(direction == 3) {
				this.addImageWithBoundingBox(sprite, new Vector(-10,0));
			}
		}else {
			this.addImageWithBoundingBox(sprite);
		}
		
		
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
